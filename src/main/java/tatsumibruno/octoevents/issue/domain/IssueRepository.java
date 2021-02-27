package tatsumibruno.octoevents.issue.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends Repository<Issue, Long> {

    void save(Issue issue);

    @Transactional(readOnly = true)
    @Query("select i from Issue i join fetch i.events where i.number = :number")
    Optional<Issue> findByNumber(Integer number);

    @Transactional(readOnly = true)
    @Query("select e.action as action, " +
            "e.generatedAt as generatedAt, " +
            "e.issue.number as issueNumber, " +
            "e.issue.title as issueTitle, " +
            "e.issue.body as issueBody, " +
            "e.issue.createdAt as issueCreatedAt, " +
            "e.issue.state as issueState " +
            "from Issue i " +
            "join i.events e " +
            "where i.number = :number")
    List<IssueEventView> eventsViewByIssueNumber(Integer number);
}
