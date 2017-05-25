package mr.cell.incubator.springboottest.controller;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestControllerAdvice
public class PersonRestControllerAdvice {
	
	@ExceptionHandler(PersonNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public VndErrors personNotFoundExceptionHandler(PersonNotFoundException e) {
		return new VndErrors("error", e.getMessage(), linkTo(PersonRestController.class).withRel("persons"));
	}
}
