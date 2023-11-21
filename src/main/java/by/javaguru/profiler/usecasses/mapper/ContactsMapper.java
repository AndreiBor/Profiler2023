package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.Contacts;
import by.javaguru.profiler.usecasses.dto.ContactsDto;
import by.javaguru.profiler.usecasses.dto.ContactsResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true))
public interface ContactsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phoneCode", ignore = true)
    @Mapping(target = "phoneCode.id", source = "phoneCodeId")
    Contacts contactsDtoToContacts(ContactsDto contactsDto);

    @Mapping(target = "phoneCodeId", source = "phoneCode.id")
    @Mapping(target = "phoneCode", source = "phoneCode.code")
    ContactsResponseDto contactsToContactsResponseDto(Contacts contacts);
}
