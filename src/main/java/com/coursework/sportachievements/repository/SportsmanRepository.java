package com.coursework.sportachievements.repository;

import com.coursework.sportachievements.entity.Sport;
import com.coursework.sportachievements.entity.Sportsman;
import com.coursework.sportachievements.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SportsmanRepository extends JpaRepository<Sportsman, Long> {

    List<Sportsman> findAllBySport(Sport sport);

    List<Sportsman> findAllByTeam(Team team);
}
