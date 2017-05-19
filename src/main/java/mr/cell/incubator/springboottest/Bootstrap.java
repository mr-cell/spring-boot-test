package mr.cell.incubator.springboottest;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import mr.cell.incubator.springboottest.domain.Account;
import mr.cell.incubator.springboottest.domain.Bookmark;
import mr.cell.incubator.springboottest.repository.AccountRepository;
import mr.cell.incubator.springboottest.repository.BookmarkRepository;
import mr.cell.incubator.springboottest.storage.StorageService;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {
	
	private AccountRepository accounts;
	private BookmarkRepository bookmarks;
	private StorageService storage;
	
	public Bootstrap(AccountRepository accounts, BookmarkRepository bookmarks, StorageService storage) {
		this.accounts = accounts;
		this.bookmarks = bookmarks;
		this.storage = storage;
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Running bootstrap...");
		Arrays.asList("marcel", "ola", "adam", "jan", "kasia").forEach( username -> {
				Account account = accounts.save(new Account(username, "password"));
				bookmarks.save(new Bookmark(account, "https://bookmarks.com/1/" + username, "A description"));
				bookmarks.save(new Bookmark(account, "https://bookmarks.com/2/" + username, "B description"));
		});
		
		storage.deleteAll();
		storage.init();
	}
}
