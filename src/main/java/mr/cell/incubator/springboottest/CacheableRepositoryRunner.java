package mr.cell.incubator.springboottest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import mr.cell.incubator.springboottest.repository.CacheablePersonRepository;

@Component
@Slf4j
public class CacheableRepositoryRunner implements CommandLineRunner {
	
	private CacheablePersonRepository persons;
	
	public CacheableRepositoryRunner(CacheablePersonRepository persons) {
		this.persons = persons;
	}	

	@Override
	public void run(String... args) throws Exception {
		log.info("... Fetching persons");
		log.info("test1 --> {}", persons.findByName("test1").get());
		log.info("test2 --> {}", persons.findByName("test2").get());
		log.info("test1 --> {}", persons.findByName("test1").get());
		log.info("test3 --> {}", persons.findByName("test3").get());
		log.info("test1 --> {}", persons.findByName("test1").get());		
	}
}
