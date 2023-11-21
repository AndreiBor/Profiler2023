package by.javaguru.profiler.usecasses;

public interface ImageValidationService {
    boolean validateImageForCv(String imageUuid);

    boolean validateImageForProfile(String imageUuid);

    boolean isImageBelongsToUser(String imageUuid);
}
