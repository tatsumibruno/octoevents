package tatsumibruno.octoevents.issue.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "issue.not.found")
public class EventNotFoundException extends RuntimeException {
}
