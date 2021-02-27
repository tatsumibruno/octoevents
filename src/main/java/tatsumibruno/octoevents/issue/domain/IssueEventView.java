package tatsumibruno.octoevents.issue.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

public interface IssueEventView {

    String getAction();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    ZonedDateTime getGeneratedAt();

    Integer getIssueNumber();

    String getIssueTitle();

    String getIssueBody();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    ZonedDateTime getIssueCreatedAt();

    String getIssueState();
}
