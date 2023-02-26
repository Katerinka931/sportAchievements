package com.coursework.sportachievements.repository;

import com.coursework.sportachievements.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findBy();

    List<Team> findAllByName(String name);

    Team findById(long id);

    void deleteById(long id);
}
