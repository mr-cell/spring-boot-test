package mr.cell.incubator.springboottest.github;

import java.util.concurrent.Future;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GithubLookupService {
	
	private static final String GITHUB_USERS_API = "https://api.github.com/users/";
	
	private final RestTemplate restTemplate;
	
	public GithubLookupService(RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder.build();
	}
	
	@Async
	public Future<GithubUser> findUser(String username) throws InterruptedException {
		log.info("Looking up {}", username);
		String url = GITHUB_USERS_API + username;
		GithubUser results = restTemplate.getForObject(url, GithubUser.class);
		
		Thread.sleep(1000L);
		return new AsyncResult<GithubUser>(results);
	}

}
