package by.javaguru.profiler.usecasses.util;

import by.javaguru.profiler.api.exception.BadRequestException;
import by.javaguru.profiler.api.exception.EmptyFileException;
import by.javaguru.profiler.api.exception.WrongMediaTypeException;
import org.springframework.web.multipart.MultipartFile;

public class ValidateImage {

    private ValidateImage() {
    }

    public static void validate(MultipartFile image) {
        if (image == null) {
            throw new BadRequestException("No form-data was send!");
        }
        String originalFilename = image.getOriginalFilename();
        if (image.isEmpty()) {
            throw new EmptyFileException(String.format("Failed to store empty file %s", originalFilename));
        }
        String contentType = image.getContentType();
        if (!ValidationConstants.CONTENT_TYPES.contains(contentType)) {
            throw new WrongMediaTypeException(String.format("Not an image %s", originalFilename));
        }
    }
}
