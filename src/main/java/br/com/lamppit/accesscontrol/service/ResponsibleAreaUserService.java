package br.com.lamppit.accesscontrol.service;

import br.com.lamppit.accesscontrol.model.ResponsibleAreaUser;
import br.com.lamppit.accesscontrol.model.dto.UserRoleDTO;
import br.com.lamppit.accesscontrol.repository.ResponsibleAreaUserRepository;
import br.com.lamppit.core.dto.MessageDTO;
import br.com.lamppit.core.exception.EntityNotFound;
import br.com.lamppit.core.exception.EntityValidationException;
import br.com.lamppit.core.service.ServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;

@Service
public class ResponsibleAreaUserService extends ServiceJpa<ResponsibleAreaUser, Long> {

    @Autowired
    ResponsibleAreaUserRepository responsibleAreaUserRepository;

    @Override
    public ResponsibleAreaUserRepository getRepository() {
        return responsibleAreaUserRepository;
    }

    @Override
    protected void validateSave(ResponsibleAreaUser entity) throws EntityValidationException {

    }

    @Override
    protected void validateUpdate(ResponsibleAreaUser entity) throws EntityValidationException {

    }

    @Override
    @PostConstruct
    protected void init() {
        setCacheKey("responsibleAreaUser.cache.key");
        setEntityKey("responsibleAreaUser");
    }

    public Iterable<ResponsibleAreaUser> findAll(Sort id) {
        return getRepository().findAll(Sort.by("id"));
    }

    public Iterable<ResponsibleAreaUser> findByResponsibleArea_Id(Long id, Sort by) {
        return getRepository().findByResponsibleArea_Id(id, Sort.by("id"));
    }

    public Iterable<ResponsibleAreaUser> findByUserId(Long id) {
        return getRepository().findByUser_Id(id, Sort.by("responsibleArea.nameArea"));
    }

    @Transactional
    public MessageDTO changeStatus(Long responsibleAreaId) throws EntityNotFound {
        ResponsibleAreaUser responsibleAreaUser = responsibleAreaUserRepository.findById(responsibleAreaId).
                orElseThrow(() -> new EntityNotFound(getNameEntity() + getMessageSource()
                        .getMessage("entity.notfound", null, null)));

        responsibleAreaUser.setActive(!responsibleAreaUser.isActive());

        if (responsibleAreaUser.isActive()) {
            responsibleAreaUser.setDeactivatedAt(null);
        } else {
            responsibleAreaUser.setDeactivatedAt(LocalDateTime.now());
        }

        responsibleAreaUserRepository.save(responsibleAreaUser);

        return new MessageDTO(getMessageSource().getMessage("status.active.inative", null, null));
    }

    public Iterable<UserRoleDTO> getUserByResponsibleAreaId(Long responsibleAreaId) {
        return getRepository().getUserByResponsibleAreaId(responsibleAreaId);
    }

    public boolean isHumanResources(Long idManager) {
        return getRepository().isHumanResources(idManager);
    }
}

