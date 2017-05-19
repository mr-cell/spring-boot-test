package mr.cell.incubator.springboottest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mr.cell.incubator.springboottest.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
	
	Optional<Person> findByName(String name);

}
