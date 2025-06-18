package com.cai.quiz_spring.services;

import java.util.List;

import com.cai.quiz_spring.entities.Country;
import com.cai.quiz_spring.entities.Domanda;

public interface CountryService {

    List<Country> getCountries();

    Domanda generaDomanda();

    Domanda generaDomandaBandiere(String difficolta);

    Country getRandomCountry();

}
