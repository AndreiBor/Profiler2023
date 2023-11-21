package by.javaguru.profiler.usecasses.impl;

import by.javaguru.profiler.persistence.model.Institution;
import by.javaguru.profiler.persistence.repository.InstitutionRepository;
import by.javaguru.profiler.usecasses.InstitutionService;
import by.javaguru.profiler.usecasses.dto.InstitutionRequestDto;
import by.javaguru.profiler.usecasses.dto.InstitutionResponseDto;
import by.javaguru.profiler.usecasses.mapper.InstitutionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;

    @Override
    public List<InstitutionResponseDto> getInstitutions() {
        List<Institution> institutions = institutionRepository.findAllByOrderByName();

        return institutions.stream()
                .map(institutionMapper::fromEntityToDto)
                .toList();
    }

    @Override
    public boolean isInstitutionExist(Long id) {
        return institutionRepository.existsById(id);
    }

    @Override
    public InstitutionResponseDto save(InstitutionRequestDto dto) {
        Institution savedInstitution = institutionRepository
                .save(institutionMapper
                        .fromDtoToEntity(dto));
        return institutionMapper.fromEntityToDto(savedInstitution);

    }
}
