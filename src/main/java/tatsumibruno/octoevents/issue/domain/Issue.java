package tatsumibruno.octoevents.issue.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "issue", uniqueConstraints = {
        @UniqueConstraint(name = "issue_number_un", columnNames = "number")
})
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_seq")
    @SequenceGenerator(name = "issue_seq", allocationSize = 1)
    private Long id;
    @NotNull
    private Integer number;
    @NotNull
    private String state;
    @NotNull
    private String title;
    private String body;
    @NotNull
    private ZonedDateTime createdAt;
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    @OrderBy("generatedAt ASC")
    private List<IssueEvent> events = new ArrayList<>();

    private Issue(Integer number, String state, String title, String body, ZonedDateTime createdAt) {
        this.number = requireNonNull(number, "Issue number cannot be null");
        this.state = requireNonNull(state, "Issue state cannot be null");
        this.title = requireNonNull(title, "Issue title cannot be null");
        this.body = body;
        this.createdAt = requireNonNull(createdAt, "Issue creation time cannot be null");
    }

    /**
     * Just for Hibernate, dont use it
     */
    Issue() {
    }

    public static Issue from(IssueEventOcurred issueCreated) {
        Issue issue = new Issue(issueCreated.issueNumber(),
                issueCreated.issueState(),
                issueCreated.issueTitle(),
                issueCreated.issueBody(),
                issueCreated.createdAt());
        issue.addEvent(issueCreated);
        return issue;
    }

    public void update(IssueEventOcurred event) {
        requireNonNull(event, "Event cannot be null");
        this.state = requireNonNull(event.issueState(), "Issue state cannot be null");
        this.title = requireNonNull(event.issueTitle(), "Issue title cannot be null");
        this.addEvent(event);
    }

    public void addEvent(IssueEventOcurred eventOcurred) {
        IssueEvent issueEvent = IssueEvent.of(this, eventOcurred.action());
        this.events.add(issueEvent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return Objects.equals(number, issue.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Issue.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("number=" + number)
                .add("state='" + state + "'")
                .add("title='" + title + "'")
                .add("body='" + body + "'")
                .add("createdAt=" + createdAt)
                .add("events=" + events.size())
                .toString();
    }
}
