package mr.cell.incubator.springboottest.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import mr.cell.incubator.springboottest.controller.BookmarkRestController;
import mr.cell.incubator.springboottest.domain.Bookmark;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.security.Principal;

@Getter
public class BookmarkResource extends ResourceSupport {
	
	private final Bookmark bookmark;
	
	public BookmarkResource(Principal principal, Bookmark bookmark) {
		String username = bookmark.getAccount().getUsername();
		this.bookmark = bookmark;
		this.add(new Link(bookmark.getUri(), "bookmark-uri"));
		this.add(linkTo(BookmarkRestController.class).withRel("bookmarks"));
		this.add(linkTo(methodOn(BookmarkRestController.class).getBookmark(principal, bookmark.getId())).withSelfRel());
	}
}
