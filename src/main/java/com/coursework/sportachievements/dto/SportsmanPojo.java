package com.coursework.sportachievements.dto;

import com.coursework.sportachievements.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SportsmanPojo {
    private long id;
    private String passport;
    private String firstName;
    private String lastName;
    private String middleName;
    private Date birthdate;
    private List<ContactPojo> contacts;
    private List<AchievementPojo> achievements;

    public static SportsmanPojo fromEntity(Sportsman sportsman) {
        SportsmanPojo pojo = new SportsmanPojo();
        pojo.setId(sportsman.getId());
        pojo.setPassport(sportsman.getPassport());
        pojo.setFirstName(sportsman.getFirstName());
        pojo.setLastName(sportsman.getLastName());
        pojo.setMiddleName(sportsman.getMiddleName());
        pojo.setBirthdate(sportsman.getBirthdate());

        if (sportsman.getContacts() != null) {
            List<ContactPojo> contacts = new ArrayList<>();
            pojo.setContacts(contacts);
            for (Contact contact : sportsman.getContacts()) {
                contacts.add(ContactPojo.fromEntity(contact));
            }
        }

        if (sportsman.getAchievements() != null) {
            List<AchievementPojo> achievements = new ArrayList<>();
            pojo.setAchievements(achievements);
            for (Achievement achievement : sportsman.getAchievements()) {
                achievements.add(AchievementPojo.fromEntity(achievement));
            }
        }
        return pojo;
    }

    public static Sportsman toEntity(SportsmanPojo pojo) {
        Sportsman sportsman = new Sportsman();
        sportsman.setId(pojo.getId());
        sportsman.setPassport(pojo.getPassport());
        sportsman.setLastName(pojo.getLastName());
        sportsman.setFirstName(pojo.getFirstName());
        sportsman.setMiddleName(pojo.getMiddleName());
        sportsman.setBirthdate(pojo.getBirthdate());

        if (pojo.getContacts() != null) {
            List<Contact> contacts = new ArrayList<>();
            sportsman.setContacts(contacts);
            for (ContactPojo contactPojo: pojo.getContacts()) {
                Contact contact = ContactPojo.toEntity(contactPojo);
                contact.setSportsman(sportsman);
                contacts.add(contact);
            }
        }

        if (pojo.getAchievements() != null) {
            List<Achievement> achievements = new ArrayList<>();
            sportsman.setAchievements(achievements);
            for (AchievementPojo achievementPojo : pojo.getAchievements()) {
                Achievement achievement = AchievementPojo.toEntity(achievementPojo);
                achievement.setSportsman(sportsman);
                achievements.add(achievement);
            }
        }

        return sportsman;
    }
}