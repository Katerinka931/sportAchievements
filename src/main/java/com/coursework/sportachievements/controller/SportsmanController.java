package com.coursework.sportachievements.controller;

import com.coursework.sportachievements.dto.AchievementPojo;
import com.coursework.sportachievements.dto.ContactPojo;
import com.coursework.sportachievements.dto.SportsmanPojo;
import com.coursework.sportachievements.service.AchievementService;
import com.coursework.sportachievements.service.ContactService;
import com.coursework.sportachievements.service.SportsmanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/sportsmen")
public class SportsmanController {
    private final SportsmanService sportsmanService;
    private final ContactService contactService;
    private final AchievementService achievementService;

    public SportsmanController(SportsmanService sportsmanService, ContactService contactService, AchievementService achievementService) {
        this.sportsmanService = sportsmanService;
        this.contactService = contactService;
        this.achievementService = achievementService;
    }

    @GetMapping("/main")
    public List<SportsmanPojo> findAllSportsmen() {
        return sportsmanService.findAll();
    }

    @GetMapping("/main/{pk}/contacts")
    public List<ContactPojo> findContactsBySportsman(@PathVariable long pk) {
        return sportsmanService.findContactsBySportsman(pk);
    }

    @GetMapping("/main/{pk}/achievements")
    public List<AchievementPojo> findAchievementsBySportsman(@PathVariable long pk) {
        return sportsmanService.findAchievementsBySportsman(pk);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSportsman(@PathVariable long id) {
        return sportsmanService.deleteSportsman(id);
    }

    @DeleteMapping("/contact/{id}")
    public boolean deleteContact(@PathVariable long id) {
        if (contactService.findById(id) != null) {
            return false;
        }

        contactService.deleteContact(id);
        return true;
    }

    @DeleteMapping("/achievement/{id}")
    public boolean deleteAchievement(@PathVariable long id) {
        if (achievementService.findById(id) != null) {
            return false;
        }

        achievementService.deleteAchievement(id);
        return true;
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
