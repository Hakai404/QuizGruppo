package com.cai.quiz_spring.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "games")
@Builder
@AllArgsConstructor
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    private String difficulty;
    private int score;
    private int attempts;
    private String modalita;

    public GameSession() {
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
