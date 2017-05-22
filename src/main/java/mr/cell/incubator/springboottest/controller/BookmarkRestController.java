package mr.cell.incubator.springboottest.controller;

import java.net.URI;
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
@RequestMapping("/{userId}/bookmarks")
public class BookmarkRestController {

	private final BookmarkRepository bookmarks;
	private final AccountRepository accounts;
	
	public BookmarkRestController(AccountRepository accounts, BookmarkRepository bookmarks) {
		this.accounts = accounts;
		this.bookmarks = bookmarks;
	}
	
	@RequestMapping(method = GET)
	public Resources<BookmarkResource> getBookmarks(@PathVariable String userId) {
		validateUsername(userId);
		
		return new Resources<>(bookmarks.findByAccountUsername(userId)
				.stream()
				.map(BookmarkResource::new)
				.collect(Collectors.toList()));		
	}
	
	@RequestMapping(method = POST)
	public ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark payload) {
		validateUsername(userId);
		
		return accounts.findByUsername(userId).map(account -> {
			Bookmark bookmark = bookmarks.save(new Bookmark(account, payload.getUri(), payload.getDescription()));
			Link selfRef = new BookmarkResource(bookmark).getLink("self");
			return ResponseEntity.created(URI.create(selfRef.getHref())).build();
		}).orElse(ResponseEntity.noContent().build());
	}
	
	@RequestMapping(method = GET, value = "/{bookmarkId}")
	public BookmarkResource getBookmark(@PathVariable String userId, @PathVariable Long bookmarkId) {
		validateUsername(userId);
		return new BookmarkResource(bookmarks.findOne(bookmarkId));
	}
	
	private void validateUsername(String username) {
		accounts.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
	}
}
