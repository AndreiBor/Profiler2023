package by.javaguru.profiler.api.controllers;

import by.javaguru.profiler.usecasses.ContactsService;
import by.javaguru.profiler.usecasses.CurriculumVitaeService;
import by.javaguru.profiler.usecasses.PhoneCodeService;
import by.javaguru.profiler.usecasses.dto.ContactsDto;
import by.javaguru.profiler.usecasses.dto.ContactsResponseDto;
import by.javaguru.profiler.util.ContactTestData;
import by.javaguru.profiler.util.CurriculumVitaeTestData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ContactsApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class ContactsApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    ContactsService contactsService;

    @MockBean
    private CurriculumVitaeService curriculumVitaeService;

    @MockBean
    PhoneCodeService phoneCodeService;


    @BeforeEach
    void setup() {
        when(curriculumVitaeService.isCurriculumVitaeExists(CurriculumVitaeTestData.CV_UUID)).thenReturn(true);
        when(phoneCodeService.isPhoneCodeExist(any())).thenReturn(true);
    }

    @Nested
    class Get {
        @Test
        void shouldReturn200AndInvokeServiceWhenGetContact() throws Exception {
            ContactsResponseDto contactsResponseDto = ContactTestData.contactsResponseDto().build();
            when(contactsService.getContacts(CurriculumVitaeTestData.CV_UUID)).thenReturn(contactsResponseDto);

            mockMvc.perform(MockMvcRequestBuilders.get(ContactTestData.CONTACTS_URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(contactsService, times(1)).getContacts(CurriculumVitaeTestData.CV_UUID);
            verify(curriculumVitaeService, times(1)).isCurriculumVitaeExists(CurriculumVitaeTestData.CV_UUID);
        }

        @Test
        void shouldReturn200AndJsonResponseWhenGetContactsSuccessful() throws Exception {
            ContactsResponseDto expectedContactResponseDto = ContactTestData.contactsResponseDto().build();
            when(contactsService.getContacts(CurriculumVitaeTestData.CV_UUID)).thenReturn(expectedContactResponseDto);

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(ContactTestData.CONTACTS_URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();
            ContactsResponseDto actualResult = objectMapper.readValue(responseBody, new TypeReference<>() {
            });

            assertEquals(expectedContactResponseDto, actualResult);
        }
    }

    @Nested
    class Post {
        @Test
        void shouldReturn201WhenCreateContactsAreSuccessful() throws Exception {
            ContactsDto contactsDto = ContactTestData.createContactDto().build();
            ContactsResponseDto contactsResponseDto = ContactTestData.contactsResponseDto().build();

            when(contactsService.saveContacts(CurriculumVitaeTestData.CV_UUID, contactsDto)).thenReturn(contactsResponseDto);

            mockMvc.perform(MockMvcRequestBuilders.post(ContactTestData.CONTACTS_URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(contactsDto)))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

        @Test
        void shouldReturn201AndCorrectContactsJsonWhenCreateContactsAreSuccessful() throws Exception {
            ContactsDto contactsDto = ContactTestData.createContactDto().build();
            ContactsResponseDto contactsResponseDto = ContactTestData.contactsResponseDto().build();

            when(contactsService.saveContacts(CurriculumVitaeTestData.CV_UUID, contactsDto)).thenReturn(contactsResponseDto);

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(ContactTestData.CONTACTS_URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(contactsDto)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andReturn();

            String expectedJson = objectMapper.writeValueAsString(contactsResponseDto);
            String resultJson = mvcResult.getResponse().getContentAsString();
            assertEquals(expectedJson, resultJson);
        }

        @Test
        void shouldInvokeContactsBusinessLogicWhenCreateContactsAreSuccessful() throws Exception {
            ContactsDto contactsDto = ContactTestData.createContactDto().build();
            ContactsResponseDto contactsResponseDto = ContactTestData.contactsResponseDto().build();

            when(contactsService.saveContacts(CurriculumVitaeTestData.CV_UUID, contactsDto)).thenReturn(contactsResponseDto);

            mockMvc.perform(MockMvcRequestBuilders.post(ContactTestData.CONTACTS_URL_TEMPLATE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(contactsDto)));

            verify(contactsService, times(1)).saveContacts(CurriculumVitaeTestData.CV_UUID, contactsDto);
            verify(curriculumVitaeService, times(1)).isCurriculumVitaeExists(CurriculumVitaeTestData.CV_UUID);
            verify(phoneCodeService, times(2)).isPhoneCodeExist(contactsDto.phoneCodeId());
        }

        @Test
        @DisplayName("Should return 404 when CV UUID is invalid")
        void shouldReturn404WhenCVUUIDIsInvalid() throws Exception {
            ContactsDto contactsDto = ContactTestData.createContactDto().build();
            String urlWithInvalidUuid = "/api/v1/cvs/234234sdf/contacts";
            ContactsResponseDto contactsResponseDto = ContactTestData.contactsResponseDto().build();

            when(contactsService.saveContacts(CurriculumVitaeTestData.CV_UUID, contactsDto)).thenReturn(contactsResponseDto);

            mockMvc.perform(post(urlWithInvalidUuid)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(contactsDto)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(containsString("CV with UUID 234234sdf not found!!!")));
        }
    }

    @Nested
    class Update {
        @Test
        void shouldReturn200WhenUpdateContactsAreSuccessful() throws Exception {
            ContactsDto contactsDto = ContactTestData.createContactDto().build();
            ContactsResponseDto contactsResponseDto = ContactTestData.contactsResponseDto().build();

            when(contactsService.saveContacts(CurriculumVitaeTestData.CV_UUID, contactsDto)).thenReturn(contactsResponseDto);

            mockMvc.perform(MockMvcRequestBuilders.put(ContactTestData.CONTACTS_URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(contactsDto)))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        void shouldReturn200AndExpectedContactsResponseJsonWhenUpdateContactsAreSuccessful() throws Exception {
            ContactsDto contactsDto = ContactTestData.createContactDto().build();
            ContactsResponseDto contactsResponseDto = ContactTestData.contactsResponseDto().build();

            when(contactsService.updateContacts(CurriculumVitaeTestData.CV_UUID, contactsDto)).thenReturn(contactsResponseDto);

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(ContactTestData.CONTACTS_URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(contactsDto)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

            String expectedJson = objectMapper.writeValueAsString(contactsResponseDto);
            String resultJson = mvcResult.getResponse().getContentAsString();
            assertEquals(expectedJson, resultJson);
        }

        @Test
        void shouldInvokeContactsBusinessLogicWhenUpdateContactsAreSuccessful() throws Exception {
            ContactsDto contactsDto = ContactTestData.createContactDto().build();
            ContactsResponseDto contactsResponseDto = ContactTestData.contactsResponseDto().build();

            when(contactsService.updateContacts(CurriculumVitaeTestData.CV_UUID, contactsDto)).thenReturn(contactsResponseDto);

            mockMvc.perform(MockMvcRequestBuilders.put(ContactTestData.CONTACTS_URL_TEMPLATE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(contactsDto)));

            verify(contactsService, times(1)).updateContacts(CurriculumVitaeTestData.CV_UUID, contactsDto);
            verify(curriculumVitaeService, times(1)).isCurriculumVitaeExists(CurriculumVitaeTestData.CV_UUID);
            verify(phoneCodeService, times(2)).isPhoneCodeExist(contactsDto.phoneCodeId());
        }

        @Test
        void shouldReturn404WhenUpdateContactWithInvalidCVUUIDI() throws Exception {
            ContactsDto contactsDto = ContactTestData.createContactDto().build();
            String urlWithInvalidUuid = "/api/v1/cvs/234234sdf/contacts";
            ContactsResponseDto contactsResponseDto = ContactTestData.contactsResponseDto().build();

            when(contactsService.updateContacts(CurriculumVitaeTestData.CV_UUID, contactsDto)).thenReturn(contactsResponseDto);

            mockMvc.perform(put(urlWithInvalidUuid)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(contactsDto)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(containsString("CV with UUID 234234sdf not found!!!")));
        }
    }

    @Nested
    class ValidationPost {
        @Test
        @DisplayName("Validation should return 400 when PhoneCodeId is null")
        void shouldReturn400WhenPhoneCodeIdIsInvalid() throws Exception {
            ContactsDto contactsDto = ContactTestData.createContactDto()
                    .withPhoneCodeId(null)
                    .build();

            ContactsResponseDto contactsResponseDto = ContactTestData.contactsResponseDto().build();

            when(contactsService.saveContacts(CurriculumVitaeTestData.CV_UUID, contactsDto)).thenReturn(contactsResponseDto);

            mockMvc.perform(MockMvcRequestBuilders.post(ContactTestData.CONTACTS_URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(contactsDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("Field must not be empty")));
        }

        @ParameterizedTest
        @DisplayName("Validation should return 400 when phone number is invalid")
        @MethodSource("by.javaguru.profiler.api.controllers.ContactsApiControllerTest#getInvalidArgumentsForPhoneNumberValidation")
        void shouldReturn400WhenDtoIsInvalid(String phoneNumber, String error) throws Exception {
            ContactsDto contactsDto = ContactTestData.createContactDto()
                    .withPhoneNumber(phoneNumber)
                    .build();

            ContactsResponseDto contactsResponseDto = ContactTestData.contactsResponseDto().build();

            when(contactsService.saveContacts(CurriculumVitaeTestData.CV_UUID, contactsDto)).thenReturn(contactsResponseDto);

            mockMvc.perform(MockMvcRequestBuilders.post(ContactTestData.CONTACTS_URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(contactsDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString(error)));
        }

        @ParameterizedTest
        @DisplayName("Validation should return 400 when email is invalid")
        @MethodSource("by.javaguru.profiler.api.controllers.ContactsApiControllerTest#getInvalidArgumentsForEmailValidation")
        void shouldReturn400WhenEmailIsInvalid(String email, String error) throws Exception {
            ContactsDto contactsDto = ContactTestData.createContactDto()
                    .withEmail(email)
                    .build();

            ContactsResponseDto contactsResponseDto = ContactTestData.contactsResponseDto().build();

            when(contactsService.saveContacts(CurriculumVitaeTestData.CV_UUID, contactsDto)).thenReturn(contactsResponseDto);

            mockMvc.perform(MockMvcRequestBuilders.post(ContactTestData.CONTACTS_URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(contactsDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString(error)));
        }

        @ParameterizedTest
        @DisplayName("Validation should return 400 when skype address is invalid")
        @MethodSource("by.javaguru.profiler.api.controllers.ContactsApiControllerTest#getInvalidArgumentsForSkypeValidation")
        void shouldReturn400WhenSkypeIsInvalid(String skype, String error) throws Exception {
            ContactsDto contactsDto = ContactTestData.createContactDto()
                    .withSkype(skype)
                    .build();

            ContactsResponseDto contactsResponseDto = ContactTestData.contactsResponseDto().build();

            when(contactsService.saveContacts(CurriculumVitaeTestData.CV_UUID, contactsDto)).thenReturn(contactsResponseDto);

            mockMvc.perform(MockMvcRequestBuilders.post(ContactTestData.CONTACTS_URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(contactsDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString(error)));
        }

        @ParameterizedTest
        @DisplayName("Validation should return 400 when linkedIn link is invalid")
        @MethodSource("by.javaguru.profiler.api.controllers.ContactsApiControllerTest#getInvalidArgumentsForLinkedInLinkValidation")
        void shouldReturn400WhenLinkedinLinkIsInvalid(String linkedin, String error) throws Exception {
            ContactsDto contactsDto = ContactTestData.createContactDto()
                    .withLinkedin(linkedin)
                    .build();

            ContactsResponseDto contactsResponseDto = ContactTestData.contactsResponseDto().build();

            when(contactsService.saveContacts(CurriculumVitaeTestData.CV_UUID, contactsDto)).thenReturn(contactsResponseDto);

            mockMvc.perform(MockMvcRequestBuilders.post(ContactTestData.CONTACTS_URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(contactsDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString(error)));
        }

        @ParameterizedTest
        @DisplayName("Validation should return 400 when portfolio link is invalid")
        @MethodSource("by.javaguru.profiler.api.controllers.ContactsApiControllerTest#getInvalidArgumentsForPortfolioLinkValidation")
        void shouldReturn400WhenPortfolioLinkIsInvalid(String portfolio, String error) throws Exception {
            ContactsDto contactsDto = ContactTestData.createContactDto()
                    .withPortfolio(portfolio)
                    .build();

            ContactsResponseDto contactsResponseDto = ContactTestData.contactsResponseDto().build();

            when(contactsService.saveContacts(CurriculumVitaeTestData.CV_UUID, contactsDto)).thenReturn(contactsResponseDto);

            mockMvc.perform(MockMvcRequestBuilders.post(ContactTestData.CONTACTS_URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(contactsDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString(error)));
        }
    }

    static Stream<Arguments> getInvalidArgumentsForPhoneNumberValidation() {
        return Stream.of(Arguments.of("", ContactTestData.FIELD_NOT_BE_EMPTY_ERROR),
                Arguments.of(" ", ContactTestData.FIELD_NOT_BE_EMPTY_ERROR),
                Arguments.of("111 22 33", ContactTestData.REGEXP_VALIDATE_CELL_PHONE_ERROR),
                Arguments.of("11one2233", ContactTestData.REGEXP_VALIDATE_CELL_PHONE_ERROR),
                Arguments.of("12345678901234567890123456", ContactTestData.MAXLENGTH_PHONE_NUMBER_ERROR),
                Arguments.of(null, ContactTestData.FIELD_NOT_BE_EMPTY_ERROR));
    }

    static Stream<Arguments> getInvalidArgumentsForEmailValidation() {
        return Stream.of(Arguments.of("", ContactTestData.REGEXP_VALIDATE_EMAIL_ERROR),
                Arguments.of(null, ContactTestData.FIELD_NOT_BE_NULL_ERROR),
                Arguments.of("maxLengthOver50aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@gmail.com", ContactTestData.MAXLENGTH_EMAIL_ERROR),
                Arguments.of("not_valid_email_address", ContactTestData.REGEXP_VALIDATE_EMAIL_ERROR),
                Arguments.of("", ContactTestData.REGEXP_VALIDATE_EMAIL_ERROR),
                Arguments.of(" ", ContactTestData.REGEXP_VALIDATE_EMAIL_ERROR));
    }

    static Stream<Arguments> getInvalidArgumentsForSkypeValidation() {
        return Stream.of(
                Arguments.of("", ContactTestData.LINK_ERROR_FIELD_MUST_BE_FILLED_ERROR),
                Arguments.of("   ", ContactTestData.LINK_ERROR_FIELD_MUST_BE_FILLED_ERROR),
                Arguments.of("lengthOver50symbolsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", ContactTestData.MAXLENGTH_SKYPE_ADDRESS_ERROR));
    }

    static Stream<Arguments> getInvalidArgumentsForLinkedInLinkValidation() {
        return Stream.of(
                Arguments.of("", ContactTestData.FIELD_NOT_BE_EMPTY_ERROR),
                Arguments.of(" ", ContactTestData.FIELD_NOT_BE_EMPTY_ERROR),
                Arguments.of(null, ContactTestData.FIELD_NOT_BE_EMPTY_ERROR),
                Arguments.of("https://linkedin.com/over255symbolsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", ContactTestData.MAXLENGTH_LINKEDIN_LINK_ERROR)
        );
    }

    static Stream<Arguments> getInvalidArgumentsForPortfolioLinkValidation() {
        return Stream.of(
                Arguments.of("https://github.com/over255symbolsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", ContactTestData.MAXLENGTH_PORTFOLIO_LINK_ERROR),
                Arguments.of("", ContactTestData.LINK_ERROR_FIELD_MUST_BE_FILLED_ERROR),
                Arguments.of(" ", ContactTestData.LINK_ERROR_FIELD_MUST_BE_FILLED_ERROR));

    }
}