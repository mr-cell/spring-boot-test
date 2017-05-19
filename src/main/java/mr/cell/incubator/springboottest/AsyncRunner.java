package mr.cell.incubator.springboottest;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import mr.cell.incubator.springboottest.github.GithubLookupService;
import mr.cell.incubator.springboottest.github.GithubUser;

@Component
@Slf4j
public class AsyncRunner implements CommandLineRunner {
	
	private GithubLookupService githubLookup;
	
	public AsyncRunner(GithubLookupService githubLookup) {
		this.githubLookup = githubLookup;
	}

	@Override
	public void run(String... args) throws Exception {
		long start = System.currentTimeMillis();
		
		Future<GithubUser> fut1 = githubLookup.findUser("Spring-Projects");
		Future<GithubUser> fut2 = githubLookup.findUser("mr-cell");
		Future<GithubUser> fut3 = githubLookup.findUser("CloudFoundry");
		
		while(!(fut1.isDone() && fut2.isDone() && fut3.isDone())) {
			Thread.sleep(10);
		}
		
		log.info("Elapsed time: {}", (System.currentTimeMillis() - start));
		log.info("--> {}", fut1.get());
		log.info("--> {}", fut2.get());
		log.info("--> {}", fut3.get());		
	}

}
