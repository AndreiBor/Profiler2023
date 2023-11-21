package by.javaguru.profiler.usecasses.impl;

import by.javaguru.profiler.persistence.model.Industry;
import by.javaguru.profiler.persistence.repository.IndustryRepository;
import by.javaguru.profiler.usecasses.IndustryService;
import by.javaguru.profiler.usecasses.dto.IndustryResponseDto;
import by.javaguru.profiler.usecasses.mapper.IndustryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IndustryServiceImpl implements IndustryService {

    private final IndustryRepository industryRepository;
    private final IndustryMapper industryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<IndustryResponseDto> getIndustries() {
        List<Industry> industries = industryRepository.findAllByOrderById();

        return industries.stream()
                .map(industryMapper::fromEntityToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Industry getIndustryById(Long id) {
        return industryRepository.getIndustryById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isIndustryExist(Long id) {
        return industryRepository.existsById(id);
    }
}
