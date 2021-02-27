package tatsumibruno.octoevents.issue.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
@Validated
public class IssueEventProcessor {

    private final IssueRepository issueRepository;

    public IssueEventProcessor(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void handle(@Valid @NotNull IssueEventOcurred event) {
        final Issue issue = issueRepository.findByNumber(event.issueNumber())
                .map(existingIssue -> {
                    existingIssue.update(event);
                    return existingIssue;
                })
                .orElse(Issue.from(event));
        issueRepository.save(issue);
    }
}
