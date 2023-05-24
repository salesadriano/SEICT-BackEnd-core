package br.com.lamppit.accesscontrol.repository;

import br.com.lamppit.accesscontrol.model.ResponsibleAreaUser;
import br.com.lamppit.accesscontrol.model.dto.EntityDTO;
import br.com.lamppit.accesscontrol.model.dto.UserRoleDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResponsibleAreaUserRepository extends JpaRepository<ResponsibleAreaUser, Long> {

    @Cacheable("allResponsibleAreaUsers")
    List<ResponsibleAreaUser> findAll();

    List<ResponsibleAreaUser> findByResponsibleArea_Id(Long id, Sort by);

    List<ResponsibleAreaUser> findByUser_Id(Long id, Sort by);

    @Query("select distinct new br.com.lamppit.accesscontrol.model.dto.UserRoleDTO(u.id, u.name, r.name) " +
            "from ResponsibleAreaUser rau inner join rau.user u " +
            "inner join Roles r on u.id = r.id " +
            "where rau.responsibleArea.id = :responsibleAreaId")
    Iterable<UserRoleDTO> getUserByResponsibleAreaId(Long responsibleAreaId);

    boolean existsByUser_Id(Long id);

    @Query("select case when count(r.id)> 0 then true else false end " +
            " from ResponsibleAreaUser r " +
            " inner join r.user u " +
            " inner join r.responsibleArea ra " +
            " where u.id = :idManager and ra.humanResources is true ")
    boolean isHumanResources(Long idManager);

}