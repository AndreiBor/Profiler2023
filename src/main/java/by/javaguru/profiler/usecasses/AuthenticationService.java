package by.javaguru.profiler.usecasses;

import by.javaguru.profiler.usecasses.dto.AuthenticationRequestDto;
import by.javaguru.profiler.usecasses.dto.AuthenticationUserDto;

public interface AuthenticationService {

    AuthenticationUserDto findByEmailAndPassword(AuthenticationRequestDto requestDto);
}
