package mr.cell.incubator.springboottest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -148668516174254614L;

	public PersonNotFoundException(String name) {
		super("Could not find person with name '" + name + "'.");
	}
}
