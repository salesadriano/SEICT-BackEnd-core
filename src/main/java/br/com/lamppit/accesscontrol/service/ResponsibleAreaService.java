package br.com.lamppit.accesscontrol.service;

import br.com.lamppit.accesscontrol.model.ResponsibleArea;
import br.com.lamppit.accesscontrol.model.dto.EntityDTO;
import br.com.lamppit.accesscontrol.model.dto.UserRoleDTO;
import br.com.lamppit.accesscontrol.repository.ResponsibleAreaRepository;
import br.com.lamppit.core.exception.EntityValidationException;
import br.com.lamppit.core.service.ServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class ResponsibleAreaService extends ServiceJpa<ResponsibleArea, Long> {

    @Autowired
    ResponsibleAreaRepository responsibleAreaRepository;

    @Autowired
    UserService userService;

    @Autowired
    ResponsibleAreaUserService responsibleAreaUserService;

    @Override
    public ResponsibleAreaRepository getRepository() {
        return responsibleAreaRepository;
    }

    @Override
    protected void validateSave(ResponsibleArea entity) throws EntityValidationException {

    }

    @Override
    protected void validateUpdate(ResponsibleArea entity) throws EntityValidationException {

    }

    @Override
    public void validateDelete(Long id) throws EntityValidationException {
        super.validateDelete(id);

        if (userService.existsByUser_Id(id)) {
            throw new EntityValidationException(getMessageSource()
                    .getMessage("entity.delete.has.dependency", null, null));
        }
        ;

    }

    @Override
    @PostConstruct
    protected void init() {
        setCacheKey("responsibleArea.cache.key");
        setEntityKey("responsibleArea");
    }

   public List<ResponsibleArea> findAllArea() {
        return getRepository().findAll(Sort.by("nameArea"));
    }

    public Iterable<EntityDTO> getByManager(Long idManager) {

        boolean isHumanResources = responsibleAreaUserService.isHumanResources(idManager);

        if (isHumanResources){
            return getRepository().getAll();
        }

        return getRepository().getByManager(idManager);
    }

    public Iterable<UserRoleDTO> getManagerByResponsibleArea(Long responsibleAreaId) {
        return getRepository().getManagerByResponsibleArea(responsibleAreaId);
    }
}

