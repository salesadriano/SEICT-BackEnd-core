package br.com.lamppit.accesscontrol.repository;

import br.com.lamppit.accesscontrol.model.ProfileActions;
import br.com.lamppit.accesscontrol.model.dto.EntityDTO;
import br.com.lamppit.accesscontrol.model.dto.ProfileActionDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfileActionsRepository extends JpaRepository<ProfileActions, Long> {

    @Cacheable("allProfileActions")
    List<ProfileActions> findAll();

    List <ProfileActions> findByProfileSystems_Id(Long profileSystemsId, Sort by);


    boolean existsByAction_IdAndProfileSystems_Id(Long id, Long id1);


    boolean existsByAction_Id(Long id);

    @Query("select new br.com.lamppit.accesscontrol.model.dto.EntityDTO(pa.action.id, pa.action.name) " +
            "from ProfileActions pa inner join pa.profileSystems ps " +
            " where ps.id = :profileSystemsId")
    Iterable<EntityDTO> getActionsByProfileSystemsId(Long profileSystemsId);

    @Query("select new br.com.lamppit.accesscontrol.model.dto.ProfileActionDTO(pa.id, a.id, a.name, p.id, p.name, s.id, s.name) " +
            "from ProfileActions pa inner join pa.action a inner join pa.profileSystems ps " +
            "inner join ps.profile p inner join ps.system s " +
            " where ps.id = :profileSystemsId")
    Iterable<ProfileActionDTO> getProfileActionsByProfileSystemsId(Long profileSystemsId);
}