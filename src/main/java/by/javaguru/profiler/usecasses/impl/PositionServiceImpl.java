package by.javaguru.profiler.usecasses.impl;

import by.javaguru.profiler.persistence.model.Position;
import by.javaguru.profiler.persistence.repository.PositionRepository;
import by.javaguru.profiler.usecasses.PositionService;
import by.javaguru.profiler.usecasses.dto.PositionDto;
import by.javaguru.profiler.usecasses.mapper.PositionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PositionServiceImpl implements PositionService {

    public static final String SORTING_PROPERTY = "name";
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    @Override
    public List<PositionDto> getPositions() {
        List<Position> positions = positionRepository.findAll(Sort.by(Sort.Order.asc(SORTING_PROPERTY)));
        return positionMapper.toListDto(positions);
    }

    @Override
    public boolean isPositionExist(Long id) {
        return positionRepository.existsById(id);
    }
}
