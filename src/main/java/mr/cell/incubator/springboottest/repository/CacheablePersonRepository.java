package mr.cell.incubator.springboottest.repository;

import java.util.Optional;

import mr.cell.incubator.springboottest.domain.Person;

public interface CacheablePersonRepository {
	
	Optional<Person> findByName(String name);

}
