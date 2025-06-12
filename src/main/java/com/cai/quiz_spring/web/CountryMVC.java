package com.cai.quiz_spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cai.quiz_spring.entities.Country;
import com.cai.quiz_spring.entities.Domanda;
import com.cai.quiz_spring.entities.GameSession;
import com.cai.quiz_spring.entities.Player;
import com.cai.quiz_spring.services.CountryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CountryMVC {

    @Autowired
    private CountryService service;

    @GetMapping("countries")
    public String countries(Model m) {
        m.addAttribute("title", "Quiz di Geografia");
        m.addAttribute("paesi", service.getCountries());
        return "countries";
    }

    @GetMapping("")
    public String home() {
        return "home";
    }

    @GetMapping("/start")
    public String start(Model m) {
        m.addAttribute("player", new Player());
        return "start";
    }

    @PostMapping("/start")
    public String postInfo(@ModelAttribute Player player, HttpSession session) {
        GameSession game = new GameSession();
        game.setPlayer(player);

        session.setAttribute("game", game);
        return "redirect:/quiz";
    }

    @GetMapping("/quiz")
    public String quiz(Model m, HttpSession session) {

        GameSession game = (GameSession) session.getAttribute("game");
        if (game == null || game.getAttempts() >= 10) {
            return "redirect:/result";
        }

        Domanda domanda = service.generaDomanda();
        m.addAttribute("game", game);
        m.addAttribute("domanda", domanda);
        m.addAttribute("title", "Quiz sui Paesi del Mondo");
        return "quiz";
    }

    @PostMapping("/submit")
    public String submitAnswer(
            @RequestParam(value = "selectedAnswer", required = false) String answer,
            @RequestParam("correctAnswer") String correctAnswer,
            Model m,
            HttpSession session) {

        GameSession game = (GameSession) session.getAttribute("game");
        if (answer == null || answer.isEmpty()) {
            game.incrementAttempts();
        } else if (answer.equalsIgnoreCase(correctAnswer)) {
            game.incrementScore();
            game.incrementAttempts();
        } else {
            game.incrementAttempts();
        }

        if (game.getAttempts() < 10) {
            return "redirect:/quiz";
        } else {
            m.addAttribute("game", game);
            return "result";
        }
    }

    @GetMapping("/result")
    public String showResult(Model model, HttpSession session) {
        GameSession game = (GameSession) session.getAttribute("game");
        model.addAttribute("game", game);
        return "result";
    }

    @GetMapping("/training")
    public String training(Model m) {
        Country country = service.getRandomCountry();
        m.addAttribute("country", country);
        return "training";
    }
    

}
