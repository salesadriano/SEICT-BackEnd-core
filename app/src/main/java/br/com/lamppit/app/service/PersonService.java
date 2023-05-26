package br.com.lamppit.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lamppit.app.model.Person;
import br.com.lamppit.app.repository.PersonRepository;
import br.com.lamppit.core.exception.EntityValidationException;
import br.com.lamppit.core.service.ServiceJpa;
import jakarta.annotation.PostConstruct;

@Service
public class PersonService extends ServiceJpa<Person, Long> {
 
    @Autowired
    private PersonRepository personRepository;

    @Override
    public PersonRepository getRepository() {
        return personRepository;
    }

    @Override
    protected void validateSave(Person entity) throws EntityValidationException {


    }

    @Override
    protected void validateUpdate(Person entity) throws EntityValidationException {

    }

    @Override
    @PostConstruct
    protected void init() {
        setCacheKey("person.cache.key");
        setEntityKey("person");
    }
    
}
