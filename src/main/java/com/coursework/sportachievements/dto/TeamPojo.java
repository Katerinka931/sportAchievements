package com.coursework.sportachievements.dto;

import com.coursework.sportachievements.entity.Sportsman;
import com.coursework.sportachievements.entity.Team;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TeamPojo {
    private long id;
    private String name;
    private int count;
    private List<SportsmanPojo> sportsmen;

    public static TeamPojo fromEntity(Team team) {
        TeamPojo pojo = new TeamPojo();
        pojo.setId(team.getId());
        pojo.setName(team.getName());
        pojo.setCount(team.getCount());

        if (team.getSportsmen() != null) {
            List<SportsmanPojo> sportsmen = new ArrayList<>();
            pojo.setSportsmen(sportsmen);
            for (Sportsman sportsman : team.getSportsmen()) {
                sportsmen.add(SportsmanPojo.fromEntity(sportsman));
            }
        }
        return pojo;
    }

    public static Team toEntity(TeamPojo pojo) {
        Team team = new Team();
        team.setId(pojo.getId());
        team.setName(pojo.getName());
        team.setCount(pojo.getCount());

        if (pojo.getSportsmen() != null) {
            List<Sportsman> sportsmen = new ArrayList<>();
            team.setSportsmen(sportsmen);
            for (SportsmanPojo sportsmanPojo : pojo.getSportsmen()) {
                Sportsman sportsman = SportsmanPojo.toEntity(sportsmanPojo);
                sportsman.setTeam(team);
                sportsmen.add(sportsman);
            }
        }
        return team;
    }
}
