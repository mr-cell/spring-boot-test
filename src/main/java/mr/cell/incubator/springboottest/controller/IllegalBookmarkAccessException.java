package mr.cell.incubator.springboottest.controller;

public class IllegalBookmarkAccessException extends RuntimeException {
	
	private static final long serialVersionUID = 6425188810399102455L;

	public IllegalBookmarkAccessException(Long bookmarkId) {
		super("Illegal access to bookmark with id '" + bookmarkId + "'");
	}

}
