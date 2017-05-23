package mr.cell.incubator.springboottest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import mr.cell.incubator.springboottest.OAuthHelper;
import mr.cell.incubator.springboottest.storage.StorageService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileUploadIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	private StorageService storageService;
	
	@Autowired
	private OAuthHelper authHelper;
	
	@LocalServerPort
	private int port;
	
	@Test
	public void shouldUploadFile() throws Exception {
		ClassPathResource resource = new ClassPathResource("fileToUpload.txt", getClass());
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "Bearer " + authHelper.getAccessTokenValue("test", "ROLE_USER"));
		
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("file", resource);
		
		ResponseEntity<String> response = restTemplate.postForEntity("/files", new HttpEntity<MultiValueMap<String, Object>>(params, headers), String.class);
		
		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.FOUND);
		assertThat(response.getHeaders().getLocation().toString()).startsWith("http://localhost:" + port + "/");
		
		then(storageService).should().store(any(MultipartFile.class));
	}
	
	@Test
	public void shouldDownloadFile() throws Exception {
		ClassPathResource resource = new ClassPathResource("fileToUpload.txt", getClass());
		String resourceContent = new String(Files.readAllBytes(Paths.get(resource.getURI())));
		given(storageService.loadAsResource("fileToUpload.txt")).willReturn(resource);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "Bearer " + authHelper.getAccessTokenValue("test", "ROLE_USER"));
		
		ResponseEntity<String> response = restTemplate.exchange("/files/{filename}", HttpMethod.GET, new HttpEntity<>(headers), String.class, "fileToUpload.txt");
		
		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION)).isEqualTo("attachment; filename=\"fileToUpload.txt\"");
		assertThat(response.getBody()).isEqualTo(resourceContent);
	}
}
