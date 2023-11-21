package by.javaguru.profiler.usecasses;

import by.javaguru.profiler.persistence.model.PhoneCode;
import by.javaguru.profiler.usecasses.dto.PhoneCodeDto;

import java.util.List;

public interface PhoneCodeService {

    List<PhoneCodeDto> getPhoneCodes();

    boolean isPhoneCodeExist(Long id);

    PhoneCode getPhoneCodeById(Long id);
}
