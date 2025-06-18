package com.cai.quiz_spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.cai.quiz_spring.entities.Country;
import com.cai.quiz_spring.entities.Domanda;
import com.cai.quiz_spring.entities.GameSession;
import com.cai.quiz_spring.entities.Player;
import com.cai.quiz_spring.services.CountryService;
import com.cai.quiz_spring.services.GameService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CountryMVC {

    @Autowired
    private CountryService service;

    @Autowired
    private GameService serviceGame;

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
    public String start(Model m, HttpSession session, @RequestParam(value = "tipo", required = false) String tipo) {
        if (tipo == null){
            return "redirect:/";
        }
        session.removeAttribute("game");
        m.addAttribute("player", new Player());
        m.addAttribute("tipo", tipo);
        return "start";
    }

    @PostMapping("/start")
    public String postInfo(@ModelAttribute Player player, HttpSession session, @RequestParam("tipo") String tipo) {
        GameSession game = new GameSession();
        game.setUserName(player.getUsername());
        game.setDifficulty(player.getDifficulty());
        game.setModalita(tipo);

        session.setAttribute("game", game);

        if (tipo.equals("Bandiere")){
            return "redirect:/quiz_bandiera";
        } else {
            return "redirect:/quiz";
        }
    }

    @GetMapping("/quiz")
    public String quiz(Model m, HttpSession session) {

        GameSession game = (GameSession) session.getAttribute("game");

        if (game == null){
            return "redirect:/";
        } else if (game.getAttempts() >= 10) {
            return "redirect:/result";
        }

        Domanda domanda = service.generaDomanda();
        m.addAttribute("game", game);
        m.addAttribute("domanda", domanda);
        m.addAttribute("title", "Quiz sulle capitali");
        return "quiz";
    }

    @GetMapping("/quiz_bandiera")
    public String quizBandiera(Model m, HttpSession session) {
        GameSession game = (GameSession) session.getAttribute("game");
        if (game == null){
            return "redirect:/";
        } else if (game.getAttempts() >= 10) {
            return "redirect:/result";
        }

        Domanda domanda = service.generaDomandaBandiere(game.getDifficulty());
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
        if (answer == null || answer.isEmpty()) {
            game.incrementAttempts();
        } else if (answer.equalsIgnoreCase(correctAnswer)) {
            game.incrementScore();
            game.incrementAttempts();
        } else {
            game.incrementAttempts();
        }

        if (game.getAttempts() < 10 && game.getModalita().equals("Bandiere")) {
            return "redirect:/quiz_bandiera";
        } else if (game.getAttempts() < 10 && game.getModalita().equals("Quiz")) {
            return "redirect:/quiz";
        } else {
            m.addAttribute("game", game);
            serviceGame.addGame(game.getUserName(), game.getScore(), game.getAttempts(), game.getModalita(), game.getDifficulty());
            return "result";
        }
    }

    @GetMapping("/result")
    public String showResult(Model model, HttpSession session) {
        GameSession game = (GameSession) session.getAttribute("game");
        model.addAttribute("game", game);
        return "result";
    }

    @GetMapping("/leaderboard")
    public String showClassifica(Model m) {
        m.addAttribute("leaderboard", serviceGame.getGamesOrderScore());
        return "leaderboard";
    }

    @GetMapping("/leaderboard_bandiera")
    public String showClassificaBandiere(Model m) {
        m.addAttribute("leaderboard", serviceGame.getFlagGamesOrderScore());
        return "leaderboard_bandiera";
    }

    @GetMapping("/credits")
    public String showCredits() {
        return "credits";
    }
    
    @GetMapping("/training")
    public String training(Model m) {
        Country country = service.getRandomCountry();
        m.addAttribute("country", country);
        return "training";
    }
    

}
