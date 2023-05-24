package br.com.lamppit.core.service;

import br.com.lamppit.core.exception.EntityNotFound;
import br.com.lamppit.core.exception.EntityValidationException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public abstract class ServiceJpa<Entity extends Serializable, TypeSequence extends java.lang.Number>
        implements Service<Entity, TypeSequence> {

    public abstract JpaRepository<Entity, TypeSequence> getRepository();

    protected abstract void validateSave(Entity entidade) throws EntityValidationException;

    protected abstract void validateUpdate(Entity entidade) throws EntityValidationException;

    protected void validateDelete(TypeSequence id) throws EntityValidationException {
        getRepository().findById(id).orElseThrow(() -> new EntityNotFound(getNameEntity() + " não encontrado"));
    }


    protected abstract void init();

    @Autowired
    private MessageSource messageSource;

    private List<String> cacheList;

    @Autowired
    private CachingService cachingService;

    private String cacheKey;

    private String entityKey;

    @Override
    public String getNameEntity() {
        return messageSource.getMessage(entityKey, null, null);
    }

    @Override
    @Transactional
    public Entity getOne(TypeSequence id) {
        return getRepository().findById(id).orElseThrow(() -> new EntityNotFound(getNameEntity() + " não encontrado"));
    }

    @Override
    @Transactional
    public Entity save(Entity newEntity) throws EntityValidationException {
        validateSave(newEntity);
        try {
            newEntity = getRepository().save(newEntity);
        } catch (DataIntegrityViolationException e) {
            throw new EntityValidationException(e.getLocalizedMessage());
        }
        clearCacheKey();
        return newEntity;
    }

    @Override
    @Transactional
    public Entity replaceEntity(Entity newEntity) throws EntityValidationException {
        validateUpdate(newEntity);
        try {
            newEntity = getRepository().save(newEntity);
        } catch (DataIntegrityViolationException e) {
            throw new EntityValidationException(e.getLocalizedMessage());
        }
        clearCacheKey();
        return newEntity;
    }

    @Override
    @Transactional
    public void deleteById(TypeSequence id) throws EntityValidationException {
        validateDelete(id);
        getRepository().deleteById(id);
        clearCacheKey();
    }

    protected void clearCache() {
        if (getCacheList() != null) {
            for (String nameCache : getCacheList()) {
                cachingService.evictAllCacheValues(nameCache);
            }
        }
    }

    protected void clearCacheKey() {
        if (cacheKey != null) {
            String cacheValue = messageSource.getMessage(cacheKey, null, null);
            if (cacheValue != null) {
                String[] cacheNames = cacheValue.split(",");
                for (String cacheName : cacheNames) {
                    cachingService.evictAllCacheValues(cacheName);
                }
            }
        }
    }

}
