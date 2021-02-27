package tatsumibruno.octoevents.issue.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tatsumibruno.octoevents.issue.domain.IssueEventProcessor;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = "/webhooks")
public class EventWebhookController {

    private final IssueEventProcessor processor;

    public EventWebhookController(IssueEventProcessor processor) {
        this.processor = processor;
    }

    @PostMapping(path = "/events")
    public ResponseEntity<Void> receive(@RequestBody @Valid IssueEventRequest event) {
        processor.handle(event);
        URI uriResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/issues/{issueNumber}/events")
                .build(event.issueNumber());
        return ResponseEntity.created(uriResource).build();
    }
}
