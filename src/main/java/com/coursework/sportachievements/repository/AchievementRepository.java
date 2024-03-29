package com.coursework.sportachievements.repository;

import com.coursework.sportachievements.entity.Achievement;
import com.coursework.sportachievements.entity.Sportsman;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    List<Achievement> findAllByName(String name);

    List<Achievement> findAllByRecvDate(Date recvDate);

    List<Achievement> findAllByAchSportsman(Sportsman achSportsman);
}
