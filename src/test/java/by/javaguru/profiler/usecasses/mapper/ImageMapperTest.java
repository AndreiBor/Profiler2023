package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.Image;
import by.javaguru.profiler.usecasses.dto.ImageDto;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static by.javaguru.profiler.util.ImageTestData.createImage;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageMapperTest {

    private final ImageMapper imageMapper = Mappers.getMapper(ImageMapper.class);

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeFromEntityToDto() {
        Image image = createImage();
        ImageDto imageDto = imageMapper.imageToImageDto(image);

        assertEquals(image.getUuid(), imageDto.uuid());
    }
}
