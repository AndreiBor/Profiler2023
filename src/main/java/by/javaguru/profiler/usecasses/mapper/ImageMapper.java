package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.Image;
import by.javaguru.profiler.usecasses.dto.ImageDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ImageMapper {
    ImageDto imageToImageDto(Image image);
}