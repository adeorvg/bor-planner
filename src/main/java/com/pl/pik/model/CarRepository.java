package com.pl.pik.model;

import java.util.List;

public class CarRepository {
    List<User> findByRegistrationNumber(String registrationNumber);

    @Query("SELECT a FROM Article a WHERE a.title=:title and a.category=:category")
    List<Article> freeCars(@Param("title") String title, @Param("category") String category);
}
