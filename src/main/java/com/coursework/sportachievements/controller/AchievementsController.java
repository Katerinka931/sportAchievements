package com.coursework.sportachievements.controller;

import com.coursework.sportachievements.dto.*;
import com.coursework.sportachievements.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/achievements")
public class AchievementsController {

    private final AchievementService achievementService;

    public AchievementsController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping
    public List<AchievementPojo> findAllAchievements() {
        return achievementService.findAll();
    }

    @GetMapping("/date")
    public List<AchievementPojo> findAchievementsByDate(@RequestParam String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return achievementService.findByReceiveDate(formatter.parse(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/name/{name}")
    public List<AchievementPojo> findAchievementsByName(@PathVariable String name) {
        return achievementService.findAllByName(name);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAchievement(@PathVariable long id) {
        return achievementService.deleteAchievement(id);
    }

    @PostMapping
    public AchievementPojo createAchievement(@RequestBody AchievementPojo achievementPojo) {
        return achievementService.createAchievement(achievementPojo);
    }

    @PutMapping("/{id}")
    public void updateAchievement(@PathVariable long id, @RequestBody AchievementPojo pojo) {
        achievementService.updateAchievement(id, pojo);
    }

    //-------------
    // todo
    //  1. можно отправлять статус ошибки, а можно отправить с кодом 200 и сообщение.
    //  выводить сообщения об ошибках и успехах пользователю
    //  2. - проверка что телефон только числа (и добавить шаблон в ангуляр);
    //     -? валидация формы на фронте
}
