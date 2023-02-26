package com.coursework.sportachievements.controller;

import com.coursework.sportachievements.entity.Sport;
import com.coursework.sportachievements.repository.SportRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final SportRepository sportRepository;

    public AdminController(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    @GetMapping()
    public List<Sport> getSports(){
        return sportRepository.findBy();
    }
}
