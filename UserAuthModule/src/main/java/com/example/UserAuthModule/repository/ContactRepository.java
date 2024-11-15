package com.example.UserAuthModule.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UserAuthModule.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
