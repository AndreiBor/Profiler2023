package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.About;
import by.javaguru.profiler.usecasses.dto.AboutDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",
        builder = @Builder(disableBuilder = true))
public interface AboutMapper {
    About aboutDtoToAbout(AboutDto aboutDto);

    AboutDto aboutToAboutDto(About about);
}