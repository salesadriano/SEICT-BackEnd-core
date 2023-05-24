package br.com.lamppit.accesscontrol.repository;

import br.com.lamppit.accesscontrol.model.RevokePermissions;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RevokePermissionsRepository extends JpaRepository<RevokePermissions, Long> {

    @Cacheable("revokePermissions")
    List<RevokePermissions> findAll();

    List<RevokePermissions> findByUser_Id(Long id);
}