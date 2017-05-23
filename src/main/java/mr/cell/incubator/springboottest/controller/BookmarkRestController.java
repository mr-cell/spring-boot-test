package mr.cell.incubator.springboottest.controller;

import java.net.URI;
import java.security.Principal;
import java.util.stream.Collectors;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mr.cell.incubator.springboottest.domain.Bookmark;
import mr.cell.incubator.springboottest.repository.AccountRepository;
import mr.cell.incubator.springboottest.repository.BookmarkRepository;
import mr.cell.incubator.springboottest.resource.BookmarkResource;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/bookmarks")
public class BookmarkRestController {

	private final BookmarkRepository bookmarks;
	private final AccountRepository accounts;
	
	public BookmarkRestController(AccountRepository accounts, BookmarkRepository bookmarks) {
		this.accounts = accounts;
		this.bookmarks = bookmarks;
	}
	
	@RequestMapping(method = GET)
	public Resources<BookmarkResource> getBookmarks(Principal principal) {
		validateUsername(principal);
		
		return new Resources<>(bookmarks.findByAccountUsername(principal.getName())
				.stream()
				.map(bookmark -> new BookmarkResource(principal, bookmark))
				.collect(Collectors.toList()));		
	}
	
	@RequestMapping(method = POST)
	public ResponseEntity<?> add(Principal principal, @RequestBody Bookmark payload) {
		validateUsername(principal);
		
		return accounts.findByUsername(principal.getName()).map(account -> {
			Bookmark bookmark = bookmarks.save(new Bookmark(account, payload.getUri(), payload.getDescription()));
			Link selfRef = new BookmarkResource(principal, bookmark).getLink("self");
			return ResponseEntity.created(URI.create(selfRef.getHref())).build();
		}).orElse(ResponseEntity.noContent().build());
	}
	
	@RequestMapping(method = GET, value = "/{bookmarkId}")
	public BookmarkResource getBookmark(Principal principal, @PathVariable Long bookmarkId) {
		validateUsername(principal);
		return new BookmarkResource(principal, bookmarks.findOne(bookmarkId));
	}
	
	private void validateUsername(Principal principal) {
		String username = principal.getName();
		accounts.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
	}
}
