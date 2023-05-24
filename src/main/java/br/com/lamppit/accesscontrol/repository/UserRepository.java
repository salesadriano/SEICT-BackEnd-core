package br.com.lamppit.accesscontrol.repository;

import br.com.lamppit.accesscontrol.model.User;
import br.com.lamppit.accesscontrol.model.dto.UserBySystemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();


    boolean existsByOffice_Id(Long id);

    boolean existsByRole_Id(Long id);

    boolean existsByEmail(String email);

    User findByEmail(String email);

    @Query("select new br.com.lamppit.accesscontrol.model.dto.UserBySystemDTO(s.id, s.name) from User u " +
            " inner join ProfileUser pu on u.id = pu.user.id" +
            " inner join ProfileSystems ps on pu.profileSystems.id = ps.id " +
            " inner join Systems s on ps.system.id = s.id " +
            " where s.id = :systemId ")
    List<UserBySystemDTO> findBySystem_Id(Long systemId);
}
