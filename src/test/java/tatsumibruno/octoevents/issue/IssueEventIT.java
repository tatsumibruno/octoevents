package tatsumibruno.octoevents.issue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tatsumibruno.octoevents.issue.domain.IssueEventView;
import tatsumibruno.octoevents.issue.domain.IssueRepository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Sql(value = "classpath:scripts/setup-issue-events.sql")
class IssueEventIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IssueRepository issueRepository;

    @Test
    @DisplayName("It should retrieve issue events ordered and with attributes correctly")
    void getEvents() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/issues/1/events")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].action", equalTo("open")))
                .andExpect(jsonPath("$[0].generatedAt", equalTo("2021-02-27T11:00:00Z")))
                .andExpect(jsonPath("$[0].issueNumber", equalTo(1)))
                .andExpect(jsonPath("$[0].issueTitle", equalTo("Issue number 1")))
                .andExpect(jsonPath("$[0].issueBody", equalTo("Content of issue number 1")))
                .andExpect(jsonPath("$[0].issueCreatedAt", equalTo("2021-02-27T11:00:00Z")))
                .andExpect(jsonPath("$[0].issueState", equalTo("closed")))
                .andExpect(jsonPath("$[1].action", equalTo("edited")))
                .andExpect(jsonPath("$[1].generatedAt", equalTo("2021-02-27T11:01:00Z")))
                .andExpect(jsonPath("$[2].action", equalTo("closed")))
                .andExpect(jsonPath("$[2].generatedAt", equalTo("2021-02-27T11:10:00Z")))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @DisplayName("It should get 404 error when find for a issue that doesnt exists")
    void getEventWithoutResult() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/issues/999/events")
                .characterEncoding("UTF-8")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en-US")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Issue requested doesnt exists", result.getResponse().getErrorMessage()));
    }

    @Test
    @DisplayName("It should create a new Issue")
    void webhook() throws Exception {
        Map<String, Object> issueEventRequest = new HashMap<>();
        Map<String, Object> issueRequest = new HashMap<>();
        issueRequest.put("number", 10);
        issueRequest.put("title", "Issue 10");
        issueRequest.put("body", "Body of Issue 10");
        issueRequest.put("state", "open");
        issueRequest.put("created_at", "2021-02-27T08:30:00-00:00");
        issueEventRequest.put("action", "opened");
        issueEventRequest.put("issue", issueRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/webhooks/events")
                .content(objectMapper.writeValueAsString(issueEventRequest))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/issues/10/events"));
    }

    @Test
    @DisplayName("It should create a new Issue and increment new events")
    void webhookSequenceEvents() throws Exception {
        Map<String, Object> issueEventRequest = new HashMap<>();
        Map<String, Object> issueRequest = new HashMap<>();
        issueRequest.put("number", 100);
        issueRequest.put("title", "Issue 100");
        issueRequest.put("body", "Body of Issue 100");
        issueRequest.put("state", "open");
        issueRequest.put("created_at", "2021-02-27T08:30:00-00:00");
        issueEventRequest.put("action", "opened");
        issueEventRequest.put("issue", issueRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/webhooks/events")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder.content(objectMapper.writeValueAsString(issueEventRequest)))
                .andExpect(status().isCreated());
        issueEventRequest.put("action", "edited");
        issueRequest.put("title", "Issue 100 edited");
        mockMvc.perform(requestBuilder.content(objectMapper.writeValueAsString(issueEventRequest)))
                .andExpect(status().isCreated());
        issueEventRequest.put("action", "closed");
        issueRequest.put("state", "closed");
        mockMvc.perform(requestBuilder.content(objectMapper.writeValueAsString(issueEventRequest)))
                .andExpect(status().isCreated());
        List<IssueEventView> issueEventViews = issueRepository.eventsViewByIssueNumber(100);
        assertEquals(3, issueEventViews.size());
        IssueEventView firstEvent = issueEventViews.get(0);
        assertEquals("Issue 100 edited", firstEvent.getIssueTitle());
        assertEquals("closed", firstEvent.getIssueState());
    }

}