package com.cai.quiz_spring.entities;

import lombok.Data;

@Data
public class GameSession {

    private Player player;
    private int score;
    private int attempts;
    private Domanda domanda;

    public GameSession() {
        this.player = new Player();
        this.score = 0;
        this.attempts = 0;
    }
    public void incrementScore() {
        this.score++;
    }
    public void incrementAttempts() {
        this.attempts++;
    }
}
