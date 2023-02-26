package com.coursework.sportachievements.controller;

import com.coursework.sportachievements.dto.*;
import com.coursework.sportachievements.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    private final SportService sportService;
    private final SportsmanService sportsmanService;
    private final TeamService teamService;
    private final ContactService contactService;
    private final AchievementService achievementService;

    public Controller(SportService sportService, SportsmanService sportsmanService, TeamService teamService, ContactService contactService, AchievementService achievementService) {
        this.sportService = sportService;
        this.sportsmanService = sportsmanService;
        this.teamService = teamService;
        this.contactService = contactService;
        this.achievementService = achievementService;
    }

    @GetMapping("/sport")
    public List<SportPojo> findAllSports() {
        return sportService.findAll();
    }

    @GetMapping("/sport/{name}")
    public SportPojo findSportByName(@PathVariable String name) {
        return sportService.findSportByName(name);
    }

    @GetMapping("/sportsmen")
    public List<SportsmanPojo> findAllSportsmen() {
        return sportsmanService.findAll();
    }

    @GetMapping("/contacts")
    public List<ContactPojo> findAllContacts() {
        return contactService.findAll();
    }

    @GetMapping("/teams")
    public List<TeamPojo> findAllTeams() {
        return teamService.findAll();
    }

    @GetMapping("/achievements")
    public List<AchievementPojo> findAllAchievements() {
        return achievementService.findAll();
    }

    @DeleteMapping("/sport/{id}")
    public ResponseEntity<HttpStatus> deleteSport(@PathVariable long id) {
        return sportService.deleteSport(id);
    }

    @DeleteMapping("/team/{id}")
    public ResponseEntity<HttpStatus> deleteTeam(@PathVariable long id) {
        return teamService.deleteTeam(id);
    }

    @DeleteMapping("/sportsman/{id}")
    public ResponseEntity<HttpStatus> deleteSportsman(@PathVariable long id) {
        return sportsmanService.deleteSportsman(id);
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable long id) {
        return contactService.deleteContact(id);
    }

    @DeleteMapping("/achievement/{id}")
    public ResponseEntity<HttpStatus> deleteAchievement(@PathVariable long id) {
        return achievementService.deleteAchievement(id);
    }

    @PostMapping("/sport")
    public ResponseEntity<SportPojo> createSport(@RequestBody SportPojo sportPojo) {
        return sportService.createSport(sportPojo);
    }

    @PostMapping("/team")
    public ResponseEntity<TeamPojo> createTeam(@RequestBody TeamPojo teamPojo) {
        return teamService.createTeam(teamPojo);
    }

    @PostMapping("/sportsman")
    public ResponseEntity<SportsmanPojo> createSportsman(@RequestBody SportsmanPojo sportsmanPojo) {
        return sportsmanService.createSportsman(sportsmanPojo);
    }

    @PostMapping("/contact")
    public ResponseEntity<ContactPojo> createContact(@RequestBody ContactPojo contactPojo) {
        return contactService.createContact(contactPojo);
    }

    @PostMapping("/achievement")
    public ResponseEntity<AchievementPojo> createAchievement(@RequestBody AchievementPojo achievementPojo) {
        return achievementService.createAchievement(achievementPojo);
    }


    // todo можно ли использовать requestParam при поиске по одной и той же ссылке (или как осуществить поиск)
    //  как сделать доступ для админского контроллера?
    //  как разделять контроллеры? по ролям или по сущностям? (наверное по сущностям)
    //  как избавиться от дупликатов (например, метод delete)
}
 /*try {
            Optional<Player> playerData = playerRepository.findById(player.getId());
            if (playerData.isEmpty()) {
                if (Objects.equals(player.getFirstName(), "") || Objects.equals(player.getLastName(), "") || Objects.equals(player.getMiddleName(), "") || Objects.equals(player.getDateOfBirth(), "") || Objects.equals(player.getTeamName(), "")) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                } else {
                    Player _player = playerRepository.save(new Player(player.getTeamName(), player.getFirstName(), player.getLastName(), player.getMiddleName(), player.getDateOfBirth()));
                    return new ResponseEntity<>(_player, HttpStatus.CREATED);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
