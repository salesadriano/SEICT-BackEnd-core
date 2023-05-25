package br.com.lamppit.accesscontrol.service;

import br.com.lamppit.accesscontrol.model.ProfileSystems;
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
import java.util.Optional;

@Service
public class ProfileSystemsService extends ServiceJpa<ProfileSystems, Long> {

    @Autowired
    ProfileSystemsRepository profileSystemsRepository;

    @Autowired
    ProfileActionsService profileActionsService;

    @Override
    public ProfileSystemsRepository getRepository() {
        return profileSystemsRepository;
    }

    @Override
    protected void validateSave(ProfileSystems entity) throws EntityValidationException {
        if(profileSystemsRepository.existsByProfile_IdAndSystem_Id(entity.getProfile().getId(), entity.getSystem().getId())) {
            throw new EntityValidationException(getMessageSource().getMessage("entity.save.already.exists", null, null));
        }
    }

    @Override
    protected void validateUpdate(ProfileSystems entity) throws EntityValidationException {

    }


    @Override
    @PostConstruct
    protected void init() {
        setCacheKey("profile_systems.cache.key");
        setEntityKey("profile_systems");

    }


    public Iterable<ProfileSystems> findBySystem_Id(Long systemId, Sort sort) {
        return getRepository().findBySystemId(systemId, Sort.by("profile.name"));
    }


    @Transactional
    public MessageDTO changeStatus(Long profileSystemsId) {
        ProfileSystems profileSystems = profileSystemsRepository.findById(profileSystemsId).
                orElseThrow(() -> new EntityNotFound(getNameEntity() + getMessageSource()
                        .getMessage("entity.notfound", null, null)));

        if (profileSystems.isActive()) {
            profileSystems.setDeactivatedAt(null);
        } else {
            profileSystems.setDeactivatedAt(LocalDateTime.now());
        }
        profileSystems.setActive(!profileSystems.isActive());

        profileSystemsRepository.save(profileSystems);

        return new MessageDTO(getMessageSource().getMessage("status.active.inative", null, null));
    }

    @Override
    protected void validateDelete(Long id) throws EntityValidationException {
        super.validateDelete(id);

        if (profileActionsService.existsBySystem_Id(id)) {
            throw new EntityValidationException(getMessageSource()
                    .getMessage("entity.delete.has.dependency", null, null));
        }

    }

    public Optional<ProfileSystems> findByProfileId(Long id) {

        return getRepository().findByProfile_Id(id);

    }

    public boolean existsBySystem_Id(Long id) {
        return getRepository().existsBySystem_Id(id);
    }


    public boolean existsByProfile_Id(Long id) {
        return getRepository().existsByProfile_Id(id);
    }
}


