package br.com.lamppit.core.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.lamppit.core.dto.ErrorDTO;
import br.com.lamppit.core.dto.ExceptionDTO;
import br.com.lamppit.core.exception.BusinessValidation;
import br.com.lamppit.core.exception.EntityNotFound;
import br.com.lamppit.core.exception.EntityValidationException;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(value = EntityNotFound.class)
	protected ResponseEntity<ExceptionDTO> handleNotFound(EntityNotFound e) {
		ExceptionDTO dto = ExceptionDTO.builder().message(e.getMessage()).build();
		return new ResponseEntity<ExceptionDTO>(dto, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { EntityValidationException.class, BusinessValidation.class })
	protected ResponseEntity<ExceptionDTO> handleBusinessException(RuntimeException e) {
		ExceptionDTO dto = ExceptionDTO.builder().message(e.getMessage()).build();
		return new ResponseEntity<ExceptionDTO>(dto, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionDTO dto = ExceptionDTO.builder().message("Erro ao validar campos").build();
		List<ErrorDTO> errors = new ArrayList<>();
		for (FieldError fieldError : e.getBindingResult().getFieldErrors())
			errors.add(ErrorDTO.builder().field(fieldError.getField()).message(fieldError.getDefaultMessage()).build());
		dto.setErrors(errors);
		return new ResponseEntity<Object>(dto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = Exception.class)
	protected ResponseEntity<ExceptionDTO> handleGeneralException(Exception e) {
		System.out.println(e.getMessage());
		ExceptionDTO dto = ExceptionDTO.builder().message("Houve um erro interno").build();
		return new ResponseEntity<ExceptionDTO>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// ex.getCause().getCause().getClass() gives MyOwnWrittenException
		// the actual logic that handles the exception...
		ExceptionDTO dto = ExceptionDTO.builder().message(messageSource.getMessage(ex.getMostSpecificCause().getLocalizedMessage(),null,null)).build();
		return new ResponseEntity<Object>(dto, HttpStatus.BAD_REQUEST);
	}
}
