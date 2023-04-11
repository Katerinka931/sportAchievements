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
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportService {
    private final SportRepository sportRepository;
    private final SportsmanRepository sportsmanRepository;
    private final TeamRepository teamRepository;

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

    public SportsmanPojo createSportsman(long sportId, SportsmanPojo pojo) {
        Sportsman sportsman = SportsmanPojo.toEntity(pojo);
        sportsman.setSport(sportRepository.findById(sportId));
        sportsmanRepository.save(sportsman);
        return SportsmanPojo.fromEntity(sportsman);
    }

    public SportPojo updateSport(long id, SportPojo pojo) {
        Sport sport = sportRepository.findById(id);
        if (sport != null) {
            sport.setName(pojo.getName());
            sportRepository.save(sport);
            return SportPojo.fromEntity(sport);
        }
        return pojo;
    }

    public TeamPojo updateTeam(long sportId, long teamId, TeamPojo pojo) {
        Team team = teamRepository.findById(teamId);
        List<Sportsman> sportsmen = sportsmanRepository.findAllByTeam(team);
        Sport s = sportRepository.findById(sportId);
        if (team != null) {
            team.setCount(pojo.getCount());
            team.setName(pojo.getName());
            team.setTeamsSport(s);
            teamRepository.save(team);
            updateAllSportsmenOfTeam(sportsmen, s);
            return TeamPojo.fromEntity(team);
        }
        return pojo;
    }

    private void updateAllSportsmenOfTeam(List<Sportsman> sportsmen, Sport sport) {
        if (sportsmen != null) {
            for (Sportsman s : sportsmen) {
                s.setSport(sport);
                sportsmanRepository.save(s);
            }
        }
    }

    public SportsmanPojo updateSportsman(long sportId, long sportsmanId, SportsmanPojo pojo) {
        Sportsman sportsman = sportsmanRepository.findById(sportsmanId);
        if (sportsman != null) {
            SportsmanPojo.setSportsmanData(sportsman, pojo);

            Team team = sportsman.getTeam();
            Sport sport = sportRepository.findById(sportId);

            if (team != null) {
                Sport teamsSport = sportRepository.findById(team.getTeamsSport().getId());
                if (sportsman.getSport().getId() != sportId) {
                    sportsman.setTeam(null);
                    sportsman.setSport(sport);
                } else {
                    if (teamsSport.getId() == sport.getId()) {
                        sportsman.setSport(sport);
                    } else {
                        sportsman.setSport(team.getTeamsSport());
                    }
                }
            } else {
                sportsman.setSport(sport);
            }
            sportsmanRepository.save(sportsman);
            return SportsmanPojo.fromEntity(sportsman);
        }
        return pojo;
    }

    public SportPojo findById(long id) {
        return SportPojo.fromEntity(sportRepository.findById(id));
    }

}

