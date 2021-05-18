package edu.spingsecurity.repository;

import edu.spingsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String username);

    @Query("select u from User u join fetch u.roles where u.login = :login")
    Optional<User> findByLoginAndFetchRoles(@Param("login") String username);

}
