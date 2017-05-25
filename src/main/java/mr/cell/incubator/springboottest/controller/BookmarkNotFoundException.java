package mr.cell.incubator.springboottest.controller;

public class BookmarkNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 4981902598683063883L;

	public BookmarkNotFoundException(Long bookmarkId) {
		super("Could not find bookmark with id '" + bookmarkId + "'");
	}

}
