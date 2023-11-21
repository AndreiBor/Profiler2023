package by.javaguru.profiler.api.controllers;

import by.javaguru.profiler.api.exception.BadRequestException;
import by.javaguru.profiler.usecasses.ImageService;
import by.javaguru.profiler.usecasses.dto.ImageDto;
import by.javaguru.profiler.usecasses.util.AuthService;
import by.javaguru.profiler.usecasses.util.ValidateImage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "Image Controller", description = "API for working with images")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/images")
public class ImageApiController {

    private final ImageService imageService;

    private final AuthService authService;

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload image")
    public ResponseEntity<ImageDto> uploadImage(@NotNull @RequestPart("image") MultipartFile image) {
        ValidateImage.validate(image);
        String username = authService.getUsername();
        log.debug("Received file with contentType: {}, file name is: {}, file size is: {}, from user: {}",
                image.getContentType(),
                image.getOriginalFilename(),
                image.getSize(),
                username);
        try {
            ImageDto imageDto = imageService.storageImage(image.getInputStream(), username);
            return new ResponseEntity<>(imageDto, HttpStatus.CREATED);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @PutMapping(value = "/{uuid}", consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Replace image", description = "Replace image by uuid")
    public ResponseEntity<ImageDto> replaceImage(@NotNull @RequestPart("image") MultipartFile image, @PathVariable(name = "uuid") String uuid) {
        ValidateImage.validate(image);
        String username = authService.getUsername();
        log.debug("Received file with contentType: {}, file name is: {}, file size is: {}, from user: {}",
                image.getContentType(),
                image.getOriginalFilename(),
                image.getSize(),
                username);
        try {
            ImageDto imageDto = imageService.replaceImage(image.getInputStream(), uuid);
            return new ResponseEntity<>(imageDto, HttpStatus.OK);
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Get image", description = "Get image by uuid")
    @GetMapping(value = "/{uuid}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable(name = "uuid") String uuid) {
        String username = authService.getUsername();
        try {
            byte[] image = imageService.getImage(uuid);
            log.debug("User {} is successful download image with uuid {}", username, uuid);
            return new ResponseEntity<>(image, HttpStatus.OK);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }
}
