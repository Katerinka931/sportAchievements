package com.coursework.sportachievements.repository;

import com.coursework.sportachievements.entity.Achievement;
import com.coursework.sportachievements.entity.Sportsman;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    List<Achievement> findBy();

    List<Achievement> findAllByName(String name);

    Achievement findById(long id);

    List<Achievement> findAllBySportsman(Sportsman sportsman);

    void deleteById(long id);
}
