package com.godcoder.myhome.repository;

import com.godcoder.myhome.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"boards"})
    List<User> findAll();

    User findByUsername(String username);
}
