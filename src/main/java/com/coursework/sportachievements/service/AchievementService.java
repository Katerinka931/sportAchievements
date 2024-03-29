package com.coursework.sportachievements.service;

import com.coursework.sportachievements.dto.AchievementPojo;
import com.coursework.sportachievements.entity.Achievement;
import com.coursework.sportachievements.repository.AchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;

    public List<AchievementPojo> findAll() {
        List<Achievement> achievements = achievementRepository.findAll();
        return AchievementPojo.convertAchievementsToPojo(achievements);
    }

    public List<AchievementPojo> findAllByName(String name) {
        List<Achievement> Achievements = achievementRepository.findAllByName(name);
        return AchievementPojo.convertAchievementsToPojo(Achievements);
    }

    public AchievementPojo findById(long pk) {
        Optional<Achievement> achievementOptional = achievementRepository.findById(pk);
        return achievementOptional.map(AchievementPojo::fromEntity).orElse(null);
    }

    public List<AchievementPojo> findByReceiveDate(Date date) {
        return AchievementPojo.convertAchievementsToPojo(achievementRepository.findAllByRecvDate(date));
    }

    public ResponseEntity<HttpStatus> deleteAchievement(long id) {
        try {
            if (achievementRepository.findById(id).isPresent()) {
                achievementRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public AchievementPojo createAchievement(AchievementPojo achievementPojo) {
        Achievement achievement = achievementRepository.save(AchievementPojo.toEntity(achievementPojo));
        return AchievementPojo.fromEntity(achievement);
    }

    public void updateAchievement(long id, AchievementPojo pojo) {
        Optional<Achievement> achievementOptional = achievementRepository.findById(id);
        if (achievementOptional.isPresent()) {
            Achievement achievement = achievementOptional.get();
            achievement.setName(pojo.getName());
            achievement.setRecvDate(pojo.getRecvDate());
            achievementRepository.save(achievement);
        }
    }
}
