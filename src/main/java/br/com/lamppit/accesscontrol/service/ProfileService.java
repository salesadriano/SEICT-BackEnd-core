package br.com.lamppit.accesscontrol.service;

import br.com.lamppit.accesscontrol.model.Profile;
import br.com.lamppit.accesscontrol.repository.ProfileRepository;
import br.com.lamppit.core.exception.EntityValidationException;
import br.com.lamppit.core.service.ServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class ProfileService extends ServiceJpa<Profile, Long> {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    ProfileSystemsService profileSystemsService;


    @Override
    public ProfileRepository getRepository() {
        return profileRepository;
    }

    @Override
    protected void validateSave(Profile entity) throws EntityValidationException {


    }

    @Override
    protected void validateUpdate(Profile entity) throws EntityValidationException {

    }

    @Override
    @PostConstruct
    protected void init() {
        setCacheKey("profile.cache.key");
        setEntityKey("profile");
    }

    public Iterable<Profile> findAll() {
        return getRepository().findAll(Sort.by("name"));
    }

    public Iterable<Profile> findByName(String name) {
        return getRepository().findByNameIgnoreCaseLike(name);
    }

    @Override
    public void validateDelete(Long id) throws EntityValidationException {
        super.validateDelete(id);

        if (profileSystemsService.existsByProfile_Id(id)) {
            throw new EntityValidationException(getMessageSource()
                    .getMessage("entity.delete.has.dependency", null, null));
        }
        ;

    }
}

