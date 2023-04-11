package com.coursework.sportachievements.controller;

import com.coursework.sportachievements.dto.SportPojo;
import com.coursework.sportachievements.dto.SportsmanPojo;
import com.coursework.sportachievements.dto.TeamPojo;
import com.coursework.sportachievements.service.SportsmanService;
import com.coursework.sportachievements.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final SportsmanService sportsmanService;

    @GetMapping("/main")
    public ResponseEntity<List<TeamPojo>> findAllTeams() {
        try {
            List<TeamPojo> teamPojos = teamService.findAll();
            return new ResponseEntity<>(teamPojos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/main/{name}")
    public ResponseEntity<List<TeamPojo>> findTeamByName(@PathVariable String name) {
        try {
            List<TeamPojo> pojoList = teamService.findAllByName(name);
            return new ResponseEntity<>(pojoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/main/{pk}/sportsmen")
    public ResponseEntity<List<SportsmanPojo>> findSportsmenByTeam(@PathVariable long pk) {
        try {
            List<SportsmanPojo> sportsmanPojos = teamService.findSportsmen(pk);
            return new ResponseEntity<>(sportsmanPojos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/main/count")
    public ResponseEntity<List<TeamPojo>> findByCountOfParticipants(@RequestParam int min, @RequestParam int max) {
        try {
            List<TeamPojo> teamPojos = teamService.findByCount(min, max);
            return new ResponseEntity<>(teamPojos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/sport")
    public ResponseEntity<SportPojo> findSportByTeam(@PathVariable long id) {
        try {
            SportPojo sportPojo = teamService.findSportByTeam(id);
            return new ResponseEntity<>(sportPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTeam(@PathVariable long id) {
        if (teamService.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return teamService.deleteTeam(id);
        }
    }

    @DeleteMapping("/sportsmen/{id}")
    public ResponseEntity<HttpStatus> deleteSportsman(@PathVariable long id) {
        if (sportsmanService.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return sportsmanService.deleteSportsman(id);
    }

    @PostMapping
    public ResponseEntity<TeamPojo> createTeam(@RequestBody TeamPojo teamPojo) {
        try {
            TeamPojo pojo = teamService.createTeam(teamPojo);
            return new ResponseEntity<>(pojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("{teamId}/sportsman")
    public ResponseEntity<SportsmanPojo> createSportsman(@PathVariable long teamId, @RequestBody SportsmanPojo pojo) {
        try {
            SportsmanPojo sportsmanPojo = teamService.createSportsman(teamId, pojo);
            return new ResponseEntity<>(sportsmanPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamPojo> updateTeam(@PathVariable long id, @RequestBody TeamPojo pojo) {
        try {
            TeamPojo teamPojo = teamService.updateTeam(id, pojo);
            return new ResponseEntity<>(teamPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{teamId}/sportsman/{sportsmanId}")
    public ResponseEntity<SportsmanPojo> updateSportsman(@PathVariable long teamId, @PathVariable long sportsmanId, @RequestBody SportsmanPojo pojo) {
        try {
            SportsmanPojo sportsmanPojo = teamService.updateSportsman(teamId, sportsmanId, pojo);
            return new ResponseEntity<>(sportsmanPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
