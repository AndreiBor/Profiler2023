package by.javaguru.profiler.usecasses.impl;

import by.javaguru.profiler.api.exception.BadRequestException;
import by.javaguru.profiler.persistence.model.Image;
import by.javaguru.profiler.persistence.model.User;
import by.javaguru.profiler.persistence.repository.ImageRepository;
import by.javaguru.profiler.persistence.repository.UserRepository;
import by.javaguru.profiler.usecasses.ImageValidationService;
import by.javaguru.profiler.usecasses.util.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageValidationServiceImpl implements ImageValidationService {

    private final UserRepository userRepository;

    private final ImageRepository imageRepository;

    private final AuthService authService;

    @Override
    public boolean validateImageForCv(String imageUuid) {
        return !imageRepository.isImageBelongUserProfile(imageUuid);
    }

    @Override
    public boolean validateImageForProfile(String imageUuid) {
        return !imageRepository.isImageBelongCurriculumVitae(imageUuid);
    }

    @Override
    public boolean isImageBelongsToUser(String imageUuid) {
        Image image = imageRepository.findByUuid(imageUuid)
                .orElseThrow(() -> new BadRequestException(String.format("No such image %s!", imageUuid)));
        User user = userRepository.findByEmail(authService.getUsername());
        return image.getUser().getId().equals(user.getId());
    }
}
