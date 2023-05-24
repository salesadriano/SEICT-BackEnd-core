package br.com.lamppit.accesscontrol.repository;

import br.com.lamppit.accesscontrol.model.ExtraPermissions;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtraPermissionsRepository extends JpaRepository<ExtraPermissions, Long> {

    @Cacheable("allExtraPermissions")
    List<ExtraPermissions> findAll();

    List<ExtraPermissions> findByUser_Id(Long userId, Sort id);

    boolean existsByUser_IdAndProfileAction_Id(Long id, Long id1);

}