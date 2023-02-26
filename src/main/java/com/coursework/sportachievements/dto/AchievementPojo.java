package com.coursework.sportachievements.dto;

import com.coursework.sportachievements.entity.Achievement;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AchievementPojo {
    private long id;
    private String name;
    private Date recvDate;

    public static AchievementPojo fromEntity(Achievement achievement) {
        AchievementPojo pojo = new AchievementPojo();
        pojo.setId(achievement.getId());
        pojo.setName(achievement.getName());
        pojo.setRecvDate(achievement.getRecvDate());
        return pojo;
    }

    public static Achievement toEntity(AchievementPojo pojo) {
        Achievement achievement = new Achievement();
        achievement.setId(pojo.getId());
        achievement.setName(pojo.getName());
        achievement.setRecvDate(pojo.getRecvDate());
        return achievement;
    }
}
