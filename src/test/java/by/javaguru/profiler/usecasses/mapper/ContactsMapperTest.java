package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.Contacts;
import by.javaguru.profiler.usecasses.dto.ContactsDto;
import by.javaguru.profiler.usecasses.dto.ContactsResponseDto;
import by.javaguru.profiler.util.ContactTestData;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ContactsMapperTest {

    private final ContactsMapper contactsMapper = Mappers.getMapper(ContactsMapper.class);
    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeFromDtoToEntity() {
        ContactsDto expectedDto  = ContactTestData.createContactDto().build();
        Contacts actualEntity = contactsMapper.contactsDtoToContacts(expectedDto );

        assertEquals(expectedDto.phoneCodeId(),actualEntity.getPhoneCode().getId());
        assertEquals(expectedDto.phoneNumber(),actualEntity.getPhoneNumber());
        assertEquals(expectedDto.email(),actualEntity.getEmail());
        assertEquals(expectedDto.skype(),actualEntity.getSkype());
        assertEquals(expectedDto.linkedin(),actualEntity.getLinkedin());
        assertEquals(expectedDto.portfolio(),actualEntity.getPortfolio());
    }

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeFromEntityToDto() {
        Contacts expectedContactEntity = ContactTestData.createContact().build();
        ContactsResponseDto actualContactResponseDto = contactsMapper.contactsToContactsResponseDto(expectedContactEntity);

        assertEquals(expectedContactEntity.getPhoneCode().getId(),actualContactResponseDto.phoneCodeId());
        assertEquals(expectedContactEntity.getPhoneCode().getCode(),actualContactResponseDto.phoneCode());
        assertEquals(expectedContactEntity.getPhoneNumber(),actualContactResponseDto.phoneNumber());
        assertEquals(expectedContactEntity.getEmail(),actualContactResponseDto.email());
        assertEquals(expectedContactEntity.getSkype(),actualContactResponseDto.skype());
        assertEquals(expectedContactEntity.getLinkedin(),actualContactResponseDto.linkedin());
        assertEquals(expectedContactEntity.getPortfolio(),actualContactResponseDto.portfolio());
    }
}