package by.javaguru.profiler.usecasses.impl;

import by.javaguru.profiler.api.exception.ContactsNotFoundException;
import by.javaguru.profiler.persistence.model.Contacts;
import by.javaguru.profiler.persistence.model.CurriculumVitae;
import by.javaguru.profiler.persistence.repository.ContactsRepository;
import by.javaguru.profiler.persistence.repository.CurriculumVitaeRepository;
import by.javaguru.profiler.persistence.repository.PhoneCodeRepository;
import by.javaguru.profiler.usecasses.ContactsService;
import by.javaguru.profiler.usecasses.dto.ContactsDto;
import by.javaguru.profiler.usecasses.dto.ContactsResponseDto;
import by.javaguru.profiler.usecasses.mapper.ContactsMapper;
import by.javaguru.profiler.usecasses.util.AuthService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactsServiceImpl implements ContactsService {

    private final ContactsRepository contactsRepository;

    private final CurriculumVitaeRepository curriculumVitaeRepository;

    private final PhoneCodeRepository phoneCodeRepository;

    private final ContactsMapper contactsMapper;

    private final AuthService authService;

    @Override
    @Transactional
    public ContactsResponseDto saveContacts(String uuid, ContactsDto contactsDto) {
        String username = authService.getUsername();
        CurriculumVitae curriculumVitae = curriculumVitaeRepository.findByUuidAndUsername(uuid, username);
        Contacts contacts = contactsMapper.contactsDtoToContacts(contactsDto);
        contacts.setId(curriculumVitae.getId());
        return contactsMapper.contactsToContactsResponseDto(contactsRepository.save(contacts));
    }

    @Override
    @Transactional
    public ContactsResponseDto getContacts(String uuid) {
        String username = authService.getUsername();
        Contacts contacts = contactsRepository.findByUuidAndUsername(uuid, username).orElseThrow(() ->
                new ContactsNotFoundException(String.format("Contacts not available for CV UUID: %s of user %s", uuid, username)));
        return contactsMapper.contactsToContactsResponseDto(contacts);
    }

    @Override
    @Transactional
    public ContactsResponseDto updateContacts(String uuid, ContactsDto contactsDto) {
        String username = authService.getUsername();
        Contacts contacts = contactsRepository.findByUuidAndUsername(uuid, username).orElseThrow(() ->
                new ContactsNotFoundException(String.format("Contacts not available for CV UUID: %s of user %s", uuid, username)));
        updateContacts(contactsDto, contacts);
        Contacts updateContacts = contactsRepository.save(contacts);
        return contactsMapper.contactsToContactsResponseDto(updateContacts);
    }

    private void updateContacts(ContactsDto contactsDto, Contacts contacts) {
        if (!contacts.getPhoneNumber().equals(contactsDto.phoneNumber())) {
            contacts.setPhoneNumber(contactsDto.phoneNumber());
        }
        if (!contacts.getEmail().equals(contactsDto.email())) {
            contacts.setEmail(contactsDto.email());
        }
        if (!contacts.getLinkedin().equals(contactsDto.linkedin())) {
            contacts.setLinkedin(contactsDto.linkedin());
        }
        if (!contacts.getPhoneCode().getId().equals(contactsDto.phoneCodeId())) {
            phoneCodeRepository.findById(contactsDto.phoneCodeId())
                    .ifPresent(contacts::setPhoneCode);
        }
        contacts.setSkype(contactsDto.skype());
        contacts.setPortfolio(contactsDto.portfolio());
    }
}
