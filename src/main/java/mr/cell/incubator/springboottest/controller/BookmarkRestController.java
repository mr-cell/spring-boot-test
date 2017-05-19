package mr.cell.incubator.springboottest.controller;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import mr.cell.incubator.springboottest.domain.Bookmark;
import mr.cell.incubator.springboottest.repository.AccountRepository;
import mr.cell.incubator.springboottest.repository.BookmarkRepository;

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
	Collection<Bookmark> getBookmarks(@PathVariable String userId) {
		validateUsername(userId);
		return bookmarks.findByAccountUsername(userId);
	}
	
	@RequestMapping(method = POST)
	ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark payload) {
		validateUsername(userId);
		
		return accounts.findByUsername(userId).map(account -> {
			Bookmark bookmark = bookmarks.save(new Bookmark(account, payload.getUri(), payload.getDescription()));
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(bookmark.getId()).toUri();
			return ResponseEntity.created(location).build();
		}).orElse(ResponseEntity.noContent().build());
	}
	
	@RequestMapping(method = GET, value = "/{bookmarkId}")
	Bookmark getBookmark(@PathVariable String userId, @PathVariable Long bookmarkId) {
		validateUsername(userId);
		return bookmarks.findOne(bookmarkId);
	}
	
	private void validateUsername(String username) {
		accounts.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
	}
}
