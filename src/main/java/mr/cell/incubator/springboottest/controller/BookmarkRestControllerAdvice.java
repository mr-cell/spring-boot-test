package mr.cell.incubator.springboottest.controller;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestControllerAdvice
public class BookmarkRestControllerAdvice {
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public VndErrors userNotFoundExceptionHandler(UserNotFoundException e) {
		return new VndErrors("error", e.getMessage(), linkTo(BookmarkRestController.class).withRel("bookmarks"));
	}
	
	@ExceptionHandler(IllegalBookmarkAccessException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public VndErrors illegalBookmarkAccessExceptionHandler(IllegalBookmarkAccessException e) {
		return new VndErrors("error", e.getMessage(), linkTo(BookmarkRestController.class).withRel("bookmarks"));
	}
	
	@ExceptionHandler(BookmarkNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public VndErrors bookmarkNotFoundExceptionHandler(BookmarkNotFoundException e) {
		return new VndErrors("error", e.getMessage(), linkTo(BookmarkRestController.class).withRel("bookmarks"));
	}

}
