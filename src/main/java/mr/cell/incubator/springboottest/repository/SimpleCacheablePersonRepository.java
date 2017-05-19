package mr.cell.incubator.springboottest.repository;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import mr.cell.incubator.springboottest.domain.Person;

@Component
public class SimpleCacheablePersonRepository implements CacheablePersonRepository {

	@Cacheable("persons")
	@Override
	public Optional<Person> findByName(String name) {
		simulateSlowService();
		return Optional.of(new Person(1L, name, 21));
	}
	
	private void simulateSlowService() {
		try {
			Thread.sleep(3000L);
		} catch(InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

}
