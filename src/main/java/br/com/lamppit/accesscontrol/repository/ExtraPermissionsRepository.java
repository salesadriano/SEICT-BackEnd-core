package br.com.lamppit.accesscontrol.repository;

import br.com.lamppit.accesscontrol.model.ExtraPermissions;
import br.com.lamppit.accesscontrol.model.dto.ActionPermissionDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExtraPermissionsRepository extends JpaRepository<ExtraPermissions, Long> {

    @Cacheable("allExtraPermissions")
    List<ExtraPermissions> findAll();

    List<ExtraPermissions> findByUser_Id(Long userId, Sort id);

    boolean existsByUser_IdAndProfileAction_Id(Long id, Long id1);

    @Query("select new br.com.lamppit.accesscontrol.model.dto.ActionPermissionDTO(pa.action, pa.isertable, pa.updatable, pa.deletable, pa.readable, pa.auditable)" +
            "from ExtraPermissions ep " +
            "inner join ep.profileAction pa " +
            "inner join ep.user u " +
            "inner join pa.profileSystems ps " +
            "inner join ps.system sy " +
            "where u.id = :userId and sy.id = :systemId")
    List<ActionPermissionDTO> getExtraPermissionsByUserAndSystems(Long userId, Long systemId);
}