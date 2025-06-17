package com.cai.quiz_spring.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Domanda {

    private String testo;
    private String rispostaCorretta;
    private List<String> risposte;

    public static Domanda fromCountry(String countryName, String correctCapital, List<String> wrongCapitals) {
        String testo = "What is the capital of " + countryName + "?";

        List<String> risposte = new ArrayList<>();
        risposte.add(correctCapital);
        risposte.addAll(wrongCapitals);

        Collections.shuffle(risposte);

        return new Domanda(testo, correctCapital, risposte);
    }

    public static Domanda fromCountryBandiera(String countryName, String correctFlag, List<String> wrongFlags) {
        String testo = "What is the flag of " + countryName + "?";

        List<String> risposte = new ArrayList<>();
        risposte.add(correctFlag);
        risposte.addAll(wrongFlags);
        Collections.shuffle(risposte);

        return new Domanda(testo, correctFlag, risposte);
    }
}
