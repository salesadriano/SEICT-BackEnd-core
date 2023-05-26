package br.com.lamppit.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lamppit.core.model.User;
import br.com.lamppit.core.repository.UserRepository;
import br.com.lamppit.core.exception.EntityValidationException;
import jakarta.annotation.PostConstruct;

@Service
public class UserService extends ServiceJpa<User, Long> {
 
    @Autowired
    private UserRepository personRepository;

    @Override
    public UserRepository getRepository() {
        return personRepository;
    }

    @Override
    protected void validateSave(User entity) throws EntityValidationException {


    }

    @Override
    protected void validateUpdate(User entity) throws EntityValidationException {

    }

    @Override
    @PostConstruct
    protected void init() {
        setCacheKey("user.cache.key");
        setEntityKey("user");
    }
    
}
