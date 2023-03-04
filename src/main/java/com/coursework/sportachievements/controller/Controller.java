package com.coursework.sportachievements.controller;

import com.coursework.sportachievements.dto.*;
import com.coursework.sportachievements.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @PostMapping("/sport") //todo return with id
    @ResponseBody
    public SportPojo createSport(@RequestBody SportPojo sportPojo) {
        return sportService.createSport(sportPojo);
    }

    @PutMapping("/sport/{id}")
    public void updateSport(@PathVariable long id, @RequestBody SportPojo pojo) {
        sportService.updateSport(id, pojo);
    }

    //-------------
    @GetMapping("/team")
    public List<TeamPojo> findAllTeams() {
        return teamService.findAll();
    }

    @GetMapping("/team/{name}")
    public List<TeamPojo> findTeamByName(@PathVariable String name) {
        return teamService.findAllByName(name);
    }

    @DeleteMapping("/team/{id}")
    public ResponseEntity<HttpStatus> deleteTeam(@PathVariable long id) {
        return teamService.deleteTeam(id);
    }

    @PostMapping("/team")//todo return with id
    @ResponseBody
    public TeamPojo createTeam(@RequestBody TeamPojo teamPojo) {
        return teamService.createTeam(teamPojo);
    }

    @PutMapping("/team/{id}")
    public void updateTeam(@PathVariable long id, @RequestBody TeamPojo pojo) {
        teamService.updateTeam(id, pojo);
    }

    //-------------
    @GetMapping("/sportsmen")
    public List<SportsmanPojo> findAllSportsmen() {
        return sportsmanService.findAll();
    }

    @DeleteMapping("/sportsmen/{id}")
    public ResponseEntity<HttpStatus> deleteSportsman(@PathVariable long id) {
        return sportsmanService.deleteSportsman(id);
    }

    @PostMapping("/sportsmen")//todo return with id
    public SportsmanPojo createSportsman(@RequestBody SportsmanPojo sportsmanPojo) {
        return sportsmanService.createSportsman(sportsmanPojo);
    }

    @PutMapping("/sportsmen/{id}")
    public void updateSportsman(@PathVariable long id, @RequestBody SportsmanPojo pojo) {
        sportsmanService.updateSportsman(id, pojo);
    }

    //------------- Contacts
    @GetMapping("/contacts")
    public List<ContactPojo> findAllContacts() {
        return contactService.findAll();
    }

    @GetMapping("/contacts/phone")
    public List<ContactPojo> findContactsByPhoneContaining(@RequestParam String phone) {
        return contactService.findByPhone(phone);
    }

    @GetMapping("/contacts/email")
    public List<ContactPojo> findContactsByEmailContaining(@RequestParam String email) {
        return contactService.findByEmail(email);
    }

    @GetMapping("/contacts/sportsman/{pk}")
    public List<ContactPojo> findContactsBySportsman(@PathVariable long pk) {
        return sportsmanService.findContactsBySportsman(pk);
    }

    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable long id) {
        return contactService.deleteContact(id);
    }

    @PostMapping("/contacts")//todo return with id
    public ContactPojo createContact(@RequestBody ContactPojo contactPojo) {
        return contactService.createContact(contactPojo);
    }

    @PutMapping("/contacts/{id}")
    public void updateContact(@PathVariable long id, @RequestBody ContactPojo pojo) {
        contactService.updateContact(id, pojo);
    }

    //------------- Achievements
    @GetMapping("/achievements")
    public List<AchievementPojo> findAllAchievements() {
        return achievementService.findAll();
    }

    @GetMapping("/achievements/date")
    public List<AchievementPojo> findAchievementsByDate(@RequestParam String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return achievementService.findByReceiveDate(formatter.parse(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/achievements/name/{name}")
    public List<AchievementPojo> findAchievementsByName(@PathVariable String name) {
        return achievementService.findAllByName(name);
    }

    @GetMapping("/achievements/sportsman/{pk}")
    public List<AchievementPojo> findAchievementsBySportsman(@PathVariable long pk) {
        return sportsmanService.findAchievementsBySportsman(pk);
    }

    @DeleteMapping("/achievements/{id}")
    public ResponseEntity<HttpStatus> deleteAchievement(@PathVariable long id) {
        return achievementService.deleteAchievement(id);
    }

    @PostMapping("/achievements")
    public AchievementPojo createAchievement(@RequestBody AchievementPojo achievementPojo) {
        return achievementService.createAchievement(achievementPojo);
    }

    @PutMapping("/achievements/{id}")
    public void updateAchievement(@PathVariable long id, @RequestBody AchievementPojo pojo) {
        achievementService.updateAchievement(id, pojo);
    }
    //-------------

    // todo можно ли использовать requestParam при поиске по одной и той же ссылке (или как осуществить поиск)
    //  как сделать доступ для админского контроллера?
    //  как разделять контроллеры? по ролям или по сущностям? (наверное по сущностям)
    //  как избавиться от дупликатов (например, метод delete)
    //  что возвращает update?
    //  проверки и ловля исключений при необходимости, заранее не писать
}
