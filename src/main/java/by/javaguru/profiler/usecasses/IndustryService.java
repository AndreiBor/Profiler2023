package by.javaguru.profiler.usecasses;

import by.javaguru.profiler.persistence.model.Industry;
import by.javaguru.profiler.usecasses.dto.IndustryResponseDto;

import java.util.List;

public interface IndustryService {

    List<IndustryResponseDto> getIndustries();

    Industry getIndustryById(Long id);

    boolean isIndustryExist(Long id);

}
