package tatsumibruno.octoevents.issue.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tatsumibruno.octoevents.issue.domain.IssueEventView;
import tatsumibruno.octoevents.issue.domain.IssueRepository;
import tatsumibruno.octoevents.issue.exceptions.EventNotFoundException;

import java.util.List;

@RestController
@RequestMapping(path = "/issues")
public class IssueEventController {

    private final IssueRepository issueRepository;

    public IssueEventController(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @GetMapping(path = "/{issueNumber}/events")
    public ResponseEntity<List<IssueEventView>> getIssueEvents(@PathVariable("issueNumber") Integer issueNumber) {
        List<IssueEventView> events = issueRepository.eventsViewByIssueNumber(issueNumber);
        if (events.isEmpty()) {
            throw new EventNotFoundException();
        }
        return ResponseEntity.ok(events);
    }
}
