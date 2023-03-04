package com.coursework.sportachievements.service;

import com.coursework.sportachievements.dto.SportsmanPojo;
import com.coursework.sportachievements.dto.TeamPojo;
import com.coursework.sportachievements.entity.Sportsman;
import com.coursework.sportachievements.entity.Team;
import com.coursework.sportachievements.repository.SportsmanRepository;
import com.coursework.sportachievements.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private TeamRepository teamRepository;
    private SportsmanRepository sportsmanRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, SportsmanRepository sportsmanRepository) {
        this.teamRepository = teamRepository;
        this.sportsmanRepository = sportsmanRepository;
    }

    public List<TeamPojo> findAll() {
        List<Team> teams = teamRepository.findAll();
        return TeamPojo.convertTeamsToPojo(teams);
    }

    public List<TeamPojo> findAllByName(String name) {
        List<Team> teams = teamRepository.findAllByName(name);
        return TeamPojo.convertTeamsToPojo(teams);
    }

    public List<SportsmanPojo> findSportsmen(long pk) {
        Team team = teamRepository.findById(pk);
        List<Sportsman> sportsmen = sportsmanRepository.findAllByTeam(team);
        return SportsmanPojo.convertSportsmenToPojo(sportsmen);
    }

    public List<TeamPojo> findByCount(int count) {
        List<Team> teams = teamRepository.findByCount(count);
        return TeamPojo.convertTeamsToPojo(teams);
    }

    public ResponseEntity<HttpStatus> deleteTeam(long id) {
        try {
            if (teamRepository.findById(id) != null) {
                teamRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public TeamPojo createTeam(TeamPojo teamPojo) {
        Team team = teamRepository.save(TeamPojo.toEntity(teamPojo));
        return TeamPojo.fromEntity(team);
    }

    public void updateTeam(long id, TeamPojo pojo) {
        Team team = teamRepository.findById(id);
        if (team != null) {
            team.setName(pojo.getName());
            team.setCount(pojo.getCount());
            teamRepository.save(team);
        }
    }
}
