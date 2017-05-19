package mr.cell.incubator.springboottest.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import mr.cell.incubator.springboottest.domain.Person;

@Controller
@RequestMapping("/inputForm")
public class InputFormController extends WebMvcConfigurerAdapter {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/results").setViewName("/inputForm/results");
	}
	
	@GetMapping
	public String showForm(Person person) {
		return "inputForm/form";
	}
	
	@PostMapping
	public String checkPersonInfo(@Valid Person person, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "inputForm/form";
		}
		
		return "redirect:/results";
	}
}
