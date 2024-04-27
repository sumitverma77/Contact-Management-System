package com.example.contact_management_system.controller;

import com.example.contact_management_system.request.AddRequest;
import com.example.contact_management_system.response.AddResponse;
import com.example.contact_management_system.service.ContactServiceWithTrie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trie")
public class ContactsWithTrie {
    @Autowired
    private ContactServiceWithTrie contactServiceWithTrie;
    @PostMapping("/add")
    public AddResponse addContact(@RequestBody AddRequest addRequest )
    {
        return contactServiceWithTrie.addService(addRequest);
    }


}
