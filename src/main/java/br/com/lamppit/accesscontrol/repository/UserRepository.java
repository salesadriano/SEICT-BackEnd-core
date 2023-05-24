package br.com.lamppit.accesscontrol.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lamppit.accesscontrol.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

	@Cacheable("allUsers")
	List<User> findAll();


	boolean existsByOffice_Id(Long id);

	boolean existsByRole_Id(Long id);


}
