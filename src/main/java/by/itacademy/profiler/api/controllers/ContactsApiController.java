package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.ContactsService;
import by.itacademy.profiler.usecasses.dto.ContactsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cvs/{uuid}/contacts")
@Slf4j
public class ContactsApiController {

    private final ContactsService contactsService;

    @PostMapping
    public ResponseEntity<ContactsDto> saveContacts(@PathVariable String uuid, @RequestBody @Valid ContactsDto contacts) {
        log.debug("Input data for creating contact information section of CV with UUID {}: {}", uuid, contacts);
        ContactsDto cvContacts = contactsService.saveContacts(uuid, contacts);
        return new ResponseEntity<>(cvContacts, HttpStatus.CREATED);
    }
}
