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
public class TeamService {
    private final TeamRepository teamRepository;
    private final SportsmanRepository sportsmanRepository;
    private final SportRepository sportRepository;

    public List<TeamPojo> findAll() {
        List<Team> teams = teamRepository.findAll();
        return TeamPojo.convertTeamsToPojo(teams);
    }

    public List<TeamPojo> findAllByName(String name) {
        List<Team> teams = teamRepository.findAllByName(name);
        return TeamPojo.convertTeamsToPojo(teams);
    }

    public SportPojo findSportByTeam(long id) {
        Team team = teamRepository.findById(id);
        Sport sport = sportRepository.findById(team.getTeamsSport().getId());
        return SportPojo.fromEntity(sport);
    }

    public TeamPojo findById(long pk) {
        return TeamPojo.fromEntity(teamRepository.findById(pk));
    }

    public List<SportsmanPojo> findSportsmen(long pk) {
        Team team = teamRepository.findById(pk);
        List<Sportsman> sportsmen = sportsmanRepository.findAllByTeam(team);
        return SportsmanPojo.convertSportsmenToPojo(sportsmen);
    }

    public List<TeamPojo> findByCount(int min, int max) {
        List<Team> teams = teamRepository.findByCountGreaterThanAndCountLessThan(min, max);
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

    public SportsmanPojo createSportsman(long teamId, SportsmanPojo pojo) {
        Sportsman sportsman = SportsmanPojo.toEntity(pojo);
        Team team = teamRepository.findById(teamId);
        Sport sport = sportRepository.findById(team.getTeamsSport().getId());
        sportsman.setTeam(team);
        sportsman.setSport(sport);
        sportsmanRepository.save(sportsman);
        return SportsmanPojo.fromEntity(sportsman);
    }

    public void updateTeam(long id, TeamPojo pojo) {
        Team team = teamRepository.findById(id);
        if (team != null) {
            team.setName(pojo.getName());
            team.setCount(pojo.getCount());
            teamRepository.save(team);
        }
    }

    public void updateSportsman(long teamId, long sportsmanId, SportsmanPojo pojo) {
        Sportsman sportsman = sportsmanRepository.findById(sportsmanId);
        if (sportsman != null) {
            SportsmanPojo.setSportsmanData(sportsman, pojo);
            Team team = teamRepository.findById(teamId);
            sportsman.setTeam(team);

            Sport sport = team.getTeamsSport();
            if (sport != null) {
                sportsman.setSport(sportRepository.findById(sport.getId()));
            } else {
                sportsman.setSport(null);
            }
            sportsmanRepository.save(sportsman);
        }
    }
}
