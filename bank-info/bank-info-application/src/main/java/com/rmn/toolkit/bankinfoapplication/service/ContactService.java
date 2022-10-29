package com.rmn.toolkit.bankinfoapplication.service;

import com.rmn.toolkit.bankinfoapplication.model.Contact;
import com.rmn.toolkit.bankinfoapplication.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository ;
    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }
}
