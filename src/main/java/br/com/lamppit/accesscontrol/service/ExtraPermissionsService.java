package br.com.lamppit.accesscontrol.service;

import br.com.lamppit.accesscontrol.model.ExtraPermissions;
import br.com.lamppit.accesscontrol.repository.ExtraPermissionsRepository;
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
public class ExtraPermissionsService extends ServiceJpa<ExtraPermissions, Long> {

    @Autowired
    ExtraPermissionsRepository extraPermissionsRepository;

    @Override
    public ExtraPermissionsRepository getRepository() {
        return extraPermissionsRepository;
    }

    @Override
    protected void validateSave(ExtraPermissions entity) throws EntityValidationException {
        if (extraPermissionsRepository.existsByUser_IdAndProfileAction_Id(entity.getUser().getId(), entity.getProfileAction().getId())) {
            throw new EntityValidationException(getMessageSource().getMessage("extra.permission.already.exists", null, null));
        }


    }

    @Override
    protected void validateUpdate(ExtraPermissions entity) throws EntityValidationException {

    }


    @Override
    @PostConstruct
    protected void init() {
        setCacheKey("extra_permissions.cache.key");
        setEntityKey("extra_permissions");

    }

    public Iterable<ExtraPermissions> findAllExtraPermissions(Sort id) {
        return getRepository().findAll(Sort.by("profileAction.profileSystems.system.name").ascending());
    }

    public Iterable<ExtraPermissions> findByUserId(Long userId) {
        return getRepository().findByUser_Id(userId, Sort.by("profileAction.profileSystems.system.name").and(Sort.by("profileAction.profileSystems.profile.name").ascending()));
    }

    @Transactional
    public MessageDTO changeStatus(Long extraPermissionId) throws EntityNotFound {
        ExtraPermissions extraPermissions = extraPermissionsRepository.findById(extraPermissionId).
                orElseThrow(() -> new EntityNotFound(getNameEntity() + getMessageSource()
                        .getMessage("entity.notfound", null, null)));

        extraPermissions.setActive(!extraPermissions.isActive());

        if (extraPermissions.isActive()) {
            extraPermissions.setDeactivatedAt(null);
        } else {
            extraPermissions.setDeactivatedAt(LocalDateTime.now());
        }

        extraPermissionsRepository.save(extraPermissions);

        return new MessageDTO(getMessageSource().getMessage("status.active.inative", null, null));
    }


}

