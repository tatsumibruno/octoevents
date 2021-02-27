package tatsumibruno.octoevents.issue.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tatsumibruno.octoevents.issue.domain.IssueRepository;
import tatsumibruno.octoevents.issue.domain.IssueView;
import tatsumibruno.octoevents.issue.exceptions.EventNotFoundException;

@RestController
@RequestMapping(path = "/issues")
public class IssueEventController {

    private final IssueRepository issueRepository;

    public IssueEventController(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @GetMapping(path = "/{issueNumber}/events")
    public ResponseEntity<IssueView> getIssueEvents(@PathVariable("issueNumber") Integer issueNumber) {
        final IssueView issueView = issueRepository.eventsViewByIssueNumber(issueNumber)
                .orElseThrow(EventNotFoundException::new);
        return ResponseEntity.ok(issueView);
    }
}
