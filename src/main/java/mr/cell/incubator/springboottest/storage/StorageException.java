package mr.cell.incubator.springboottest.storage;

public class StorageException extends RuntimeException {
	
	private static final long serialVersionUID = 6868288877010794772L;

	public StorageException(String message, Throwable t) {
		super(message, t);
	}
	
	public StorageException(String message) {
		super(message);
	}

}
