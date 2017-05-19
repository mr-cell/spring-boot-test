package mr.cell.incubator.springboottest.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import mr.cell.incubator.springboottest.controller.InputFormController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(InputFormController.class)
public class InputFormControllerTest {
	
	@Autowired
	private MockMvc mvc;

	@Test
	public void inputCorrectData() throws Exception {
		mvc.perform(post("/inputForm")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.param("name", "Christopher")
					.param("age", "21"))
				.andExpect(redirectedUrl("/results"));
	}
	
	@Test
	public void inputNoNameData() throws Exception {
		mvc.perform(post("/inputForm")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.param("age", "21"))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrors("person", "name"));
	}
	
	@Test
	public void inputNameTooShortData() throws Exception {
		mvc.perform(post("/inputForm")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.param("name", "A")
					.param("age", "21"))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrors("person", "name"));
	}
	
	@Test
	public void inputNameTooLongData() throws Exception {
		mvc.perform(post("/inputForm")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.param("name", "NameWithMoreThan30Characters!!!")
					.param("age", "21"))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrors("person", "name"));
	}
	
	@Test
	public void inputNoAgeData() throws Exception {
		mvc.perform(post("/inputForm")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.param("name", "Christopher"))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrors("person", "age"));
	}
	
	@Test
	public void inputAgeTooSmallData() throws Exception {
		mvc.perform(post("/inputForm")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.param("name", "Christopher")
					.param("age", "17"))					
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrors("person", "age"));
	}
	
	@Test
	public void inputMultipleIncorrectData() throws Exception {
		mvc.perform(post("/inputForm")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.param("name", "A")
					.param("age", "17"))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrors("person", "name", "age"));
	}
}
