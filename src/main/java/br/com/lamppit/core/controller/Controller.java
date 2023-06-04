package br.com.lamppit.core.controller;

import java.io.Serializable;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.lamppit.core.exception.EntityValidationException;
import br.com.lamppit.core.service.Service;
import jakarta.validation.Valid;

public abstract class Controller<Entity extends Serializable, TypeSequence extends java.lang.Number> {

	protected abstract Service<Entity, TypeSequence> getService();

	@PostMapping
	protected Entity newEntity(@Valid @RequestBody Entity newEntity) throws EntityValidationException {
		return getService().save(newEntity);
	}

	@PutMapping
	protected Entity replaceEntity(@Valid @RequestBody Entity newEntity) throws EntityValidationException {
		return getService().replaceEntity(newEntity);
	}

	@GetMapping("{id}")
	protected Entity one(@PathVariable TypeSequence id) throws EntityValidationException {
		return getService().getOne(id);
	}

	@DeleteMapping("{id}")
	protected void deleteEntidade(@PathVariable TypeSequence id) throws EntityValidationException {
		getService().deleteById(id);
	}
}