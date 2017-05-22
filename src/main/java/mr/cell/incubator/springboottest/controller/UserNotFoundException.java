package mr.cell.incubator.springboottest.controller;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1133262251632672688L;

	public UserNotFoundException(String username) {
		super("Could not find user with username '" + username + "'.");
	}
}
