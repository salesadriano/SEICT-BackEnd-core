package br.com.lamppit.accesscontrol.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.lamppit.core.exception.EntityValidationException;
import br.com.lamppit.entity.EntityTest;
import br.com.lamppit.service.ServiceObj;

@SpringBootTest
public class GenericControllerTest {
	@Autowired
	ServiceObj serviceObj;

	@Test
	public void creationServiceSucceful() {
//		EntityTest entityTest = new EntityTest(1, "test");
//		serviceObj.s
		assertNotNull(serviceObj);
	}

	@Test
	public void notExistsEntity() {
		Integer id = (int) Math.random();
		EntityTest entityNotFound = serviceObj.getOne(id);
		assertFalse(entityNotFound != null);
	}

	@Test
	public void createEntityTest() {
		EntityTest entityTest = new EntityTest(1, "entity teste");
		EntityTest entitySave = serviceObj.save(entityTest);
		assertThat(entitySave.getId()).isNotNull();
	}

	@Test
	public void entityNotFound() {
		EntityTest entityTest = new EntityTest(5, "entity teste");
		assertThrows(EntityValidationException.class, () -> serviceObj.replaceEntity(entityTest));
	}
	
	@Test
	public void updateEntity() {
		EntityTest entityTest = new EntityTest(1, "entity teste atualizado");
	}

}
