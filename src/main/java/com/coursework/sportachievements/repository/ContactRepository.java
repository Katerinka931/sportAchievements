package com.coursework.sportachievements.repository;

import com.coursework.sportachievements.entity.Contact;
import com.coursework.sportachievements.entity.Sportsman;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findBy();

    Contact findById(long id);

    List<Contact> findAllBySportsman(Sportsman sportsman);

    void deleteById(long id);
}
