package by.javaguru.profiler.usecasses;


import by.javaguru.profiler.usecasses.dto.UserProfileDto;
import by.javaguru.profiler.usecasses.dto.UserProfileResponseDto;

import java.util.Optional;

public interface UserProfileService {

    UserProfileResponseDto saveUserProfile(UserProfileDto userProfileDto);

    UserProfileResponseDto getUserProfile();

    Optional<UserProfileResponseDto> updateUserProfile(UserProfileDto userProfileDto);
}
