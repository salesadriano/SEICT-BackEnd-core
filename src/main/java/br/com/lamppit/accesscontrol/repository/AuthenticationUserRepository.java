package br.com.lamppit.accesscontrol.repository;

import br.com.lamppit.accesscontrol.model.AuthenticationUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthenticationUserRepository extends JpaRepository<AuthenticationUser, Long> {
    AuthenticationUser findByToken(String token);

    @Query("select a from AuthenticationUser a " +
            " where a.user.id = :id " +
            " order by a.id desc ")
    List<AuthenticationUser> getLastByUser_Id(Long id, Pageable pageable);
}