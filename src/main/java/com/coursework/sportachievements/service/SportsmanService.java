package com.coursework.sportachievements.service;

import com.coursework.sportachievements.dto.*;
import com.coursework.sportachievements.entity.*;
import com.coursework.sportachievements.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportsmanService {
    private final SportsmanRepository sportsmanRepository;
    private final AchievementRepository achievementRepository;
    private final ContactRepository contactRepository;
    private final SportRepository sportRepository;
    private final TeamRepository teamRepository;

    public List<SportsmanPojo> findAll() {
        List<Sportsman> sportsmen = sportsmanRepository.findAll();
        return SportsmanPojo.convertSportsmenToPojo(sportsmen);
    }

    public List<AchievementPojo> findAchievementsBySportsman(long sportsmanId) {
        Sportsman sportsman = sportsmanRepository.findById(sportsmanId);
        List<Achievement> achievements = achievementRepository.findAllByAchSportsman(sportsman);
        return AchievementPojo.convertAchievementsToPojo(achievements);
    }

    public List<ContactPojo> findContactsBySportsman(long sportsmanId) {
        Sportsman sportsman = sportsmanRepository.findById(sportsmanId);
        List<Contact> contacts = contactRepository.findAllBySportsman(sportsman);
        return ContactPojo.convertContactsToPojo(contacts);
    }

    public ResponseEntity<HttpStatus> deleteSportsman(long id) {
        try {
            if (sportsmanRepository.findById(id) != null) {
                sportsmanRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public SportsmanPojo createSportsman(SportsmanPojo sportsmanPojo) {
        Sportsman sportsman = sportsmanRepository.save(SportsmanPojo.toEntity(sportsmanPojo));
        return SportsmanPojo.fromEntity(sportsman);
    }

    public ContactPojo createContact(long sportsmanId, ContactPojo pojo) {
        if (!NumberUtils.isCreatable(pojo.getPhone()))
            throw new NumberFormatException();

        Contact contact = ContactPojo.toEntity(pojo);
        contact.setSportsman(sportsmanRepository.findById(sportsmanId));
        contactRepository.save(contact);
        return ContactPojo.fromEntity(contact);
    }

    public AchievementPojo createAchievement(long sportsmanId, AchievementPojo pojo) {
        Achievement achievement = AchievementPojo.toEntity(pojo);
        achievement.setAchSportsman(sportsmanRepository.findById(sportsmanId));
        achievementRepository.save(achievement);
        return AchievementPojo.fromEntity(achievement);
    }

    public SportsmanPojo updateSportsman(long pk, SportsmanPojo pojo) {
        Sportsman sportsman = sportsmanRepository.findById(pk);
        if (sportsman != null) {
            SportsmanPojo.setSportsmanData(sportsman, pojo);
            sportsmanRepository.save(sportsman);
            return SportsmanPojo.fromEntity(sportsman);
        }
        return pojo;
    }

    public ContactPojo updateContact(long contactId, long sportsmanId, ContactPojo pojo) {
        if (!NumberUtils.isCreatable(pojo.getPhone()))
            throw new NumberFormatException();
        
        Contact contact = contactRepository.findById(contactId);
        if (contact != null) {
            contact.setEmail(pojo.getEmail());
            contact.setPhone(pojo.getPhone());
            contact.setSportsman(sportsmanRepository.findById(sportsmanId));
            contactRepository.save(contact);
            return ContactPojo.fromEntity(contact);
        }
        return pojo;
    }

    public AchievementPojo updateAchievement(long achievementId, long sportsmanId, AchievementPojo pojo) {
        Achievement achievement = achievementRepository.findById(achievementId);
        if (achievement != null) {
            achievement.setName(pojo.getName());
            achievement.setRecvDate(pojo.getRecvDate());
            achievement.setAchSportsman(sportsmanRepository.findById(sportsmanId));
            achievementRepository.save(achievement);
            return AchievementPojo.fromEntity(achievement);
        }
        return pojo;
    }

    public SportsmanPojo findById(long id) {
        return SportsmanPojo.fromEntity(sportsmanRepository.findById(id));
    }

    public SportPojo findSportBySportsman(long id) {
        Sportsman sportsman = sportsmanRepository.findById(id);
        Sport sport = sportRepository.findById(sportsman.getSport().getId());
        return SportPojo.fromEntity(sport);
    }

    public TeamPojo findTeamBySportsman(long id) {
        Sportsman sportsman = sportsmanRepository.findById(id);
        Team sportsmanTeam = sportsman.getTeam();
        if (sportsmanTeam != null) {
            long team_id = sportsmanTeam.getId();
            Team team = teamRepository.findById(team_id);
            return TeamPojo.fromEntity(team);
        }
        return null;
    }
}
