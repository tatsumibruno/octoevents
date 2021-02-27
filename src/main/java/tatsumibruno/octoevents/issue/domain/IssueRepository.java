package tatsumibruno.octoevents.issue.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IssueRepository extends Repository<Issue, Long> {

    void save(Issue issue);

    @Transactional(readOnly = true)
    @Query("select i from Issue i join fetch i.events where i.number = :number")
    Optional<Issue> findByNumber(Integer number);

    @Transactional(readOnly = true)
    @Query("select i from Issue i join fetch i.events e where i.number = :number")
    Optional<IssueView> eventsViewByIssueNumber(Integer number);
}
