package com.example.UserAuthModule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.UserAuthModule.entity.Contact;
import com.example.UserAuthModule.service.ContactService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/contact")
@Validated
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitContactForm(@Valid @RequestBody Contact contact) {
        contactService.saveContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body("Contact message submitted successfully");
    }
}
