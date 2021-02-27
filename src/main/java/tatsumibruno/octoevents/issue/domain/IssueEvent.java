package tatsumibruno.octoevents.issue.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

@Entity
public class IssueEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_event_seq")
    @SequenceGenerator(name = "issue_event_seq", allocationSize = 1)
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Issue issue;
    @NotNull
    private ZonedDateTime generatedAt;
    @NotNull
    private String action;

    private IssueEvent(Issue issue, String action) {
        this.issue = requireNonNull(issue, "Issue cannot be null");
        this.action = requireNonNull(action, "Event action cannot be null");
        this.generatedAt = ZonedDateTime.now();
    }

    /**
     * Just for Hibernate, dont use it
     */
    IssueEvent() {
    }

    public static IssueEvent of(Issue issue, String action) {
        return new IssueEvent(issue, action);
    }



    @Override
    public String toString() {
        return new StringJoiner(", ", IssueEvent.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("generatedAt=" + generatedAt)
                .add("action='" + action + "'")
                .toString();
    }
}
