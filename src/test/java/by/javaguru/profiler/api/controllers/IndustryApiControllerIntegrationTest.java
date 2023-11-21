package by.javaguru.profiler.api.controllers;

import by.javaguru.profiler.MysqlSQLTestContainerExtension;
import by.javaguru.profiler.usecasses.dto.IndustryResponseDto;
import by.javaguru.profiler.util.AuthenticationTestData;
import by.javaguru.profiler.util.IndustryTestData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MysqlSQLTestContainerExtension.class)
class IndustryApiControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnExpectedIndustryResponseJsonAnd200WhenGetListOfIndustries() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(
                IndustryTestData.INDUSTRY_URL_TEMPLATE,
                HttpMethod.GET,
                getAuthHttpEntity(),
                String.class);

        List<IndustryResponseDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        Assertions.assertEquals(IndustryTestData.EXPECTED_NUMBER_OF_INDUSTRIES, result.size());
    }

    @SneakyThrows
    private HttpEntity<String> getAuthHttpEntity() {
        HttpEntity<Map<String, String>> requestHttpAuthEntity = AuthenticationTestData.createLoginRequestHttpEntity();

        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
                AuthenticationTestData.AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestHttpAuthEntity,
                new ParameterizedTypeReference<>() {}
        );

        Map<String, String> responseMap = responseEntity.getBody();
        String token = "Bearer " + responseMap.get("token");

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, token);
        return new HttpEntity<>(headers);
    }
}
