package com.pl.pik.hello;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

        List<User> findByLogin(String lastName);
}