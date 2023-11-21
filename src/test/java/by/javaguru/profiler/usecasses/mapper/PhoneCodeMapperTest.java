package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.PhoneCode;
import by.javaguru.profiler.usecasses.dto.PhoneCodeDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static by.javaguru.profiler.util.PhoneCodeTestData.createPhoneCode;
import static org.junit.jupiter.api.Assertions.*;

class PhoneCodeMapperTest {

    private final PhoneCodeMapper phoneCodeMapper = Mappers.getMapper(PhoneCodeMapper.class);
    @Test
    void shouldMapCorrectlyAllFieldWhenInvokeFromEntityToDto() {
        List<PhoneCode> phoneCodeList = List.of(createPhoneCode());
        List<PhoneCodeDto> phoneCodeDtoList = phoneCodeMapper.toDto(phoneCodeList);

        assertEquals(phoneCodeList.get(0).getId(),phoneCodeDtoList.get(0).id());
        assertEquals(phoneCodeList.get(0).getCode(),phoneCodeDtoList.get(0).code());
        assertEquals(phoneCodeList.get(0).getCountry(),phoneCodeDtoList.get(0).country());
    }
}