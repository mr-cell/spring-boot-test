package mr.cell.incubator.springboottest.controller;

import java.util.stream.Collectors;

import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mr.cell.incubator.springboottest.repository.PersonRepository;
import mr.cell.incubator.springboottest.resource.PersonResource;

@RestController
@RequestMapping("/persons")
public class PersonRestController {

	private PersonRepository persons;
	
	public PersonRestController(PersonRepository persons) {
		this.persons = persons;
	}
	
	@GetMapping
	public Resources<PersonResource> getAllPersons() {
		return new Resources<>(persons.findAll()
				.stream()
				.map(PersonResource::new)
				.collect(Collectors.toList()));
	}
	
	@GetMapping("/{name}")
	public PersonResource getPersonByName(@PathVariable String name) {
		return new PersonResource(persons.findByName(name)
				.orElseThrow(() -> new PersonNotFoundException(name) ));
	}
}
