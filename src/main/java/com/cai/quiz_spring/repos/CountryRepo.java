package com.cai.quiz_spring.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cai.quiz_spring.entities.Country;

public interface CountryRepo extends JpaRepository<Country, String> {

}
