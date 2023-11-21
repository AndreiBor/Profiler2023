package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.Position;
import by.javaguru.profiler.usecasses.dto.PositionDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface PositionMapper {

    List<PositionDto> toListDto(List<Position> positions);
}

