package br.com.lamppit.accesscontrol.service;

import br.com.lamppit.accesscontrol.model.Action;
import br.com.lamppit.accesscontrol.repository.ActionRepository;
import br.com.lamppit.core.exception.EntityValidationException;
import br.com.lamppit.core.service.ServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class ActionService extends ServiceJpa<Action, Long> {

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    ProfileActionsService profileActionsService;

    @Override
    public ActionRepository getRepository() {
        return actionRepository;
    }

    @Override
    protected void validateSave(Action entity) throws EntityValidationException {

    }

    @Override
    protected void validateUpdate(Action entity) throws EntityValidationException {

    }

    @Override
    public void validateDelete(Long id) throws EntityValidationException {
        super.validateDelete(id);

        if (profileActionsService.existsByAction_Id(id)) {
            throw new EntityValidationException(getMessageSource()
                    .getMessage("entity.delete.has.dependency", null, null));
        }
        ;

    }

    @Override
    @PostConstruct
    protected void init() {
        setCacheKey("action.cache.key");
        setEntityKey("action");

    }

    public Iterable<Action> findAllAction() {
        return getRepository().findAll(Sort.by("name"));
    }

    public Iterable<Action> findByName(String name) {
        return getRepository().findByNameIgnoreCase(name);
    }


}

