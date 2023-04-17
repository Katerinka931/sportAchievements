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
public class SportService {
    private final SportRepository sportRepository;
    private final SportsmanRepository sportsmanRepository;
    private final TeamRepository teamRepository;

    public List<SportPojo> findAll() {
        List<Sport> sports = sportRepository.findAll();
        return SportPojo.convertSportsToPojo(sports);
    }

    public SportPojo findSportByName(String name) {
        return SportPojo.fromEntity(sportRepository.findByNameIgnoreCase(name));
    }

    public List<SportsmanPojo> findSportsmen(long pk) {
        Optional<Sport> sportOptional = sportRepository.findById(pk);
        if (sportOptional.isPresent()) {
            Sport sport = sportOptional.get();
            List<Sportsman> sportsmen = sportsmanRepository.findAllBySport(sport);
            return SportsmanPojo.convertSportsmenToPojo(sportsmen);
        }
        return null;
    }

    public List<TeamPojo> findTeams(long pk) {
        Optional<Sport> sportOptional = sportRepository.findById(pk);
        if (sportOptional.isPresent()) {
            Sport sport = sportOptional.get();
            List<Team> teams = teamRepository.findByTeamsSport(sport);
            return TeamPojo.convertTeamsToPojo(teams);
        }
        return null;
    }

    public ResponseEntity<HttpStatus> deleteSport(long id) {
        try {
            if (sportRepository.findById(id).isPresent()) {
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
        Optional<Sport> sportOptional = sportRepository.findById(sportId);
        if (sportOptional.isPresent()) {
            team.setTeamsSport(sportOptional.get());
            teamRepository.save(team);
            return TeamPojo.fromEntity(team);
        }
        return pojo;
    }

    public SportsmanPojo createSportsman(long sportId, SportsmanPojo pojo) {
        Sportsman sportsman = SportsmanPojo.toEntity(pojo);
        Optional<Sport> sportOptional = sportRepository.findById(sportId);
        if (sportOptional.isPresent()) {
            sportsman.setSport(sportOptional.get());
            sportsmanRepository.save(sportsman);
            return SportsmanPojo.fromEntity(sportsman);
        }
        return pojo;
    }

    public SportPojo updateSport(long id, SportPojo pojo) {
        Optional<Sport> sportOptional = sportRepository.findById(id);
        if (sportOptional.isPresent()) {
            Sport sport = sportOptional.get();
            sport.setName(pojo.getName());
            sportRepository.save(sport);
            return SportPojo.fromEntity(sport);
        }
        return pojo;
    }

    public TeamPojo updateTeam(long sportId, long teamId, TeamPojo pojo) {
        Optional<Sport> sportOptional = sportRepository.findById(sportId);
        Optional<Team> teamOptional = teamRepository.findById(teamId);

        if (sportOptional.isPresent() && teamOptional.isPresent()) {
            Team team = teamOptional.get();
            Sport s = sportOptional.get();

            List<Sportsman> sportsmen = sportsmanRepository.findAllByTeam(team);
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
        Optional<Sportsman> sportsman_opt = sportsmanRepository.findById(sportsmanId);
        if (sportsman_opt.isPresent()) {
            Sportsman sportsman = sportsman_opt.get();
            SportsmanPojo.setSportsmanData(sportsman, pojo);

            Team team = sportsman.getTeam();
            Optional<Sport> sportOptional = sportRepository.findById(sportId);
            if (sportOptional.isPresent()) {
                Sport sport = sportOptional.get();
                if (team != null) {
                    Optional<Sport> teamsSportOptional = sportRepository.findById(team.getTeamsSport().getId());
                    if (teamsSportOptional.isPresent()) {
                        Sport teamsSport = teamsSportOptional.get();
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
                    }
                } else {
                    sportsman.setSport(sport);
                }
            }
            sportsmanRepository.save(sportsman);
            return SportsmanPojo.fromEntity(sportsman);
        }
        return pojo;
    }

    public SportPojo findById(long id) {
        Optional<Sport> sportOptional = sportRepository.findById(id);
        return sportOptional.map(SportPojo::fromEntity).orElse(null);
    }

}

