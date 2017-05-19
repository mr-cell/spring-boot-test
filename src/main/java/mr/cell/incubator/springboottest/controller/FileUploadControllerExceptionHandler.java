package mr.cell.incubator.springboottest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import mr.cell.incubator.springboottest.storage.StorageFileNotFoundException;

@ControllerAdvice
@Slf4j
public class FileUploadControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(MultipartException.class)
	public ResponseEntity handleMultipartException(MultipartException e) {
		log.error("multipart exception handler");
		return ResponseEntity.badRequest().build();
	}
	
	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exception) {
		return ResponseEntity.notFound().build();
	}
}
