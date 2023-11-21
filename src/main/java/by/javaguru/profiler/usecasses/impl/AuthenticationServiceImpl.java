package by.javaguru.profiler.usecasses.impl;

import by.javaguru.profiler.persistence.model.User;
import by.javaguru.profiler.persistence.repository.UserRepository;
import by.javaguru.profiler.usecasses.AuthenticationService;
import by.javaguru.profiler.usecasses.dto.AuthenticationRequestDto;
import by.javaguru.profiler.usecasses.dto.AuthenticationUserDto;
import by.javaguru.profiler.usecasses.mapper.AuthenticationUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationUserMapper authenticationUserMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationUserDto findByEmailAndPassword(AuthenticationRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.email());
        if (user != null && passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            AuthenticationUserDto authenticationUserDto = authenticationUserMapper.userToAuthenticationUserDto(user);
            log.info("IN findByEmailAndPassword - authenticationUserDto: {} found by email: {}", authenticationUserDto,
                    authenticationUserDto.email());
            return authenticationUserDto;
        } else {
            log.info("IN findByEmailAndPassword - Invalid username or password");
            return null;
        }
    }
}
