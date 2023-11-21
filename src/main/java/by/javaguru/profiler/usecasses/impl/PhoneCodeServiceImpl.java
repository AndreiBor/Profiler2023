package by.javaguru.profiler.usecasses.impl;

import by.javaguru.profiler.persistence.model.PhoneCode;
import by.javaguru.profiler.persistence.repository.PhoneCodeRepository;
import by.javaguru.profiler.usecasses.PhoneCodeService;
import by.javaguru.profiler.usecasses.dto.PhoneCodeDto;
import by.javaguru.profiler.usecasses.mapper.PhoneCodeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PhoneCodeServiceImpl implements PhoneCodeService {

    private final PhoneCodeRepository phoneCodeRepository;
    private final PhoneCodeMapper phoneCodeMapper;

    @Override
    public List<PhoneCodeDto> getPhoneCodes() {
        List<PhoneCode> phoneCodes = phoneCodeRepository.findAll();
        List<PhoneCodeDto> phoneCodeDtos = phoneCodeMapper.toDto(phoneCodes);
        log.debug("Getting {} phone codes from database", phoneCodeDtos.size());
        return phoneCodeDtos;
    }

    @Override
    public boolean isPhoneCodeExist(Long id) {
        return phoneCodeRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PhoneCode getPhoneCodeById(Long id) {
        return phoneCodeRepository.getPhoneCodeById(id);
    }
}
