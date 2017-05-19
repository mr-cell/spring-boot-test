package mr.cell.incubator.springboottest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1133262251632672688L;

	public UserNotFoundException(String username) {
		super("Could not find user with username '" + username + "'.");
	}
}
