package by.javaguru.profiler.usecasses;

import by.javaguru.profiler.api.exception.ImageStorageException;
import by.javaguru.profiler.persistence.model.Image;
import by.javaguru.profiler.usecasses.dto.ImageDto;

import java.io.IOException;
import java.io.InputStream;

public interface ImageService {

    ImageDto storageImage(InputStream imageInputStream, String username) throws ImageStorageException;

    ImageDto replaceImage(InputStream imageInputStream, String uuid) throws ImageStorageException;

    byte[] getImage(String imageName) throws IOException;

    void deleteStoredImageFile(Image image);

    boolean isImageChanging(String incomingImageUuid, Image storedImage);

    String replaceImage(String incomingImageUuid, Image storedImage);
}
