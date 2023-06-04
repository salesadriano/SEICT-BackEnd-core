package br.com.lamppit.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lamppit.core.model.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
    
}
