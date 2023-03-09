package com.coursework.sportachievements.controller;

import com.coursework.sportachievements.dto.*;
import com.coursework.sportachievements.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
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
    // todo можно ли использовать requestParam при поиске по одной и той же ссылке (или как осуществить поиск)
    //  !1 везде при updateSportsman сделать проверку на соответствие команды и спорта.
    //  ----
    //  response body - чтобы возвращать сущность и отправлять ее json в ангуляр?
    //  поиск спортсмена по имени? по полному, или только по фамилии? как и что нужно вообще
    //  как сделать доступ для админского контроллера?
    //  как избавиться от дупликатов (например, метод delete)
    //  что возвращает update?
    //  проверки и ловля исключений
}
