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
    @ResponseBody
    public List<SportPojo> findAllSports() {
        return sportService.findAll();
    }

    @GetMapping("/sport/{name}")
    @ResponseBody
    public SportPojo findSportByName(@PathVariable String name) {
        return sportService.findSportByName(name);
    }

    @DeleteMapping("/sport/{id}")
    public ResponseEntity<HttpStatus> deleteSport(@PathVariable long id) {
        return sportService.deleteSport(id);
    }

    @PostMapping("/sport")
    @ResponseBody
    public SportPojo createSport(@RequestBody SportPojo sportPojo) {
        return sportService.createSport(sportPojo);
    }

    @PutMapping("/sport/{id}")
    public void updateSport(@PathVariable long id, @RequestBody SportPojo pojo) {
        sportService.updateSport(id, pojo);
    }

    @GetMapping("/team")
    public List<TeamPojo> findAllTeams() {
        return teamService.findAll();
    }

    @DeleteMapping("/team/{id}")
    public ResponseEntity<HttpStatus> deleteTeam(@PathVariable long id) {
        return teamService.deleteTeam(id);
    }

    @PostMapping("/team")
    @ResponseBody
    public TeamPojo createTeam(@RequestBody TeamPojo teamPojo) {
        return teamService.createTeam(teamPojo);
    }

    @PutMapping("/team/{id}")
    public void updateTeam(@PathVariable long id, @RequestBody TeamPojo pojo) {
        teamService.updateTeam(id, pojo);
    }

    @GetMapping("/sportsmen")
    public List<SportsmanPojo> findAllSportsmen() {
        return sportsmanService.findAll();
    }

    @DeleteMapping("/sportsman/{id}")
    public ResponseEntity<HttpStatus> deleteSportsman(@PathVariable long id) {
        return sportsmanService.deleteSportsman(id);
    }

    @PostMapping("/sportsman")
    public SportsmanPojo createSportsman(@RequestBody SportsmanPojo sportsmanPojo) {
        return sportsmanService.createSportsman(sportsmanPojo);
    }

    @PutMapping("/sportsman/{id}")
    public void updateSportsman(@PathVariable long id, @RequestBody SportsmanPojo pojo) {
        sportsmanService.updateSportsman(id, pojo);
    }

    @GetMapping("/contacts")
    public List<ContactPojo> findAllContacts() {
        return contactService.findAll();
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable long id) {
        return contactService.deleteContact(id);
    }

    @PostMapping("/contact")
    public ContactPojo createContact(@RequestBody ContactPojo contactPojo) {
        return contactService.createContact(contactPojo);
    }

    @PutMapping("/contact/{id}")
    public void updateContact(@PathVariable long id, @RequestBody ContactPojo pojo) {
        contactService.updateContact(id, pojo);
    }

    @GetMapping("/achievements")
    public List<AchievementPojo> findAllAchievements() {
        return achievementService.findAll();
    }

    @DeleteMapping("/achievement/{id}")
    public ResponseEntity<HttpStatus> deleteAchievement(@PathVariable long id) {
        return achievementService.deleteAchievement(id);
    }

    @PostMapping("/achievement")
    public AchievementPojo createAchievement(@RequestBody AchievementPojo achievementPojo) {
        return achievementService.createAchievement(achievementPojo);
    }

    @PutMapping("/achievement/{id}")
    public void updateAchievement(@PathVariable long id, @RequestBody AchievementPojo pojo) {
        achievementService.updateAchievement(id, pojo);
    }
    // todo можно ли использовать requestParam при поиске по одной и той же ссылке (или как осуществить поиск)
    //  как сделать доступ для админского контроллера?
    //  как разделять контроллеры? по ролям или по сущностям? (наверное по сущностям)
    //  как избавиться от дупликатов (например, метод delete)
    //  как лучше возвращать объект в ангуляр? @ResponseBody или ResponseEntity<>
    //  что возвращает update?
    //  проверки и ловля исключений при необходимости, заранее не писать
}
