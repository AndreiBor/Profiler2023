package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.About;
import by.itacademy.profiler.usecasses.dto.AboutDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",
        builder = @Builder(disableBuilder = true))
public interface AboutMapper {
    About aboutDtoToAbout(AboutDto aboutDto);

    AboutDto aboutToAboutDto(About about);
}