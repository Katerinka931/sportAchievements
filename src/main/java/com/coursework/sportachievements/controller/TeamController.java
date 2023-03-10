package com.coursework.sportachievements.controller;

import com.coursework.sportachievements.dto.SportsmanPojo;
import com.coursework.sportachievements.dto.TeamPojo;
import com.coursework.sportachievements.service.SportsmanService;
import com.coursework.sportachievements.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/team")
public class TeamController {
    private final TeamService teamService;
    private final SportsmanService sportsmanService;

    public TeamController(TeamService teamService, SportsmanService sportsmanService) {
        this.teamService = teamService;
        this.sportsmanService = sportsmanService;
    }

    @GetMapping("/main")
    public List<TeamPojo> findAllTeams() {
        return teamService.findAll();
    }

    @GetMapping("/main/{name}")
    public List<TeamPojo> findTeamByName(@PathVariable String name) {
        return teamService.findAllByName(name);
    }

    @GetMapping("/main/{pk}/sportsmen")
    public List<SportsmanPojo> findSportsmenByTeam(@PathVariable long pk) {
        return teamService.findSportsmen(pk);
    }

    @GetMapping("/main/count")
    public List<TeamPojo> findByCountOfParticipants(@RequestParam int min, @RequestParam int max) {
        return teamService.findByCount(min, max);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTeam(@PathVariable long id) {
        return teamService.deleteTeam(id);
    }

    @DeleteMapping("/sportsmen/{id}")
    public boolean deleteSportsman(@PathVariable long id) {
        if (sportsmanService.findById(id) == null) {
            return false;
        }

        sportsmanService.deleteSportsman(id);
        return true;
    }

    @PostMapping
    @ResponseBody
    public TeamPojo createTeam(@RequestBody TeamPojo teamPojo) {
        return teamService.createTeam(teamPojo);
    }

    @PostMapping("{teamId}/sportsman")
    public SportsmanPojo createSportsman(@PathVariable long teamId, @RequestBody SportsmanPojo pojo) {
        return teamService.createSportsman(teamId, pojo);
    }

    @PutMapping("/{id}")
    public void updateTeam(@PathVariable long id, @RequestBody TeamPojo pojo) {
        teamService.updateTeam(id, pojo);
    }

    @PutMapping("/{teamId}/sportsman/{sportsmanId}")
    public void updateSportsman(@PathVariable long teamId, @PathVariable long sportsmanId, @RequestBody SportsmanPojo pojo) {
        teamService.updateSportsman(teamId, sportsmanId, pojo);
    }
}
