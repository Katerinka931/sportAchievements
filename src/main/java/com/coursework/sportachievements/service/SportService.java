package com.coursework.sportachievements.service;

import com.coursework.sportachievements.dto.SportPojo;
import com.coursework.sportachievements.dto.SportsmanPojo;
import com.coursework.sportachievements.dto.TeamPojo;
import com.coursework.sportachievements.entity.Sport;
import com.coursework.sportachievements.entity.Sportsman;
import com.coursework.sportachievements.entity.Team;
import com.coursework.sportachievements.repository.SportRepository;
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
public class SportService {

    private SportRepository sportRepository;

    private SportsmanRepository sportsmanRepository;

    private TeamRepository teamRepository;

    @Autowired
    public SportService(SportRepository sportRepository, SportsmanRepository sportsmanRepository, TeamRepository teamRepository) {
        this.sportRepository = sportRepository;
        this.sportsmanRepository = sportsmanRepository;
        this.teamRepository = teamRepository;
    }

    public List<SportPojo> findAll() {
        List<Sport> sports = sportRepository.findBy();
        return SportPojo.convertSportsToPojo(sports);
    }

    public SportPojo findSportByName(String name) {
        return SportPojo.fromEntity(sportRepository.findByNameIgnoreCase(name));
    }

    public List<SportsmanPojo> findSportsmen(long pk) {
        Sport sport = sportRepository.findById(pk);
        List<Sportsman> sportsmen = sportsmanRepository.findAllBySport(sport);
        return SportsmanPojo.convertSportsmenToPojo(sportsmen);
    }

    public List<TeamPojo> findTeams(long pk) {
        Sport sport = sportRepository.findById(pk);
        List<Team> teams = teamRepository.findByTeamsSport(sport);
        return TeamPojo.convertTeamsToPojo(teams);
    }

    public ResponseEntity<HttpStatus> deleteSport(long id) {
        try {
            if (sportRepository.findById(id) != null) {
                sportRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public SportPojo createSport(SportPojo sportPojo) {
        Sport sport = sportRepository.save(SportPojo.toEntity(sportPojo));
        return SportPojo.fromEntity(sport);
    }

    public TeamPojo createTeam(long sportId, TeamPojo pojo) {
        Team team = TeamPojo.toEntity(pojo);
        team.setTeamsSport(sportRepository.findById(sportId));
        teamRepository.save(team);
        return TeamPojo.fromEntity(team);
    }

    public void updateSport(long id, SportPojo pojo) {
        Sport sport = sportRepository.findById(id);
        if (sport != null) {
            sport.setName(pojo.getName());
            sportRepository.save(sport);
        }
    }

    public void updateTeam(long sportId, long teamId, TeamPojo pojo) {
        Team team = teamRepository.findById(teamId);
        if (team != null) {
            team.setCount(pojo.getCount());
            team.setName(pojo.getName());
            team.setTeamsSport(sportRepository.findById(sportId));
            teamRepository.save(team);
        }
    }
}

