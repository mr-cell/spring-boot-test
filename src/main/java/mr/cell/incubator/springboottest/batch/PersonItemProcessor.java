package mr.cell.incubator.springboottest.batch;

import org.springframework.batch.item.ItemProcessor;

import lombok.extern.slf4j.Slf4j;
import mr.cell.incubator.springboottest.domain.Person;

@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

	@Override
	public Person process(Person person) throws Exception {
		String name = person.getName().toUpperCase();
		Integer age = person.getAge() + 1;
		
		Person transformedPerson = new Person(name, age);
		
		log.info("Converting ({}) into ({})", person, transformedPerson);
		
		return transformedPerson;
	}
}
