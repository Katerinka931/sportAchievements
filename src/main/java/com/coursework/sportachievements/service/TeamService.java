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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Team> team_opt = teamRepository.findById(id);
        if (team_opt.isPresent()) {
            Optional<Sport> sport_opt = sportRepository.findById(team_opt.get().getTeamsSport().getId());
            return sport_opt.map(SportPojo::fromEntity).orElse(null);
        }
        return null;
    }

    public TeamPojo findById(long pk) {
        Optional<Team> team_opt = teamRepository.findById(pk);
        return team_opt.map(TeamPojo::fromEntity).orElse(null);
    }

    public List<SportsmanPojo> findSportsmen(long pk) {
        Optional<Team> team_opt = teamRepository.findById(pk);
        if (team_opt.isPresent()) {
            Team team = team_opt.get();
            List<Sportsman> sportsmen = sportsmanRepository.findAllByTeam(team);
            return SportsmanPojo.convertSportsmenToPojo(sportsmen);
        }
        return null;
    }

    public List<TeamPojo> findByCount(int min, int max) {
        List<Team> teams = teamRepository.findByCountGreaterThanAndCountLessThan(min, max);
        return TeamPojo.convertTeamsToPojo(teams);
    }

    public ResponseEntity<HttpStatus> deleteTeam(long id) {
        try {
            if (teamRepository.findById(id).isPresent()) {
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
        Optional<Team> team_opt = teamRepository.findById(teamId);
        if (team_opt.isPresent()) {
            Team team = team_opt.get();
            Optional<Sport> sport_opt = sportRepository.findById(team.getTeamsSport().getId());
            if (sport_opt.isPresent()) {
                Sport sport = sport_opt.get();
                sportsman.setTeam(team);
                sportsman.setSport(sport);
                sportsmanRepository.save(sportsman);
                return SportsmanPojo.fromEntity(sportsman);
            }
            return null;
        }
        return null;
    }

    public TeamPojo updateTeam(long id, TeamPojo pojo) {
        Optional<Team> team_opt = teamRepository.findById(id);
        if (team_opt.isPresent()) {
            Team team = team_opt.get();
            team.setName(pojo.getName());
            team.setCount(pojo.getCount());
            teamRepository.save(team);
            return TeamPojo.fromEntity(team);
        }
        return pojo;
    }

    public SportsmanPojo updateSportsman(long teamId, long sportsmanId, SportsmanPojo pojo) {
        Optional<Sportsman> opt = sportsmanRepository.findById(sportsmanId);
        if (opt.isPresent()) {
            Sportsman sportsman = opt.get();
            SportsmanPojo.setSportsmanData(sportsman, pojo);
            Optional<Team> teamOptional = teamRepository.findById(teamId);
            if (teamOptional.isPresent()) {
                Team team = teamOptional.get();
                sportsman.setTeam(team);

                Sport sport = team.getTeamsSport();
                if (sport != null) {
                    Optional<Sport> sportOptional = sportRepository.findById(sport.getId());
                    sportOptional.ifPresent(sportsman::setSport);
                } else {
                    sportsman.setSport(null);
                }
                sportsmanRepository.save(sportsman);
                return SportsmanPojo.fromEntity(sportsman);
            }
        }
        return pojo;
    }
}
