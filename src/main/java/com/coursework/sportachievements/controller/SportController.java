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

    @GetMapping("/sport")
    @ResponseBody
    public List<SportPojo> findAllSports() {
        return sportService.findAll();
    }

    @GetMapping("/{name}")
    @ResponseBody
    public SportPojo findSportByName(@PathVariable String name) {
        return sportService.findSportByName(name);
    }

    @GetMapping("/{pk}/sportsmen")
    public List<SportsmanPojo> findSportsmenBySport(@PathVariable long pk) {
        return sportService.findSportsmen(pk);
    }

    @GetMapping("/{pk}/teams")
    public List<TeamPojo> findTeamBySport(@PathVariable long pk) {
        return sportService.findTeams(pk);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSport(@PathVariable long id) {
        return sportService.deleteSport(id);
    }

    @DeleteMapping("/sportsmen/{id}")
    public boolean deleteSportsman(@PathVariable long id) {
        if (sportsmanService.findById(id) == null) {
            return false;
        }

        sportsmanService.deleteSportsman(id);
        return true;
    }

    @DeleteMapping("/team/{id}")
    public boolean deleteTeam(@PathVariable long id) {
        if (teamService.findById(id) == null) {
            return false;
        }

        teamService.deleteTeam(id);
        return true;
    }

    @PostMapping
    @ResponseBody
    public SportPojo createSport(@RequestBody SportPojo sportPojo) {
        return sportService.createSport(sportPojo);
    }

    @PostMapping("/{sportId}/team")
    public TeamPojo createTeam(@RequestBody TeamPojo pojo, @PathVariable long sportId) {
        return sportService.createTeam(sportId, pojo);
    }

    @PostMapping("/{sportId}/sportsman")
    public SportsmanPojo createSportsman(@PathVariable long sportId, @RequestBody SportsmanPojo pojo) {
        return sportService.createSportsman(sportId, pojo);
    }

    @PutMapping("/{id}")
    public void updateSport(@PathVariable long id, @RequestBody SportPojo pojo) {
        sportService.updateSport(id, pojo);
    }

    @PutMapping("/{sportId}/team/{teamId}")
    public void updateTeam(@PathVariable long sportId, @PathVariable long teamId, @RequestBody TeamPojo pojo) {
        sportService.updateTeam(sportId, teamId, pojo);
    }

    @PutMapping("/{sportId}/sportsman/{sportsmanId}")
    public void updateSportsman(@PathVariable long sportId, @PathVariable long sportsmanId, @RequestBody SportsmanPojo pojo) {
        sportService.updateSportsman(sportId, sportsmanId, pojo);
    }
}
