package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.PhoneCode;
import by.javaguru.profiler.usecasses.dto.PhoneCodeDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true))
public interface PhoneCodeMapper {

    List<PhoneCodeDto> toDto(List<PhoneCode> phoneCodes);
}
