package by.javaguru.profiler.usecasses;

import by.javaguru.profiler.usecasses.dto.PositionDto;

import java.util.List;

public interface PositionService {

    List<PositionDto> getPositions();

    boolean isPositionExist(Long id);
}
