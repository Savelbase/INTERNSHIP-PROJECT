package com.rmn.toolkit.bankinfoapplication.controller;

import com.rmn.toolkit.bankinfoapplication.model.Contact;
import com.rmn.toolkit.bankinfoapplication.service.ContactService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contacts")
@Tag(name = "Contacts")
@RequiredArgsConstructor
@Slf4j
public class ContactController {
    private final ContactService contactService ;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contacts received successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class))))
    })
    public List<Contact> getContacts() {
        log.info("GET /api/v1/contacts");
        return contactService.getContacts();
    }
}
