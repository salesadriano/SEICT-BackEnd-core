package br.com.lamppit.service;

import java.util.ArrayList;
import java.util.List;

import br.com.lamppit.core.exception.EntityValidationException;
import br.com.lamppit.core.service.Service;
import br.com.lamppit.entity.EntityTest;

@org.springframework.stereotype.Service
public class ServiceObj implements Service<EntityTest, Number> {
	private List<EntityTest> entities = new ArrayList<>();

	@Override
	public EntityTest save(EntityTest newEntity) throws EntityValidationException {
		newEntity.setId(entities.size() + 1);
		entities.add(newEntity);
		return newEntity;
	}

	@Override
	public EntityTest replaceEntity(EntityTest newEntidade) throws EntityValidationException {
		EntityTest entity = getOne(newEntidade.getId());
		if (entity != null) {
			int index = entities.indexOf(entity);
			entities.set(index, newEntidade);
			return newEntidade;
		} else
			throw new EntityValidationException("");
	}

	@Override
	public void deleteById(Number id) throws EntityValidationException {
		EntityTest entity = getOne(id);
		if (entity != null) {
			int index = entities.indexOf(entity);
			entities.remove(index);
		} else
			throw new EntityValidationException("");

	}

	@Override
	public EntityTest getOne(Number id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNameEntity() {
		// TODO Auto-generated method stub
		return null;
	}

}
