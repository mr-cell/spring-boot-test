package mr.cell.incubator.springboottest.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import mr.cell.incubator.springboottest.controller.BookmarkRestController;
import mr.cell.incubator.springboottest.domain.Bookmark;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class BookmarkResource extends ResourceSupport {
	
	private final Bookmark bookmark;
	
	public BookmarkResource(Bookmark bookmark) {
		String username = bookmark.getAccount().getUsername();
		this.bookmark = bookmark;
		this.add(new Link(bookmark.getUri(), "bookmark-uri"));
		this.add(linkTo(BookmarkRestController.class, username).withRel("bookmarks"));
		this.add(linkTo(methodOn(BookmarkRestController.class, username).getBookmark(username, bookmark.getId())).withSelfRel());
	}
}
