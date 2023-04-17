package com.coursework.sportachievements.repository;

import com.coursework.sportachievements.entity.Sport;
import com.coursework.sportachievements.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findAllByName(String name);

    List<Team> findByCountGreaterThanAndCountLessThan(int count, int count2);

    List<Team> findByTeamsSport(Sport teamsSport);
}
