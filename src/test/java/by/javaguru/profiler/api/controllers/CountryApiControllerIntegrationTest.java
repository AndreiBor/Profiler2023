package by.javaguru.profiler.api.controllers;

import by.javaguru.profiler.MysqlSQLTestContainerExtension;
import by.javaguru.profiler.usecasses.dto.CountryDto;
import by.javaguru.profiler.util.AuthenticationTestData;

import by.javaguru.profiler.util.CountryTestData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import lombok.SneakyThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MysqlSQLTestContainerExtension.class)
class CountryApiControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn200AndJsonContentTypeWhenGetListOfCountries() {
        HttpEntity<String> requestEntity = new HttpEntity<>(getAuthHeader());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                CountryTestData.COUNTRIES_URL_TEMPLATE,
                HttpMethod.GET,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    void shouldReturnExpectedNumberOfCountriesWhenGetListOfCountries() throws JsonProcessingException {
        HttpEntity<String> requestEntity = new HttpEntity<>(getAuthHeader());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                CountryTestData.COUNTRIES_URL_TEMPLATE,
                HttpMethod.GET,
                requestEntity,
                String.class);

        List<CountryDto> actualResponse = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {});

        assertThat(actualResponse).hasSize(CountryTestData.NUMBER_OF_COUNTRIES_IN_DB);

    }

    @SneakyThrows
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
