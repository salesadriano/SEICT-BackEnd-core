package br.com.lamppit.accesscontrol.repository;

import br.com.lamppit.accesscontrol.model.Office;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficeRepository extends JpaRepository<Office, Long> {



    @Cacheable("allOffices")
    public List<Office> findAll();
}