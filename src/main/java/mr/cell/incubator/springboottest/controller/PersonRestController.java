package mr.cell.incubator.springboottest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mr.cell.incubator.springboottest.domain.Person;
import mr.cell.incubator.springboottest.repository.PersonRepository;

@RestController
@RequestMapping("/persons")
public class PersonRestController {

	private PersonRepository persons;
	
	public PersonRestController(PersonRepository persons) {
		this.persons = persons;
	}
	
	@GetMapping
	public List<Person> getAllPersons() {
		return persons.findAll();
	}
	
	@GetMapping("/{name}")
	public Person getPersonByName(@PathVariable String name) {
		return persons.findByName(name).orElseThrow(() -> new PersonNotFoundException(name));
	}
}
