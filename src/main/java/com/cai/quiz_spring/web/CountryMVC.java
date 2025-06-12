package com.cai.quiz_spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.cai.quiz_spring.entities.Domanda;
import com.cai.quiz_spring.entities.GameSession;
import com.cai.quiz_spring.entities.Player;
import com.cai.quiz_spring.services.CountryService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CountryMVC {

    @Autowired
    private CountryService service;

    @GetMapping("/")
    public String home(HttpSession session) {
        // Clear any existing game session when returning to the home page
        session.removeAttribute("game");
        session.removeAttribute("quizType");
        return "home";
    }

    @GetMapping("/countries")
    public String countries(Model m) {
        m.addAttribute("title", "Quiz di Geografia");
        m.addAttribute("paesi", service.getCountries());
        return "countries";
    }


    @GetMapping("/start")
    public String start(Model m, HttpSession session) {
        // Reset the game session for a new quiz
        session.removeAttribute("game");


        String quizType = (String) session.getAttribute("quizType");
        if (quizType == null) {
            quizType = "standard"; // Default
        }

        m.addAttribute("player", new Player());
        return "start";
    }


    @GetMapping("/start-bandiera")
    public String startBandiera(Model m, HttpSession session) {
        // Reset the game session for a new quiz
        session.removeAttribute("game");

        // Set the quiz type to "bandiera"
        session.setAttribute("quizType", "bandiera");

        m.addAttribute("player", new Player());
        return "start";
    }

 
    @PostMapping("/start")
    public String postInfo(@ModelAttribute Player player, HttpSession session) {
        GameSession game = new GameSession();
        game.setPlayer(player);
        session.setAttribute("game", game);

        String quizType = (String) session.getAttribute("quizType");
        if ("bandiera".equals(quizType)) {
            return "redirect:/quiz-bandiera";
        } else {
            return "redirect:/quiz";
        }
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

 
    @GetMapping("/quiz-bandiera")
    public String quizBandiera(Model m, HttpSession session) {
        GameSession game = (GameSession) session.getAttribute("game");
        if (game == null || game.getAttempts() >= 10) {
            return "redirect:/result";
        }

        Domanda domanda = service.generaDomandaBandiere();
        m.addAttribute("game", game);
        m.addAttribute("domanda", domanda);
        m.addAttribute("title", "Quiz di Bandiere");
        return "quiz_bandiera";
    }
    @PostMapping("/submit")
    public String submitAnswer(
            @RequestParam(value = "selectedAnswer", required = false) String answer,
            @RequestParam("correctAnswer") String correctAnswer,
            Model m,
            HttpSession session) {

        GameSession game = (GameSession) session.getAttribute("game");
        if (game == null) {
            return "redirect:/start";
        }

        if (answer == null || answer.isEmpty()) {
            game.incrementAttempts();
        } else if (answer.equalsIgnoreCase(correctAnswer)) {
            game.incrementScore();
            game.incrementAttempts();
        } else {
            game.incrementAttempts();
        }

        if (game.getAttempts() < 10) {
            String quizType = (String) session.getAttribute("quizType");
            if ("bandiera".equals(quizType)) {
                return "redirect:/quiz-bandiera";
            } else {
                return "redirect:/quiz";
            }
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
}