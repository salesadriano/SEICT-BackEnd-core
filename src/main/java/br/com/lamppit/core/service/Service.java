package br.com.lamppit.core.service;

import java.io.Serializable;

import br.com.lamppit.core.exception.EntityValidationException;

public interface Service<Entity extends Serializable, TypeSequence extends java.lang.Number> {

	Entity getOne(TypeSequence id);

	Entity save(Entity newEntity) throws EntityValidationException;

	Entity replaceEntity(Entity newEntity) throws EntityValidationException;

	void deleteById(TypeSequence id) throws EntityValidationException;
	
	String getNameEntity();

}