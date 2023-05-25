package br.com.lamppit.accesscontrol.service;

import br.com.lamppit.accesscontrol.model.Office;
import br.com.lamppit.accesscontrol.repository.OfficeRepository;
import br.com.lamppit.core.exception.EntityValidationException;
import br.com.lamppit.core.service.ServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class OfficeService extends ServiceJpa<Office, Long> {

    @Autowired
    OfficeRepository officeRepository;

    @Autowired
    UserService userService;


    @Override
    public OfficeRepository getRepository() {
        return officeRepository;
    }

    @Override
    protected void validateSave(Office entity) throws EntityValidationException {

    }

    @Override
    protected void validateUpdate(Office entity) throws EntityValidationException {

    }

    public void validateDelete(Long id) throws EntityValidationException {
        super.validateDelete(id);

        if (userService.existsByOffice_Id(id)) {
            throw new EntityValidationException(getMessageSource()
                    .getMessage("entity.delete.has.dependency", null, null));
        }
        ;

    }

    @Override
    @PostConstruct
    protected void init() {
        setCacheKey("office.cache.key");
        setEntityKey("office");
    }

    public List<Office> findAllOffice() {
        return getRepository().findAll(Sort.by("name"));
    }

}
