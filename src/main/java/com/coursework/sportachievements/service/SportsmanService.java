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
import java.util.Optional;

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
        Optional<Sportsman> opt = sportsmanRepository.findById(sportsmanId);
        if (opt.isPresent()) {
            Sportsman sportsman = opt.get();
            List<Achievement> achievements = achievementRepository.findAllByAchSportsman(sportsman);
            return AchievementPojo.convertAchievementsToPojo(achievements);
        }
        return null;
    }

    public List<ContactPojo> findContactsBySportsman(long sportsmanId) {
        Optional<Sportsman> opt = sportsmanRepository.findById(sportsmanId);
        if (opt.isPresent()) {
            Sportsman sportsman = opt.get();
            List<Contact> contacts = contactRepository.findAllBySportsman(sportsman);
            return ContactPojo.convertContactsToPojo(contacts);
        }
        return null;
    }

    public ResponseEntity<HttpStatus> deleteSportsman(long id) {
        try {
            if (sportsmanRepository.findById(id).isPresent()) {
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

        Optional<Sportsman> opt = sportsmanRepository.findById(sportsmanId);
        if (opt.isPresent()) {
            Contact contact = ContactPojo.toEntity(pojo);
            contact.setSportsman(opt.get());
            contactRepository.save(contact);
            return ContactPojo.fromEntity(contact);
        }
        return pojo;
    }

    public AchievementPojo createAchievement(long sportsmanId, AchievementPojo pojo) {
        Optional<Sportsman> opt = sportsmanRepository.findById(sportsmanId);
        if (opt.isPresent()) {
            Achievement achievement = AchievementPojo.toEntity(pojo);
            achievement.setAchSportsman(opt.get());
            achievementRepository.save(achievement);
            return AchievementPojo.fromEntity(achievement);
        }
        return pojo;
    }

    public SportsmanPojo updateSportsman(long pk, SportsmanPojo pojo) {
        Optional<Sportsman> opt = sportsmanRepository.findById(pk);
        if (opt.isPresent()) {
            Sportsman sportsman = opt.get();
            SportsmanPojo.setSportsmanData(sportsman, pojo);
            sportsmanRepository.save(sportsman);
            return SportsmanPojo.fromEntity(sportsman);
        }
        return pojo;
    }

    public ContactPojo updateContact(long contactId, long sportsmanId, ContactPojo pojo) {
        if (!NumberUtils.isCreatable(pojo.getPhone()))
            throw new NumberFormatException();

        Optional<Contact> contact_opt = contactRepository.findById(contactId);
        if (contact_opt.isPresent()) {
            Contact contact = contact_opt.get();
            contact.setEmail(pojo.getEmail());
            contact.setPhone(pojo.getPhone());
            Optional<Sportsman> opt = sportsmanRepository.findById(sportsmanId);
            if (opt.isPresent()) {
                contact.setSportsman(opt.get());
                contactRepository.save(contact);
                return ContactPojo.fromEntity(contact);
            }
            return pojo;
        }
        return pojo;
    }

    public AchievementPojo updateAchievement(long achievementId, long sportsmanId, AchievementPojo pojo) {
        Optional<Achievement> achievement_opt = achievementRepository.findById(achievementId);
        if (achievement_opt.isPresent()) {
            Achievement achievement = achievement_opt.get();
            achievement.setName(pojo.getName());
            achievement.setRecvDate(pojo.getRecvDate());
            Optional<Sportsman> opt = sportsmanRepository.findById(sportsmanId);
            if (opt.isPresent()) {
                achievement.setAchSportsman(opt.get());
                achievementRepository.save(achievement);
                return AchievementPojo.fromEntity(achievement);
            }
            return pojo;
        }
        return pojo;
    }

    public SportsmanPojo findById(long id) {
        Optional<Sportsman> opt = sportsmanRepository.findById(id);
        return opt.map(SportsmanPojo::fromEntity).orElse(null);
    }

    public SportPojo findSportBySportsman(long id) {
        Optional<Sportsman> opt = sportsmanRepository.findById(id);
        if (opt.isPresent()) {
            Sportsman sportsman = opt.get();
            Optional<Sport> sport_opt = sportRepository.findById(sportsman.getSport().getId());
            if (sport_opt.isPresent()) {
                return SportPojo.fromEntity(sport_opt.get());
            }
        }
        return null;
    }

    public TeamPojo findTeamBySportsman(long id) {
        Optional<Sportsman> opt = sportsmanRepository.findById(id);
        if (opt.isPresent()) {
            Sportsman sportsman = opt.get();
            Team sportsmanTeam = sportsman.getTeam();
            long team_id = sportsmanTeam.getId();
            Optional<Team> team_opt = teamRepository.findById(team_id);
            if (team_opt.isPresent()) {
                return TeamPojo.fromEntity(team_opt.get());
            }
        }
        return null;
    }
}
