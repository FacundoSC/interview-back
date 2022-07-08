package com.meli.interview.back.subscription_api.repository;

import com.meli.interview.back.subscription_api.datos.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Integer> {

    User findByUsername(String username);

    Optional<User> findById(Integer id);

    List<User> findAll();

    User save (User user);

    User getById(Integer id);
}
