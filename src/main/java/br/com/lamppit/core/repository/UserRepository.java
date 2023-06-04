package br.com.lamppit.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lamppit.core.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByUsername(String username);
    
}
