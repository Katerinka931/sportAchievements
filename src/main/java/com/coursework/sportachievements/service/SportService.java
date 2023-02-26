package com.coursework.sportachievements.service;

import com.coursework.sportachievements.dto.SportPojo;
import com.coursework.sportachievements.entity.Sport;
import com.coursework.sportachievements.repository.SportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SportService {

    private SportRepository sportRepository;

    @Autowired
    public SportService(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    public List<SportPojo> findAll() {
        List<Sport> sports = sportRepository.findBy();
        return SportPojo.convertSportsToPojo(sports);
    }

    public SportPojo findSportByName(String name) {
        return SportPojo.fromEntity(sportRepository.findByNameIgnoreCase(name));
    }

    public SportPojo findSportById(long id) {
        return SportPojo.fromEntity(sportRepository.findById(id));
    }

    public ResponseEntity<HttpStatus> deleteSport(long id) {
        try {
            if (sportRepository.findById(id) != null) {
                sportRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public SportPojo createSport(SportPojo sportPojo) {
        sportRepository.save(SportPojo.toEntity(sportPojo));
        return sportPojo;
    }

    public void updateSport(long id, SportPojo pojo) {
        Sport sport = sportRepository.findById(id);
        if (sport != null) {
            sport.setName(pojo.getName());
            sportRepository.save(sport);
        }
    }
}

