package mr.cell.incubator.springboottest.resource;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import mr.cell.incubator.springboottest.controller.PersonRestController;
import mr.cell.incubator.springboottest.domain.Person;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class PersonResource extends ResourceSupport {
	
	private final Person person;
	
	public PersonResource(Person person) {
		this.person = person;
		add(linkTo(PersonRestController.class).withRel("persons"));
		add(linkTo(methodOn(PersonRestController.class).getPersonByName(person.getName())).withSelfRel());
	}
}
