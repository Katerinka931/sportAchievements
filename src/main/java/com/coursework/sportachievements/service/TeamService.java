package com.coursework.sportachievements.service;

import com.coursework.sportachievements.dto.TeamPojo;
import com.coursework.sportachievements.entity.Team;
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

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<TeamPojo> findAll() {
        List<Team> teams = teamRepository.findAll();
        return TeamPojo.convertTeamsToPojo(teams);
    }

    public List<TeamPojo> findAllByName(String name) {
        List<Team> teams = teamRepository.findAllByName(name);
        return TeamPojo.convertTeamsToPojo(teams);
    }

    public TeamPojo findById(long pk) {
        return TeamPojo.fromEntity(teamRepository.findById(pk));
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
        return null;
    }
}
