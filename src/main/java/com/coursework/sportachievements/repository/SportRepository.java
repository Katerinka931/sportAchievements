package com.coursework.sportachievements.repository;

import com.coursework.sportachievements.entity.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SportRepository extends JpaRepository<Sport, Long> {
    List<Sport> findBy();

    List<Sport> findAllByName(String name);

    Sport findById(long id);

    void deleteById(long id);
}
