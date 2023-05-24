package br.com.lamppit.accesscontrol.repository;

import br.com.lamppit.accesscontrol.model.Profile;
import br.com.lamppit.accesscontrol.model.ProfileUser;
import br.com.lamppit.accesscontrol.model.Systems;
import br.com.lamppit.accesscontrol.model.dto.EntityDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfileUserRepository extends JpaRepository<ProfileUser, Long> {

    @Cacheable("allProfileUsers")
    List<ProfileUser> findAll();

    boolean existsByProfileSystems_IdAndUser_Id(Long profileSystemsId, Long userId);

    List<ProfileUser> findByUser_Id(Long userId, Sort and);

    boolean existsByUser_Id(Long id);

    @Query("select distinct ps.system from ProfileUser pu inner join pu.profileSystems ps " +
            " where pu.user.id = ?1")
    List<Systems> getSystemsByUserId(Long id);

    List<ProfileUser> findByUser_IdAndProfileSystems_System_Id(Long userId, Long id);

    @Query("select distinct new br.com.lamppit.accesscontrol.model.dto.EntityDTO(ps.system.id, ps.system.name) " +
            "from ProfileUser pu inner join pu.profileSystems ps " +
            " where pu.user.id = :userId")
    Iterable<EntityDTO> getSystemByUserId(Long userId);

    @Query("select new br.com.lamppit.accesscontrol.model.dto.EntityDTO(ps.id, ps.profile.name) " +
            "from ProfileUser pu inner join pu.profileSystems ps " +
            " where pu.user.id = :userId and ps.system.id = :systemId")
    Iterable<EntityDTO> getByUserIdAndSystemId(Long userId, Long systemId);

    @Query("select  ps.profile from ProfileUser pu inner join pu.profileSystems ps " +
            " where pu.user.id = :userId and ps.system.id = :systemId ")
    List<Profile> getProfilesByUserAndSystems(Long userId, Long systemId);
}