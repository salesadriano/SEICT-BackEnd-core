package br.com.lamppit.accesscontrol.service;

import br.com.lamppit.accesscontrol.model.ProfileActions;
import br.com.lamppit.accesscontrol.model.dto.EntityDTO;
import br.com.lamppit.accesscontrol.model.dto.ProfileActionDTO;
import br.com.lamppit.accesscontrol.repository.ProfileActionsRepository;
import br.com.lamppit.accesscontrol.repository.ProfileSystemsRepository;
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
public class ProfileActionsService extends ServiceJpa<ProfileActions, Long> {

    @Autowired
    ProfileActionsRepository profileActionsRepository;

    @Autowired
    ProfileSystemsRepository profileSystemsRepository;

    @Override
    public ProfileActionsRepository getRepository() {
        return profileActionsRepository;
    }

    @Override
    protected void validateSave(ProfileActions entity) throws EntityValidationException {
        if(profileActionsRepository.existsByAction_IdAndProfileSystems_Id(entity.getAction().getId(), entity.getProfileSystems().getId())) {
            throw new EntityValidationException(getMessageSource().getMessage("entity.save.already.exists", null, null));
        }

    }

    @Override
    protected void validateUpdate(ProfileActions entity) throws EntityValidationException {

    }


    @Override
    @PostConstruct
    protected void init() {
        setCacheKey("profile_actions.cache.key");
        setEntityKey("profile_actions");

    }

    public Iterable<ProfileActions> findAllProfileActions() {
        return getRepository().findAll();
    }

    public Iterable<ProfileActions> findByProfileSystemsId(Long profileSystemsId) {
        return getRepository().findByProfileSystems_Id(profileSystemsId, Sort.by("action.name"));
    }

    @Transactional
    public MessageDTO changeStatus(Long profileActionsId) {
        ProfileActions profileActions = profileActionsRepository.findById(profileActionsId).
                orElseThrow(() -> new EntityNotFound(getNameEntity() + getMessageSource()
                        .getMessage("entity.notfound", null, null)));

        profileActions.setActive(!profileActions.isActive());

        if (profileActions.isActive()) {
            profileActions.setDeactivatedAt(null);
        } else {
            profileActions.setDeactivatedAt(LocalDateTime.now());
        }

        profileActionsRepository.save(profileActions);

        return new MessageDTO(getMessageSource().getMessage("status.active.inative", null, null));
    }

    public boolean existsBySystem_Id(Long id) {
        return profileSystemsRepository.existsBySystem_Id(id);
    }


    public boolean existsByAction_Id(Long id) {
        return profileActionsRepository.existsByAction_Id(id);
    }

    public Iterable<EntityDTO> getActionsByProfileSystemsId(Long profileSystemsId) {
        return getRepository().getActionsByProfileSystemsId(profileSystemsId);
    }

    public Iterable<ProfileActionDTO> getProfileActionsByProfileSystemsId(Long profileSystemsId) {
        return getRepository().getProfileActionsByProfileSystemsId(profileSystemsId);
    }
}

