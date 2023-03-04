package com.coursework.sportachievements.controller;

import com.coursework.sportachievements.dto.AchievementPojo;
import com.coursework.sportachievements.dto.ContactPojo;
import com.coursework.sportachievements.dto.SportsmanPojo;
import com.coursework.sportachievements.service.SportsmanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sportsmen")
public class SportsmanController {
    private final SportsmanService sportsmanService;

    public SportsmanController(SportsmanService sportsmanService) {
        this.sportsmanService = sportsmanService;
    }

    @GetMapping
    public List<SportsmanPojo> findAllSportsmen() {
        return sportsmanService.findAll();
    }

    @GetMapping("/{pk}/contacts")
    public List<ContactPojo> findContactsBySportsman(@PathVariable long pk) {
        return sportsmanService.findContactsBySportsman(pk);
    }

    @GetMapping("/{pk}/achievements")
    public List<AchievementPojo> findAchievementsBySportsman(@PathVariable long pk) {
        return sportsmanService.findAchievementsBySportsman(pk);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSportsman(@PathVariable long id) {
        return sportsmanService.deleteSportsman(id);
    }

    @PostMapping
    public SportsmanPojo createSportsman(@RequestBody SportsmanPojo sportsmanPojo) {
        return sportsmanService.createSportsman(sportsmanPojo);
    }

    @PostMapping("/{sportsmanId}/contacts")
    public ContactPojo createContact(@RequestBody ContactPojo pojo, @PathVariable long sportsmanId) {
        return sportsmanService.createContact(sportsmanId, pojo);
    }

    @PostMapping("/{sportsmanId}/achievements")
    public AchievementPojo createAchievement(@RequestBody AchievementPojo pojo, @PathVariable long sportsmanId) {
        return sportsmanService.createAchievement(sportsmanId, pojo);
    }

    @PutMapping("/{id}")
    public void updateSportsman(@PathVariable long id, @RequestBody SportsmanPojo pojo) {
        sportsmanService.updateSportsman(id, pojo);
    }

    @PutMapping("/{sportsmanId}/contacts/{contactId}")
    public void updateContact(@PathVariable long contactId, @PathVariable long sportsmanId, @RequestBody ContactPojo pojo) {
        sportsmanService.updateContact(contactId, sportsmanId, pojo);
    }

    @PutMapping("/{sportsmanId}/achievements/{achievementId}")
    public void updateAchievement(@PathVariable long achievementId, @PathVariable long sportsmanId, @RequestBody AchievementPojo pojo) {
        sportsmanService.updateAchievement(achievementId, sportsmanId, pojo);
    }
}