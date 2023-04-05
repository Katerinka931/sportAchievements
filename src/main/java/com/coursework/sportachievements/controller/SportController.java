package com.coursework.sportachievements.controller;

import com.coursework.sportachievements.dto.SportPojo;
import com.coursework.sportachievements.dto.SportsmanPojo;
import com.coursework.sportachievements.dto.TeamPojo;
import com.coursework.sportachievements.service.SportService;
import com.coursework.sportachievements.service.SportsmanService;
import com.coursework.sportachievements.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/sport")
public class SportController {
    private final SportService sportService;
    private final SportsmanService sportsmanService;
    private final TeamService teamService;

    public SportController(SportService sportService, SportsmanService sportsmanService, TeamService teamService) {
        this.sportService = sportService;
        this.sportsmanService = sportsmanService;
        this.teamService = teamService;
    }

    @GetMapping("/main")
    public ResponseEntity<List<SportPojo>> findAllSports() {
        try {
            List<SportPojo> sportPojoList = sportService.findAll();
            return new ResponseEntity<>(sportPojoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/main/{name}")
    public ResponseEntity<SportPojo> findSportByName(@PathVariable String name) {
        try {
            SportPojo sportPojo = sportService.findSportByName(name);
            return new ResponseEntity<>(sportPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/main/{pk}/sportsmen")
    public ResponseEntity<List<SportsmanPojo>> findSportsmenBySport(@PathVariable long pk) {
        try {
            List<SportsmanPojo> sportsmanPojo = sportService.findSportsmen(pk);
            return new ResponseEntity<>(sportsmanPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/main/{pk}/teams")
    public ResponseEntity<List<TeamPojo>> findTeamBySport(@PathVariable long pk) {
        try {
            List<TeamPojo> teamPojo = sportService.findTeams(pk);
            return new ResponseEntity<>(teamPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSport(@PathVariable long id) {
        if (sportService.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return sportService.deleteSport(id);
        }
    }

    @DeleteMapping("/sportsmen/{id}")
    public ResponseEntity<HttpStatus> deleteSportsman(@PathVariable long id) {
        if (sportsmanService.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return sportsmanService.deleteSportsman(id);
        }
    }

    @DeleteMapping("/team/{id}")
    public ResponseEntity<HttpStatus> deleteTeam(@PathVariable long id) {
        if (teamService.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return teamService.deleteTeam(id);
        }
    }

    @PostMapping
    public ResponseEntity<SportPojo> createSport(@RequestBody SportPojo sportPojo) {
        try {
            SportPojo sport = sportService.createSport(sportPojo);
            return new ResponseEntity<>(sport, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{sportId}/team")
    public ResponseEntity<TeamPojo> createTeam(@RequestBody TeamPojo pojo, @PathVariable long sportId) {
        try {
            TeamPojo teamPojo = sportService.createTeam(sportId, pojo);
            return new ResponseEntity<>(teamPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{sportId}/sportsman")
    public ResponseEntity<SportsmanPojo> createSportsman(@PathVariable long sportId, @RequestBody SportsmanPojo pojo) {
        try {
            SportsmanPojo sportsmanPojo = sportService.createSportsman(sportId, pojo);
            return new ResponseEntity<>(sportsmanPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SportPojo> updateSport(@PathVariable long id, @RequestBody SportPojo pojo) {
        try {
            SportPojo sportPojo = sportService.updateSport(id, pojo);
            return new ResponseEntity<>(sportPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{sportId}/team/{teamId}")
    public ResponseEntity<TeamPojo> updateTeam(@PathVariable long sportId, @PathVariable long teamId, @RequestBody TeamPojo pojo) {
        try {
            TeamPojo teamPojo = sportService.updateTeam(sportId, teamId, pojo);
            return new ResponseEntity<>(teamPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{sportId}/sportsman/{sportsmanId}")
    public ResponseEntity<SportsmanPojo> updateSportsman(@PathVariable long sportId, @PathVariable long sportsmanId, @RequestBody SportsmanPojo pojo) {
        try {
            SportsmanPojo sportsmanPojo = sportService.updateSportsman(sportId, sportsmanId, pojo);
            return new ResponseEntity<>(sportsmanPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
