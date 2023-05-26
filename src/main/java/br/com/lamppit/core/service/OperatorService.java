// package br.com.lamppit.core.service;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Service;

// import br.com.lamppit.core.entity.Operator;
// import br.com.lamppit.core.exception.EntityValidationException;

// @Service
// public class OperatorService extends ServiceJpa<Operator, Number> {
	
// 	@Override
// 	public JpaRepository<Operator, Number> getRepository() {
// 		return null;
// 	}

// 	@Override
// 	protected void validateSave(Operator entidade) throws EntityValidationException {
// 	}

// 	@Override
// 	protected void validateUpdate(Operator entidade) throws EntityValidationException {
// 	}

// 	@Override
// 	protected void init() {
// 		// TODO Auto-generated method stub
// 	}
	
// 	@Override
// 	public Operator save(Operator newEntity) throws EntityValidationException {
		
// 		return super.save(newEntity);
// 	}

// }
