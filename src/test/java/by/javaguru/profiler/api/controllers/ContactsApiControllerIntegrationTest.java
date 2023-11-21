package by.javaguru.profiler.api.controllers;

import by.javaguru.profiler.MysqlSQLTestContainerExtension;
import by.javaguru.profiler.usecasses.dto.ContactsDto;
import by.javaguru.profiler.usecasses.dto.ContactsResponseDto;
import by.javaguru.profiler.util.AuthenticationTestData;
import by.javaguru.profiler.util.ContactTestData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SqlGroup({
        @Sql(scripts = "classpath:testdata/add_cvs.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/add_contacts_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/clear_contacts_test_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MysqlSQLTestContainerExtension.class)
class ContactsApiControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn200AndCorrectJsonWhenGetContactsSuccessful() throws JsonProcessingException {
        ContactsResponseDto expectedResponse = ContactTestData.contactsResponseDto().build();
        HttpEntity<String> requestEntity = new HttpEntity<>(getAuthHeader());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                ContactTestData.CONTACTS_URL_TEMPLATE,
                HttpMethod.GET,
                requestEntity,
                String.class);

        ContactsResponseDto actualResponse = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {
        });

        assertEquals(expectedResponse, actualResponse);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    void shouldReturn201AndCorrectJsonWhenCreateSuccessful() throws JsonProcessingException {
        ContactsResponseDto expectedResponse = ContactTestData.contactsResponseDto().build();
        ContactsDto request = ContactTestData.createContactDto().build();
        HttpEntity<ContactsDto> requestEntity = new HttpEntity<>(request, getAuthHeader());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                ContactTestData.CONTACTS_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                String.class);

        ContactsResponseDto actualResponse = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {
        });

        assertEquals(expectedResponse, actualResponse);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    void shouldReturn200AndCorrectJsonWhenUpdateSuccessful() throws JsonProcessingException {
        ContactsResponseDto expectedResponse = ContactTestData.contactsResponseDto().withEmail("updated@email.com").build();
        ContactsDto request = ContactTestData.createContactDto().withEmail("updated@email.com").build();
        HttpEntity<ContactsDto> requestEntity = new HttpEntity<>(request, getAuthHeader());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                ContactTestData.CONTACTS_URL_TEMPLATE,
                HttpMethod.PUT,
                requestEntity,
                String.class);

        ContactsResponseDto actualResponse = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {
        });

        assertEquals(expectedResponse, actualResponse);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @SneakyThrows
    private HttpHeaders getAuthHeader() {
        HttpEntity<Map<String, String>> requestHttpAuthEntity = AuthenticationTestData.createLoginRequestHttpEntity();

        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
                AuthenticationTestData.AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestHttpAuthEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        Map<String, String> responseMap = responseEntity.getBody();
        String token = "Bearer " + responseMap.get("token");

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, token);
        return headers;
    }
}
