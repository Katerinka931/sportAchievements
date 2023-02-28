package com.coursework.sportachievements.service;

import com.coursework.sportachievements.dto.ContactPojo;
import com.coursework.sportachievements.dto.SportPojo;
import com.coursework.sportachievements.entity.Contact;
import com.coursework.sportachievements.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {
    private ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<ContactPojo> findAll() {
        List<Contact> contacts = contactRepository.findAll();
        return ContactPojo.convertContactsToPojo(contacts);
    }

    public List<ContactPojo> findByPhone(String phone) {
        List<Contact> contacts = contactRepository.findAllByPhoneContaining(phone);
        return ContactPojo.convertContactsToPojo(contacts);
    }

    public List<ContactPojo> findByEmail(String email) {
        List<Contact> contacts = contactRepository.findAllByEmailContaining(email);
        return ContactPojo.convertContactsToPojo(contacts);
    }

    public ContactPojo findById(long pk) {
        return ContactPojo.fromEntity(contactRepository.findById(pk));
    }

    public ResponseEntity<HttpStatus> deleteContact(long id) {
        try {
            if (contactRepository.findById(id) != null) {
                contactRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ContactPojo createContact(ContactPojo contactPojo) {
        Contact contact = contactRepository.save(ContactPojo.toEntity(contactPojo));
        return ContactPojo.fromEntity(contact);
    }

    public void updateContact(long id, ContactPojo pojo) {
        Contact contact = contactRepository.findById(id);
        if (contact != null) {
            contact.setEmail(pojo.getEmail());
            contact.setPhone(pojo.getPhone());
            contactRepository.save(contact);
        }
    }
}
