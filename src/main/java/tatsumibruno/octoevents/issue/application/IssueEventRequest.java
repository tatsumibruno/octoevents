package tatsumibruno.octoevents.issue.application;

import tatsumibruno.octoevents.issue.domain.IssueEventOcurred;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.StringJoiner;

public class IssueEventRequest implements IssueEventOcurred {
    @NotEmpty
    private String action;
    @Valid
    @NotNull
    private IssueRequest issue;

    public String getAction() {
        return action;
    }

    public IssueRequest getIssue() {
        return issue;
    }

    @Override
    public String action() {
        return action;
    }

    @Override
    public Integer issueNumber() {
        return issue.getNumber();
    }

    @Override
    public String issueTitle() {
        return issue.getTitle();
    }

    @Override
    public String issueBody() {
        return issue.getBody();
    }

    @Override
    public String issueState() {
        return issue.getState();
    }

    @Override
    public ZonedDateTime createdAt() {
        return issue.getCreatedAt();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", IssueEventRequest.class.getSimpleName() + "[", "]")
                .add("action='" + action + "'")
                .add("issue=" + issue)
                .toString();
    }
}
