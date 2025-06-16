package com.cai.quiz_spring.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cai.quiz_spring.entities.GameSession;

@Repository
public interface GameRepo extends JpaRepository<GameSession, Integer>{

}
