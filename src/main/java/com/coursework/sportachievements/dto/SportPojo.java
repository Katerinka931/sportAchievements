package com.coursework.sportachievements.dto;

import com.coursework.sportachievements.entity.Sport;
import com.coursework.sportachievements.entity.Sportsman;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SportPojo {
    private long id;
    private String name;
    private List<SportsmanPojo> sportsmen;

    public static SportPojo fromEntity(Sport sport) {
        SportPojo pojo = new SportPojo();
        pojo.setId(sport.getId());
        pojo.setName(sport.getName());

        if (sport.getSportsmen() != null) {
            List<SportsmanPojo> sportsmen = new ArrayList<>();
            pojo.setSportsmen(sportsmen);
            for (Sportsman sportsman : sport.getSportsmen()) {
                sportsmen.add(SportsmanPojo.fromEntity(sportsman));
            }
        }
        return pojo;
    }

    public static Sport toEntity(SportPojo pojo) {
        Sport sport = new Sport();
        sport.setId(pojo.getId());
        sport.setName(pojo.getName());

        if (pojo.getSportsmen() != null) {
            List<Sportsman> sportsmen = new ArrayList<>();
            sport.setSportsmen(sportsmen);
            for (SportsmanPojo sportsmanPojo : pojo.getSportsmen()) {
                Sportsman sportsman = SportsmanPojo.toEntity(sportsmanPojo);
                sportsman.setSport(sport);
                sportsmen.add(sportsman);
            }
        }
        return sport;
    }

    public static List<SportPojo> convertSportsToPojo(List<Sport> sports) {
        List<SportPojo> pojoList = new ArrayList<>();
        for (Sport sport : sports) {
            pojoList.add(SportPojo.fromEntity(sport));
        }
        return pojoList;
    }
}