package com.cai.quiz_spring.services;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cai.quiz_spring.entities.GameSession;
import com.cai.quiz_spring.repos.GameRepo;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepo repo;

    @Override
    public List<GameSession> getGames() {
        return repo.findAll();
    }

    @Override
    public void addGame(String userName, int score, int attempts, String modalita, String difficulty) {
        GameSession game = new GameSession();
        game.setUserName(userName);
        game.setScore(score);
        game.setAttempts(attempts);
        game.setModalita(modalita);
        game.setDifficulty(difficulty);
        repo.save(game);
    }

    @Override
    public List<GameSession> getGamesOrderScore() {
        return repo.findAll()
                   .stream()
                   .filter(g -> g.getModalita().equals("Quiz"))
                   .sorted((g1, g2) -> Integer.compare(g2.getScore(), g1.getScore())) 
                   .collect(Collectors.toList());
    }

    @Override
    public List<GameSession> getFlagGamesOrderScore() {
        return repo.findAll()
                   .stream()
                   .filter(g -> g.getModalita().equals("Bandiere"))
                   .sorted((g1, g2) -> Integer.compare(g2.getScore(), g1.getScore())) 
                   .collect(Collectors.toList());
    }

}
