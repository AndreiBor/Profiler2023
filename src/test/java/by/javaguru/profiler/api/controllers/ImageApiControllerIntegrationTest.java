package by.javaguru.profiler.api.controllers;

import by.javaguru.profiler.MysqlSQLTestContainerExtension;
import by.javaguru.profiler.persistence.model.Image;
import by.javaguru.profiler.persistence.repository.ImageRepository;
import by.javaguru.profiler.usecasses.dto.ImageDto;
import by.javaguru.profiler.util.AuthenticationTestData;

import by.javaguru.profiler.util.ImageTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SqlGroup({
        @Sql(scripts = "classpath:testdata/add_images_test_data.sql",
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/clear_images_test_data.sql",
                executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MysqlSQLTestContainerExtension.class)
class ImageApiControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ImageRepository imageRepository;

    @Value("${image.storage-dir}")
    private String imageStorageLocation;

    private static final int IMAGE_UUID_LENGTH = 36;

    @BeforeEach
    void prepareImageFolder() throws IOException {
        Path location = Paths.get(imageStorageLocation);
        if (!Files.exists(location)) {
            Files.createDirectories(location);
        }
        ClassLoader classLoader = getClass().getClassLoader();
        File imageSource = new File(classLoader.getResource(ImageTestData.IMAGE_FOLDER + '/' + ImageTestData.ORIGINAL_FILENAME).getFile());
        Path imageTarget = Paths.get(imageStorageLocation, ImageTestData.IMAGE_UUID);
        Files.copy(imageSource.toPath(), imageTarget);
    }

    @AfterEach
    void clearImageFolder() throws IOException {
        Path location = Paths.get(imageStorageLocation);
        if (Files.exists(location)) {
            Files.walk(location)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    @Test
    void shouldReturn201AndJsonContentTypeWhenUploadImage() throws Exception {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();

        ByteArrayResource contentsAsResource = getFileToUpload();
        body.add(ImageTestData.REQUEST_PART_NAME, contentsAsResource);

        HttpHeaders headers = getAuthHeader();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<ImageDto> responseEntity = restTemplate.exchange(
                ImageTestData.IMAGES_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                ImageDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

        ImageDto responseDto = responseEntity.getBody();
        Image img = imageRepository.findByUuid(responseDto.uuid()).get();
        imageRepository.delete(img);
    }

    @Test
    void shouldReturn201AndRightLengthImageUuidWhenUploadImage() throws Exception {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();

        ByteArrayResource contentsAsResource = getFileToUpload();
        body.add(ImageTestData.REQUEST_PART_NAME, contentsAsResource);

        HttpHeaders headers = getAuthHeader();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<ImageDto> responseEntity = restTemplate.exchange(
                ImageTestData.IMAGES_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                ImageDto.class);

        ImageDto responseDto = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(IMAGE_UUID_LENGTH, responseDto.uuid().length());

        Image img = imageRepository.findByUuid(responseDto.uuid()).get();
        imageRepository.delete(img);
    }

    @Test
    void shouldReturn200AndImagePngContentTypeWhenGetImage() throws Exception {
        HttpEntity<String> requestEntity = new HttpEntity<>(getAuthHeader());
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(
                ImageTestData.SPECIFIED_IMAGE_URL_TEMPLATE,
                HttpMethod.GET,
                requestEntity,
                byte[].class,
                ImageTestData.IMAGE_UUID);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.IMAGE_PNG, responseEntity.getHeaders().getContentType());
    }

    @Test
    void shouldReturn200AndRightByteArrayWhenGetImage() throws Exception {
        HttpEntity<String> requestEntity = new HttpEntity<>(getAuthHeader());
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(
                ImageTestData.SPECIFIED_IMAGE_URL_TEMPLATE,
                HttpMethod.GET,
                requestEntity,
                byte[].class,
                ImageTestData.IMAGE_UUID);

        byte[] result = responseEntity.getBody();
        byte[] expected = ImageTestData.ACTUAL_IMAGE_BYTE_SOURCE;

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.IMAGE_PNG, responseEntity.getHeaders().getContentType());
        assertArrayEquals(expected, result);
    }

    @Test
    void shouldReturn200AndJsonContentTypeWhenReplaceImage() throws Exception {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();

        ByteArrayResource contentsAsResource = getFileToUpload();
        body.add(ImageTestData.REQUEST_PART_NAME, contentsAsResource);

        HttpHeaders headers = getAuthHeader();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<ImageDto> responseEntity = restTemplate.exchange(
                ImageTestData.SPECIFIED_IMAGE_URL_TEMPLATE,
                HttpMethod.PUT,
                requestEntity,
                ImageDto.class,
                ImageTestData.IMAGE_UUID);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    void shouldReturn200AndRightLengthImageUuidWhenReplaceImage() throws Exception {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();

        ByteArrayResource contentsAsResource = getFileToUpload();
        body.add(ImageTestData.REQUEST_PART_NAME, contentsAsResource);

        HttpHeaders headers = getAuthHeader();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<ImageDto> responseEntity = restTemplate.exchange(
                ImageTestData.SPECIFIED_IMAGE_URL_TEMPLATE,
                HttpMethod.PUT,
                requestEntity,
                ImageDto.class,
                ImageTestData.IMAGE_UUID);

        ImageDto responseDto = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(IMAGE_UUID_LENGTH, responseDto.uuid().length());
    }

    private ByteArrayResource getFileToUpload() {
        ByteArrayResource contentsAsResource = new ByteArrayResource(ImageTestData.BYTE_SOURCE) {
            @Override
            public String getFilename() {
                return ImageTestData.ORIGINAL_FILENAME;
            }
        };
        return contentsAsResource;
    }

    private HttpHeaders getAuthHeader() {
        HttpEntity<Map<String, String>> requestHttpAuthEntity = AuthenticationTestData.createLoginRequestHttpEntity();

        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
                AuthenticationTestData.AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestHttpAuthEntity,
                new ParameterizedTypeReference<>() {});

        Map<String, String> responseMap = responseEntity.getBody();
        String token = "Bearer " + responseMap.get("token");

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, token);
        return headers;
    }
}
