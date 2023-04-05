package com.coursework.sportachievements.controller;

import com.coursework.sportachievements.dto.ContactPojo;
import com.coursework.sportachievements.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public List<ContactPojo> findAllContacts() {
        return contactService.findAll();
    }

    @GetMapping("/phone")
    public List<ContactPojo> findContactsByPhoneContaining(@RequestParam String phone) {
        return contactService.findByPhone(phone);
    }

    @GetMapping("/email")
    public List<ContactPojo> findContactsByEmailContaining(@RequestParam String email) {
        return contactService.findByEmail(email);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable long id) {
        return contactService.deleteContact(id);
    }

    @PostMapping
    public ContactPojo createContact(@RequestBody ContactPojo contactPojo) {
        return contactService.createContact(contactPojo);
    }

    @PutMapping("/{id}")
    public void updateContact(@PathVariable long id, @RequestBody ContactPojo pojo) {
        contactService.updateContact(id, pojo);
    }
}
