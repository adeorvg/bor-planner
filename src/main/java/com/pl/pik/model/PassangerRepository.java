package com.pl.pik.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PassangerRepository extends CrudRepository<Passanger, Long> {
    List<Passanger> findAll();
}
