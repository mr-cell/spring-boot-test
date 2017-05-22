package mr.cell.incubator.springboottest.controller;

public class PersonNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -148668516174254614L;

	public PersonNotFoundException(String name) {
		super("Could not find person with name '" + name + "'.");
	}
}
