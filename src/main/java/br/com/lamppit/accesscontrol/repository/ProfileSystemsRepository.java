package br.com.lamppit.accesscontrol.repository;

import br.com.lamppit.accesscontrol.model.ProfileSystems;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileSystemsRepository extends JpaRepository<ProfileSystems, Long> {
    List<ProfileSystems> findBySystemId(Long systemId, Sort by);

    Optional<ProfileSystems> findByProfile_Id(Long id);

    boolean existsBySystem_Id(Long id);

    boolean existsByProfile_Id(Long id);

    boolean existsByProfile_IdAndSystem_Id(Long id, Long id1);
}