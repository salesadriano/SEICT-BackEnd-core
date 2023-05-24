package br.com.lamppit.accesscontrol.service;

import br.com.lamppit.accesscontrol.model.ExtraPermissions;
import br.com.lamppit.accesscontrol.model.RevokePermissions;
import br.com.lamppit.accesscontrol.repository.RevokePermissionsRepository;
import br.com.lamppit.core.dto.MessageDTO;
import br.com.lamppit.core.exception.EntityNotFound;
import br.com.lamppit.core.exception.EntityValidationException;
import br.com.lamppit.core.service.ServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Service
public class RevokePermissionsService extends ServiceJpa<RevokePermissions, Long> {

    @Autowired
    RevokePermissionsRepository revokePermissionsRepository;

    @Override
    public RevokePermissionsRepository getRepository() {
        return revokePermissionsRepository;
    }

    @Override
    protected void validateSave(RevokePermissions entity) throws EntityValidationException {

    }

    @Override
    protected void validateUpdate(RevokePermissions entity) throws EntityValidationException {

    }


    @Override
    @PostConstruct
    protected void init() {
        setCacheKey("revoke_permissions.cache.key");
        setEntityKey("revoke_permissions");

    }

    public Iterable<RevokePermissions> findAllRevokePermissions() {
        return getRepository().findAll(Sort.by("user.name"));
    }

    public Iterable<RevokePermissions> findByUserId(Long userId) {
        return getRepository().findByUser_Id(userId);
    }

    @Transactional
    public MessageDTO changeStatus(Long revokePermissionsId) throws EntityNotFound {
        RevokePermissions revokePermissions = revokePermissionsRepository.findById(revokePermissionsId).
                orElseThrow(() -> new EntityNotFound(getNameEntity() + getMessageSource()
                        .getMessage("entity.notfound", null, null)));

        revokePermissions.setActive(!revokePermissions.isActive());

        if (revokePermissions.isActive()) {
            revokePermissions.setDeactivatedAt(null);
        } else {
            revokePermissions.setDeactivatedAt(LocalDateTime.now());
        }

        revokePermissionsRepository.save(revokePermissions);

        return new MessageDTO(getMessageSource().getMessage("status.active.inative", null, null));
    }


}

