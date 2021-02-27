package tatsumibruno.octoevents.issue.domain;

import java.time.ZonedDateTime;

public interface IssueEventOcurred {
    String action();
    Integer issueNumber();
    String issueTitle();
    String issueBody();
    String issueState();
    ZonedDateTime createdAt();
}
