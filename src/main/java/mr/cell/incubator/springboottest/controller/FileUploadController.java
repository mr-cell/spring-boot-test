package mr.cell.incubator.springboottest.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mr.cell.incubator.springboottest.storage.StorageService;

@Controller
@RequestMapping("/files")
public class FileUploadController {

	private StorageService storage;
	
	public FileUploadController(StorageService storage) {
		this.storage = storage;
	}
	
	@GetMapping
	public String listUploadedFiles(Model model) throws IOException {
		model.addAttribute("files", storage
				.loadAll()
				.map(path -> 
					MvcUriComponentsBuilder.fromMethodName(
							FileUploadController.class, "serveFile", path.getFileName().toString()
					).build().toString()
				).collect(Collectors.toList()));
		return "fileUpload/uploadForm";
	}
	
	@GetMapping("/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storage.loadAsResource(filename);
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	@PostMapping
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		storage.store(file);
		redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
		return "redirect:/files";
	}
}
