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
        String rispostaCorretta = correctCapital;
        
        // Create a mutable list from the correct capital and wrong capitals
        List<String> risposte = new ArrayList<>();
        risposte.add(rispostaCorretta);
        risposte.addAll(wrongCapitals);

        // Shuffle the mutable list
        Collections.shuffle(risposte);

        return new Domanda(testo, rispostaCorretta, risposte);
    }
    public static Domanda fromCountryBandiera(String countryName, String correctFlag, List<String> wrongFlags) {
    String testo = "Qual è la bandiera di " + countryName + "?";

    List<String> risposte = new ArrayList<>();
    risposte.add(correctFlag);
    risposte.addAll(wrongFlags);
    Collections.shuffle(risposte);

    return new Domanda(testo, correctFlag, risposte);
}
}
