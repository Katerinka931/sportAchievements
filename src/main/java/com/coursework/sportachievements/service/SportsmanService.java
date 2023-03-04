package com.coursework.sportachievements.service;

import com.coursework.sportachievements.dto.AchievementPojo;
import com.coursework.sportachievements.dto.ContactPojo;
import com.coursework.sportachievements.dto.SportsmanPojo;
import com.coursework.sportachievements.entity.Achievement;
import com.coursework.sportachievements.entity.Contact;
import com.coursework.sportachievements.entity.Sportsman;
import com.coursework.sportachievements.repository.AchievementRepository;
import com.coursework.sportachievements.repository.ContactRepository;
import com.coursework.sportachievements.repository.SportsmanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportsmanService {
    private SportsmanRepository sportsmanRepository;
    private AchievementRepository achievementRepository;
    private ContactRepository contactRepository;

    @Autowired
    public SportsmanService(SportsmanRepository sportsmanRepository, AchievementRepository achievementRepository, ContactRepository contactRepository) {
        this.sportsmanRepository = sportsmanRepository;
        this.achievementRepository = achievementRepository;
        this.contactRepository = contactRepository;
    }

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

    public void updateSportsman(long pk, SportsmanPojo pojo) {
        Sportsman sportsman = sportsmanRepository.findById(pk);
        if (sportsman != null) {
            SportsmanPojo.setSportsmanData(sportsman, pojo);
            sportsmanRepository.save(sportsman);
        }
    }

    public void updateContact(long contactId, long sportsmanId, ContactPojo pojo) {
        Contact contact = contactRepository.findById(contactId);
        if (contact != null) {
            contact.setEmail(pojo.getEmail());
            contact.setPhone(pojo.getPhone());
            contact.setSportsman(sportsmanRepository.findById(sportsmanId));
            contactRepository.save(contact);
        }
    }

    public void updateAchievement(long achievementId, long sportsmanId, AchievementPojo pojo) {
        Achievement achievement = achievementRepository.findById(achievementId);
        if (achievement != null) {
            achievement.setName(pojo.getName());
            achievement.setRecvDate(pojo.getRecvDate());
            achievement.setAchSportsman(sportsmanRepository.findById(sportsmanId));
            achievementRepository.save(achievement);
        }
    }

    public SportsmanPojo findById(long id) {
        return SportsmanPojo.fromEntity(sportsmanRepository.findById(id));
    }
}
