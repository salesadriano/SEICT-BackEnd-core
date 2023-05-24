package br.com.lamppit.accesscontrol.repository;

import br.com.lamppit.accesscontrol.model.Profile;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Cacheable("allProfiles")
    List<Profile> findAll();

    List<Profile> findByNameIgnoreCaseLike(String name);


}
