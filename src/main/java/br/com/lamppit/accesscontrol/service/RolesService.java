package br.com.lamppit.accesscontrol.service;

import br.com.lamppit.accesscontrol.model.Roles;
import br.com.lamppit.accesscontrol.repository.RolesRepository;
import br.com.lamppit.core.exception.EntityValidationException;
import br.com.lamppit.core.service.ServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class RolesService extends ServiceJpa<Roles, Long> {

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    UserService userService;


    @Override
    public RolesRepository getRepository() {
        return rolesRepository;
    }

    @Override
    protected void validateSave(Roles entity) throws EntityValidationException {

    }

    @Override
    protected void validateUpdate(Roles entity) throws EntityValidationException {

    }

    @Override
    public void validateDelete(Long id) throws EntityValidationException {
        super.validateDelete(id);

        if (userService.existsByRole_Id(id)) {
            throw new EntityValidationException(getMessageSource()
                    .getMessage("entity.delete.has.dependency", null, null));
        }
        ;

    }

    public List<Roles> findAllRoles() {
        return getRepository().findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    @PostConstruct
    protected void init() {
        setCacheKey("roles.cache.key");
        setEntityKey("roles");

    }
}

