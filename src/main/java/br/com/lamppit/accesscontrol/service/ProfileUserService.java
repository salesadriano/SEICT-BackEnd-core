package br.com.lamppit.accesscontrol.service;

import br.com.lamppit.accesscontrol.model.ProfileUser;
import br.com.lamppit.accesscontrol.model.Systems;
import br.com.lamppit.accesscontrol.model.dto.EntityDTO;
import br.com.lamppit.accesscontrol.repository.ProfileUserRepository;
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
import java.util.List;

@Service
public class ProfileUserService extends ServiceJpa<ProfileUser, Long> {

    @Autowired
    ProfileUserRepository profileUserRepository;

    @Override
    public ProfileUserRepository getRepository() {
        return profileUserRepository;
    }

    @Override
    protected void validateSave(ProfileUser entity) throws EntityValidationException {
        if(profileUserRepository.existsByProfileSystems_IdAndUser_Id(entity.getProfileSystems().getId(), entity.getUser().getId())) {
            throw new EntityValidationException(getMessageSource().getMessage("entity.save.already.exists", null, null));
        }

    }

    @Override
    protected void validateUpdate(ProfileUser entity) throws EntityValidationException {

    }


    @Override
    @PostConstruct
    protected void init() {
        setCacheKey("profile_user.cache.key");
        setEntityKey("profile_user");

    }

    public Iterable<ProfileUser> findAllProfileUser() {
        return getRepository().findAll(Sort.by("profileSystems.system.name"));
    }

    @Transactional
    public MessageDTO changeStatus(Long profileUserId) throws EntityNotFound {
        ProfileUser profileUser = profileUserRepository.findById(profileUserId).
                orElseThrow(() -> new EntityNotFound(getNameEntity() + getMessageSource()
                        .getMessage("entity.notfound", null, null)));

        profileUser.setActive(!profileUser.isActive());

        if (profileUser.isActive()) {
            profileUser.setDeactivatedAt(null);
        } else {
            profileUser.setDeactivatedAt(LocalDateTime.now());
        }

        profileUserRepository.save(profileUser);

        return new MessageDTO(getMessageSource().getMessage("status.active.inative", null, null));
    }

    public Iterable<ProfileUser> findByUserId(Long userId) {
        return profileUserRepository.findByUser_Id(userId, Sort.by("profileSystems.system.name").and(Sort.by("profileSystems.profile.name")));
    }


    public boolean existsByUser_Id(Long id) {
        return profileUserRepository.existsByUser_Id(id);
    }

    public List<Systems> getSystemsByUserId(Long userId) {

        List<Systems> systems = profileUserRepository.getSystemsByUserId(userId);

        return systems;
    }

    public List<ProfileUser> findByUser_IdAndProfileSystems_System_Id(Long userId, Long id) {
        return profileUserRepository.findByUser_IdAndProfileSystems_System_Id(userId, id);
    }

    public Iterable<EntityDTO> getSystemByUserId(Long userId) {
        return profileUserRepository.getSystemByUserId(userId);
    }

    public Iterable<EntityDTO> getByUserIdAndSystemId(Long userId, Long systemId) {
        return profileUserRepository.getByUserIdAndSystemId(userId, systemId);
    }

}

