package br.com.lamppit.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lamppit.core.model.Role;
import br.com.lamppit.core.repository.RoleRepository;
import br.com.lamppit.core.exception.EntityValidationException;
import jakarta.annotation.PostConstruct;

@Service
public class RoleService extends ServiceJpa<Role, Long> {
 
    @Autowired
    private RoleRepository personRepository;

    @Override
    public RoleRepository getRepository() {
        return personRepository;
    }

    @Override
    protected void validateSave(Role entity) throws EntityValidationException {


    }

    @Override
    protected void validateUpdate(Role entity) throws EntityValidationException {

    }

    @Override
    @PostConstruct
    protected void init() {
        setCacheKey("role.cache.key");
        setEntityKey("role");
    }
    
}
