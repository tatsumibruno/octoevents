package tatsumibruno.octoevents.issue.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Tag("unit")
class IssueTest {

    @Test
    @DisplayName("If two issues have the same number, they are the same")
    void isEquals() {
        final Issue issueOne = Issue.from(new IssueEventOcurredTest(1));
        final Issue issueTwo = Issue.from(new IssueEventOcurredTest(1));
        assertEquals(issueOne, issueTwo);
    }

    @Test
    @DisplayName("If two issues have the same ID but not the same number, they are different")
    void isNotEquals() {
        final Issue issueOne = Issue.from(new IssueEventOcurredTest(1));
        final Issue issueTwo = Issue.from(new IssueEventOcurredTest(2));
        ReflectionTestUtils.setField(issueOne, "id", 1L);
        ReflectionTestUtils.setField(issueTwo, "id", 1L);
        assertNotEquals(issueOne, issueTwo);
    }

    class IssueEventOcurredTest implements IssueEventOcurred {

        private Integer number;

        public IssueEventOcurredTest(Integer number) {
            this.number = number;
        }

        @Override
        public String action() {
            return "fake action";
        }

        @Override
        public Integer issueNumber() {
            return number;
        }

        @Override
        public String issueTitle() {
            return "fake title";
        }

        @Override
        public String issueBody() {
            return "fake body";
        }

        @Override
        public String issueState() {
            return "fake state";
        }

        @Override
        public ZonedDateTime createdAt() {
            return ZonedDateTime.now();
        }
    }
}