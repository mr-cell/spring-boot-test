package mr.cell.incubator.springboottest.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import mr.cell.incubator.springboottest.controller.PersonRestController;
import mr.cell.incubator.springboottest.domain.Person;
import mr.cell.incubator.springboottest.repository.PersonRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonRestController.class)
public class PersonRestControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PersonRepository persons;
	
	@Test
	public void getAllPersons() throws Exception {
		List<Person> personList = Arrays.asList(new Person(1L, "test1", 20), new Person(2L, "test2", 21));
		given(persons.findAll()).willReturn(personList);
		
		mvc.perform(get("/persons"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(personList.get(0).getId().intValue())))
				.andExpect(jsonPath("$[0].name", is(personList.get(0).getName())))
				.andExpect(jsonPath("$[0].age", is(personList.get(0).getAge())))
				.andExpect(jsonPath("$[1].id", is(personList.get(1).getId().intValue())))
				.andExpect(jsonPath("$[1].name", is(personList.get(1).getName())))
				.andExpect(jsonPath("$[1].age", is(personList.get(1).getAge())));
	}
	
	@Test
	public void getValidPerson() throws Exception {
		Person person = new Person(1L, "test1", 20);
		given(persons.findByName("test1")).willReturn(Optional.of(person));
		
		mvc.perform(get("/persons/test1"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id", is(person.getId().intValue())))
			.andExpect(jsonPath("$.name", is(person.getName())))
			.andExpect(jsonPath("$.age", is(person.getAge())));
	}
	
	@Test
	public void getInvalidPerson() throws Exception {
		given(persons.findByName("test1")).willReturn(Optional.empty());
		
		mvc.perform(get("/persons/test1"))
			.andExpect(status().isNotFound());
	}

}
