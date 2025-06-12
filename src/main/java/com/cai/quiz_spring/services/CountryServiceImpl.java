package com.cai.quiz_spring.services;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cai.quiz_spring.entities.Country;
import com.cai.quiz_spring.entities.Domanda;
import com.cai.quiz_spring.repos.CountryRepo;

@Service
public class CountryServiceImpl implements CountryService{

    @Autowired
    private CountryRepo repo;

    @Override
    public List<Country> getCountries() {
        return repo.findAll();
    }

    @Override
    public Domanda generaDomanda() {
        List<Country> all = repo.findAll();
        Collections.shuffle(all);
        Country correct = all.get(0);

        String correctCapital = correct.getCapital();
        String countryName = correct.getName();

        List<String> wrongCapitals = all.stream()
                .filter(c -> !c.getCapital().equals(correctCapital))
                .map(Country::getCapital)
                .distinct()
                .limit(2)
                .collect(Collectors.toList());

        return Domanda.fromCountry(countryName, correctCapital, wrongCapitals);
    }

    @Override
    public Country getRandomCountry() {
        List<Country> countries = getCountries();
        Random random = new Random();
        int randomIndex = random.nextInt(countries.size());
        Country country = countries.get(randomIndex);
        return country;
    }

}
