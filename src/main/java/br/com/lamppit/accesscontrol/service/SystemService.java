package br.com.lamppit.accesscontrol.service;

import br.com.lamppit.accesscontrol.model.Systems;
import br.com.lamppit.accesscontrol.repository.SystemRepository;
import br.com.lamppit.core.exception.EntityValidationException;
import br.com.lamppit.core.service.ServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class SystemService extends ServiceJpa<Systems, Long> {

    @Autowired
    private SystemRepository systemRepository;

    @Autowired
    private ProfileSystemsService profileSystemsService;

    @Override
    public SystemRepository getRepository() {
        return systemRepository;
    }

    @Override
    protected void validateSave(Systems entity) throws EntityValidationException {

    }

    @Override
    protected void validateUpdate(Systems entity) throws EntityValidationException {

    }

    @Override
    public void validateDelete(Long id) throws EntityValidationException {
        super.validateDelete(id);

        if (profileSystemsService.existsBySystem_Id(id)) {
            throw new EntityValidationException(getMessageSource()
                    .getMessage("entity.delete.has.dependency", null, null));
        }
        ;

    }

    @Override
    @PostConstruct
    protected void init() {
        setCacheKey("system.cache.key");
        setEntityKey("system");

    }

    public List<Systems> findAllSystems() {
        return getRepository().findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
}
