package mr.cell.incubator.springboottest.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import mr.cell.incubator.springboottest.BookmarksApplication;
import mr.cell.incubator.springboottest.OAuthHelper;
import mr.cell.incubator.springboottest.domain.Person;
import mr.cell.incubator.springboottest.repository.PersonRepository;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookmarksApplication.class)
@AutoConfigureMockMvc
public class PersonRestControllerTest {
	
	private static final MediaType APPLICATION_HAL_JSON_UTF8 = new MediaType("application", "hal+json", StandardCharsets.UTF_8);
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private OAuthHelper authHelper;
	
	@MockBean
	private PersonRepository persons;
	
	@Test
	public void getAllPersons() throws Exception {
		List<Person> personList = Arrays.asList(new Person(1L, "test1", 20), new Person(2L, "test2", 21));
		given(persons.findAll()).willReturn(personList);
		
		mvc.perform(get("/persons")
						.with(authHelper.addBearerToken("test", "ROLE_USER"))
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_HAL_JSON_UTF8))
				.andExpect(jsonPath("$._embedded.persons", hasSize(2)))
				.andExpect(jsonPath("$._embedded.persons[0].person.id", is(personList.get(0).getId().intValue())))
				.andExpect(jsonPath("$._embedded.persons[0].person.name", is(personList.get(0).getName())))
				.andExpect(jsonPath("$._embedded.persons[0].person.age", is(personList.get(0).getAge())))
				.andExpect(jsonPath("$._embedded.persons[1].person.id", is(personList.get(1).getId().intValue())))
				.andExpect(jsonPath("$._embedded.persons[1].person.name", is(personList.get(1).getName())))
				.andExpect(jsonPath("$._embedded.persons[1].person.age", is(personList.get(1).getAge())));
	}
	
	@Test
	public void getValidPerson() throws Exception {
		Person person = new Person(1L, "test1", 20);
		given(persons.findByName("test1")).willReturn(Optional.of(person));
		
		mvc.perform(get("/persons/test1")
				.with(authHelper.addBearerToken("test", "ROLE_USER"))
			)
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_HAL_JSON_UTF8))
			.andExpect(jsonPath("$.person.id", is(person.getId().intValue())))
			.andExpect(jsonPath("$.person.name", is(person.getName())))
			.andExpect(jsonPath("$.person.age", is(person.getAge())));
	}
	
	@Test
	public void getInvalidPerson() throws Exception {
		given(persons.findByName("test1")).willReturn(Optional.empty());
		
		mvc.perform(get("/persons/test1")
				.with(authHelper.addBearerToken("test", "ROLE_USER"))
			)
			.andExpect(status().isNotFound());
	}

}
