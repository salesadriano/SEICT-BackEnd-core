package br.com.lamppit.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lamppit.core.exception.EntityValidationException;
import br.com.lamppit.core.service.ServiceJpa;

@Service    
public class PersonService extends ServiceJpa<Person, Long> {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public PersonRepository getRepository() {
        return personRepository;
    }

    @Override
    protected void validateSave(Person entidade) throws EntityValidationException {
    }

    @Override
    protected void validateUpdate(Person entidade) throws EntityValidationException {
    }

    @Override
    protected void init() {
    }
    
}
