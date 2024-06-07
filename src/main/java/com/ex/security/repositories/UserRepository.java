package com.ex.security.repositories;

import com.ex.security.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, RevisionRepository<User,Long,Integer> {
    //User findByEmail(String email);

    Optional<User> findByEmail(String email);

    @Override
    void delete(User user);

}
