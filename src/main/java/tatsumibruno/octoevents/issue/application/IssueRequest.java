package tatsumibruno.octoevents.issue.application;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.ZonedDateTime;
import java.util.StringJoiner;

public class IssueRequest {
    @NotNull
    private Integer number;
    @NotEmpty
    private String title;
    private String body;
    @NotEmpty
    private String state;
    @NotNull
    @PastOrPresent
    @JsonProperty("created_at")
    private ZonedDateTime createdAt;

    public Integer getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getState() {
        return state;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", IssueRequest.class.getSimpleName() + "[", "]")
                .add("number=" + number)
                .add("title='" + title + "'")
                .add("body='" + body + "'")
                .add("state='" + state + "'")
                .add("createdAt=" + createdAt)
                .toString();
    }
}
