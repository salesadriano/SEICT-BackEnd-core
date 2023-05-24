package br.com.lamppit.accesscontrol.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lamppit.accesscontrol.model.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long> {

    @Cacheable("allRoles")
    public List<Roles> findAll();
}