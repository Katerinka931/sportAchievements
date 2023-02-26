package com.coursework.sportachievements.controller;

import com.coursework.sportachievements.dto.SportPojo;
import com.coursework.sportachievements.service.SportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SportController {

    private final SportService sportService;

    public SportController(SportService sportService) {
        this.sportService = sportService;
    }

    @GetMapping()
    public List<SportPojo> getSports(){
        return sportService.findAll();
    }
}
