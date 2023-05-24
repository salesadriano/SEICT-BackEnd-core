package br.com.lamppit.accesscontrol.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lamppit.accesscontrol.model.Systems;

public interface SystemRepository extends JpaRepository<Systems, Long> {

    @Cacheable("allSystems")
    public List<Systems> findAll();


}