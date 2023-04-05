package com.coursework.sportachievements.controller;

import com.coursework.sportachievements.dto.*;
import com.coursework.sportachievements.service.AchievementService;
import com.coursework.sportachievements.service.ContactService;
import com.coursework.sportachievements.service.SportsmanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<List<SportsmanPojo>> findAllSportsmen() {
        try {
            List<SportsmanPojo> sportsmanPojos = sportsmanService.findAll();
            return new ResponseEntity<>(sportsmanPojos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/main/{pk}/contacts")
    public ResponseEntity<List<ContactPojo>> findContactsBySportsman(@PathVariable long pk) {
        try {
            List<ContactPojo> sportsmanPojos = sportsmanService.findContactsBySportsman(pk);
            return new ResponseEntity<>(sportsmanPojos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/main/{pk}/achievements")
    public ResponseEntity<List<AchievementPojo>> findAchievementsBySportsman(@PathVariable long pk) {
        try {
            List<AchievementPojo> achievements = sportsmanService.findAchievementsBySportsman(pk);
            return new ResponseEntity<>(achievements, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/sport")
    public ResponseEntity<SportPojo> findSportBySportsman(@PathVariable long id) {
        try {
            SportPojo sportPojo = sportsmanService.findSportBySportsman(id);
            return new ResponseEntity<>(sportPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/team")
    public ResponseEntity<TeamPojo> findTeamBySportsman(@PathVariable long id) {
        try {
            TeamPojo teamPojo = sportsmanService.findTeamBySportsman(id);
            return new ResponseEntity<>(teamPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSportsman(@PathVariable long id) {
        if (sportsmanService.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return sportsmanService.deleteSportsman(id);
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable long id) {
        if (contactService.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return contactService.deleteContact(id);
    }

    @DeleteMapping("/achievement/{id}")
    public ResponseEntity<HttpStatus> deleteAchievement(@PathVariable long id) {
        if (achievementService.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return achievementService.deleteAchievement(id);
    }

    @PostMapping
    public ResponseEntity<SportsmanPojo> createSportsman(@RequestBody SportsmanPojo sportsmanPojo) {
        try {
            SportsmanPojo teamPojo = sportsmanService.createSportsman(sportsmanPojo);
            return new ResponseEntity<>(teamPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{sportsmanId}/contacts")
    public ResponseEntity<ContactPojo> createContact(@RequestBody ContactPojo pojo, @PathVariable long sportsmanId) {
        try {
            ContactPojo contactPojo = sportsmanService.createContact(sportsmanId, pojo);
            return new ResponseEntity<>(contactPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{sportsmanId}/achievements")
    public ResponseEntity<AchievementPojo> createAchievement(@RequestBody AchievementPojo pojo, @PathVariable long sportsmanId) {
        try {
            AchievementPojo achievementPojo = sportsmanService.createAchievement(sportsmanId, pojo);
            return new ResponseEntity<>(achievementPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SportsmanPojo> updateSportsman(@PathVariable long id, @RequestBody SportsmanPojo pojo) {
        try {
            SportsmanPojo sportsmanPojo = sportsmanService.updateSportsman(id, pojo);
            return new ResponseEntity<>(sportsmanPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{sportsmanId}/contacts/{contactId}")
    public ResponseEntity<ContactPojo> updateContact(@PathVariable long contactId, @PathVariable long sportsmanId, @RequestBody ContactPojo pojo) {
        try {
            ContactPojo contactPojo = sportsmanService.updateContact(contactId, sportsmanId, pojo);
            return new ResponseEntity<>(contactPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{sportsmanId}/achievements/{achievementId}")
    public ResponseEntity<AchievementPojo> updateAchievement(@PathVariable long achievementId, @PathVariable long sportsmanId, @RequestBody AchievementPojo pojo) {
        try {
            AchievementPojo achievementPojo = sportsmanService.updateAchievement(achievementId, sportsmanId, pojo);
            return new ResponseEntity<>(achievementPojo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
