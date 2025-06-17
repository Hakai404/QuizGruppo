package com.cai.quiz_spring.services;

import java.util.List;

import com.cai.quiz_spring.entities.GameSession;

public interface GameService {

    List<GameSession> getGames();

    List<GameSession> getGamesOrderScore();

    List<GameSession> getFlagGamesOrderScore();

    void addGame(String userName, int score, int attempts, String modalita, String difficulty);
}
