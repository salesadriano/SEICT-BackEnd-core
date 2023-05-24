package br.com.lamppit.accesscontrol.repository;

import br.com.lamppit.accesscontrol.model.ResponsibleArea;
import br.com.lamppit.accesscontrol.model.dto.EntityDTO;
import br.com.lamppit.accesscontrol.model.dto.UserRoleDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResponsibleAreaRepository extends JpaRepository<ResponsibleArea, Long> {

    @Cacheable("allAreas")
    List<ResponsibleArea> findAll();

    @Query("select distinct new br.com.lamppit.accesscontrol.model.dto.EntityDTO(ra.id, ra.nameArea) " +
            " from ResponsibleArea ra inner join ra.manager m " +
            " where m.id = :idManager ")
    Iterable<EntityDTO> getByManager(Long idManager);

    @Query("select distinct new br.com.lamppit.accesscontrol.model.dto.EntityDTO(ra.id, ra.nameArea) " +
            " from ResponsibleArea ra ")
    List<EntityDTO> getAll();

    boolean existsByManager_Id(Long id);


    @Query("select new br.com.lamppit.accesscontrol.model.dto.EntityDTO(ra.id, ra.nameArea) " +
            "from ResponsibleArea ra " +
            "inner join ra.manager m " +
            "where m.id = :idManager")
    Iterable<EntityDTO> getResponsibleAreaByManager_Id(Long idManager);

    @Query("select new br.com.lamppit.accesscontrol.model.dto.UserRoleDTO(m.id, m.name, r.name)  " +
            "from ResponsibleArea ra inner join ra.manager m " +
            "inner join Roles r on m.id = r.id " +
            "where ra.id = :responsibleAreaId")
    Iterable<UserRoleDTO> getManagerByResponsibleArea(Long responsibleAreaId);
}