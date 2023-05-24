package br.com.lamppit.accesscontrol.repository;

import br.com.lamppit.accesscontrol.model.Action;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Long> {

    @Cacheable("allActions")
    List<Action> findAll();

    List<Action> findByNameIgnoreCase(String name);
}