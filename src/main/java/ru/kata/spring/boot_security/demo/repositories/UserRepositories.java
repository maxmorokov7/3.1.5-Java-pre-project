package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepositories extends JpaRepository<User, Integer> {
    @Override
    @Query(value = "select distinct u from User u left join fetch u.role")
    List<User> findAll();

    @Override
    @Query(value = "select distinct u from User u left join fetch u.role where u.userId = ?1")
    Optional<User> findById(Integer id);

    @Query(value = "select distinct u from User u left join fetch u.role where u.name = ?1")
    Optional<User> findByName(String name);
}