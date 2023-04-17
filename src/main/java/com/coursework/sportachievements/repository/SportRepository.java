package com.coursework.sportachievements.repository;

import com.coursework.sportachievements.entity.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<Sport, Long> {
    Sport findByNameIgnoreCase(String name);
}
