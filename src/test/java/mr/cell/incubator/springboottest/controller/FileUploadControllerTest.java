package mr.cell.incubator.springboottest.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import mr.cell.incubator.springboottest.controller.FileUploadController;
import mr.cell.incubator.springboottest.storage.StorageFileNotFoundException;
import mr.cell.incubator.springboottest.storage.StorageService;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.file.Paths;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@WebMvcTest(FileUploadController.class)
public class FileUploadControllerTest {

	@Autowired 
	private MockMvc mvc;
	
	@MockBean
	private StorageService storageService;
	
	@Test
	public void shouldListAllFiles() throws Exception {
		given(storageService.loadAll())
				.willReturn(Stream.of(Paths.get("first.txt"), Paths.get("second.txt")));
		
		mvc.perform(get("/files"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("files", Matchers.contains("http://localhost/files/first.txt", "http://localhost/files/second.txt")));
	}
	
	@Test
	public void shouldSaveUploadedFile() throws Exception {
		MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "Test content".getBytes());
		mvc.perform(fileUpload("/files").file(multipartFile))
				.andExpect(status().isFound())
				.andExpect(header().string("Location", "/files"));
		
		then(storageService).should().store(multipartFile);
	}
	
	@Test
	public void should404WhenMissingFile() throws Exception {
		given(storageService.loadAsResource("test.txt"))
				.willThrow(StorageFileNotFoundException.class);
		
		mvc.perform(get("/files/test.txt"))
				.andExpect(status().isNotFound());
	}
}
