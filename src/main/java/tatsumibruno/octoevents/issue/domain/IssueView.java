package tatsumibruno.octoevents.issue.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.List;

public interface IssueView {
    Integer getNumber();
    String getTitle();
    String getBody();
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    ZonedDateTime getCreatedAt();
    String getState();
    List<IssueEventView> getEvents();
}

interface IssueEventView {
    String getAction();
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    ZonedDateTime getGeneratedAt();
}
