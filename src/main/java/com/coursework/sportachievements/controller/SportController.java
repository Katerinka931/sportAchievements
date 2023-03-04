package com.coursework.sportachievements.controller;

import com.coursework.sportachievements.dto.SportPojo;
import com.coursework.sportachievements.dto.SportsmanPojo;
import com.coursework.sportachievements.dto.TeamPojo;
import com.coursework.sportachievements.service.SportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sport")
public class SportController {
    private final SportService sportService;

    public SportController(SportService sportService) {
        this.sportService = sportService;
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

    @PostMapping
    @ResponseBody
    public SportPojo createSport(@RequestBody SportPojo sportPojo) {
        return sportService.createSport(sportPojo);
    }

    @PostMapping("/{sportId}/team")
    public TeamPojo createTeam(@RequestBody TeamPojo pojo, @PathVariable long sportId) {
        return sportService.createTeam(sportId, pojo);
    }

    @PutMapping("/{id}")
    public void updateSport(@PathVariable long id, @RequestBody SportPojo pojo) {
        sportService.updateSport(id, pojo);
    }

    @PutMapping("/{sportId}/team/{teamId}")
    public void updateTeam(@PathVariable long sportId, @PathVariable long teamId, @RequestBody TeamPojo pojo) {
        sportService.updateTeam(sportId, teamId, pojo);
    }
}
