package br.com.lamppit.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lamppit.app.model.Person;


public interface PersonRepository extends JpaRepository<Person, Long> {
    
}
