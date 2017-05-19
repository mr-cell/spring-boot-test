package mr.cell.incubator.springboottest.storage;

public class StorageFileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8689643203988087740L;

	public StorageFileNotFoundException(String message) {
		super(message);
	}
	
	public StorageFileNotFoundException(String message, Throwable t) {
		super(message, t);
	}
}
