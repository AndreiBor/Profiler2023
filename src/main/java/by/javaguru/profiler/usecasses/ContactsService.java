package by.javaguru.profiler.usecasses;


import by.javaguru.profiler.usecasses.dto.ContactsDto;
import by.javaguru.profiler.usecasses.dto.ContactsResponseDto;

public interface ContactsService {

    ContactsResponseDto saveContacts(String uuid, ContactsDto contactsDto);

    ContactsResponseDto getContacts(String uuid);

    ContactsResponseDto updateContacts(String uuid, ContactsDto contactsDto);
}
