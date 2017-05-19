package mr.cell.incubator.springboottest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import mr.cell.incubator.springboottest.BookmarksApplication;
import mr.cell.incubator.springboottest.domain.Account;
import mr.cell.incubator.springboottest.domain.Bookmark;
import mr.cell.incubator.springboottest.repository.AccountRepository;
import mr.cell.incubator.springboottest.repository.BookmarkRepository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookmarksApplication.class)
@AutoConfigureMockMvc
public class BookmarkRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	private String username = "test";
	
	private Account account;
	
	private List<Bookmark> bookmarkList = new ArrayList<>();
	
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private BookmarkRepository bookmarkRepository;
	
	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {
		mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
				.findAny()
				.orElse(null);
		
		assertNotNull("the JSON message converter must not be null", mappingJackson2HttpMessageConverter);
	}
	
	@Before
	public void setup() throws Exception {
		bookmarkRepository.deleteAllInBatch();
		accountRepository.deleteAllInBatch();
		
		account = accountRepository.save(new Account(username, "password"));
		bookmarkList.add(bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + username, "description1")));
		bookmarkList.add(bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + username, "description2")));
	}
	
	@Test
	public void userNotFound() throws Exception {
		mockMvc.perform(
				post("/dummy/bookmarks")
						.content(json(new Bookmark()))
						.contentType(APPLICATION_JSON_UTF8)
		).andExpect(status().isNotFound());
	}
	
	@Test
	public void getSingleBookmark() throws Exception {
		mockMvc.perform(get("/" + username + "/bookmarks/" + bookmarkList.get(0).getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(this.bookmarkList.get(0).getId().intValue())))
				.andExpect(jsonPath("$.uri", is(this.bookmarkList.get(0).getUri())))
				.andExpect(jsonPath("$.description", is(this.bookmarkList.get(0).getDescription())));
	}
	
	@Test
	public void getBookmarks() throws Exception {
		mockMvc.perform(get("/" + username + "/bookmarks"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(bookmarkList.get(0).getId().intValue())))
				.andExpect(jsonPath("$[0].uri", is(bookmarkList.get(0).getUri())))
				.andExpect(jsonPath("$[0].description", is(bookmarkList.get(0).getDescription())))
				.andExpect(jsonPath("$[1].id", is(bookmarkList.get(1).getId().intValue())))
				.andExpect(jsonPath("$[1].uri", is(bookmarkList.get(1).getUri())))
				.andExpect(jsonPath("$[1].description", is(bookmarkList.get(1).getDescription())));
	}
	
	@Test
	public void createBookmark() throws Exception {
		mockMvc.perform(
				post("/" + username + "/bookmarks")
						.contentType(APPLICATION_JSON_UTF8)
						.content(json(new Bookmark(account, "http://bookmark.com/3/test", "test description")))
		).andExpect(status().isCreated())
				.andExpect(header().string("location", notNullValue()));
	}
	
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage message = new MockHttpOutputMessage();
		mappingJackson2HttpMessageConverter.write(o, APPLICATION_JSON_UTF8, message);
		return message.getBodyAsString();
	}
}
