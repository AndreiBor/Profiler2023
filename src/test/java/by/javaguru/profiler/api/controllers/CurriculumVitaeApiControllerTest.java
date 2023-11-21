package by.javaguru.profiler.api.controllers;

import by.javaguru.profiler.usecasses.CountryService;
import by.javaguru.profiler.usecasses.CurriculumVitaeService;
import by.javaguru.profiler.usecasses.ImageValidationService;
import by.javaguru.profiler.usecasses.PositionService;
import by.javaguru.profiler.usecasses.dto.CurriculumVitaeRequestDto;
import by.javaguru.profiler.usecasses.dto.CurriculumVitaeResponseDto;
import by.javaguru.profiler.usecasses.util.AuthService;
import by.javaguru.profiler.util.CurriculumVitaeTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CurriculumVitaeApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class CurriculumVitaeApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CurriculumVitaeService curriculumVitaeService;

    @MockBean
    private AuthService authService;

    @MockBean
    private ImageValidationService imageValidationService;

    @MockBean
    private CountryService countryService;

    @MockBean
    private PositionService positionService;

    private static final String INVALID_NAME = "Invalid Namenamenamenamenamenamenamenamenamenamename";
    private static final String INVALID_SURNAME = "Invalid Surnamenamenamenamenamenamenamenamenamename";
    private static final String INVALID_CITY = "Invalid City-citycitycitycitycitycitycitycitycitycity";
    private static final CurriculumVitaeRequestDto CURRICULUM_VITAE_REQUEST_DTO =
            CurriculumVitaeTestData.getValidCvRequestDto().build();
    private static final CurriculumVitaeResponseDto CURRICULUM_VITAE_RESPONSE_DTO =
            CurriculumVitaeTestData.getCvResponseDtoByCvRequestDto(CURRICULUM_VITAE_REQUEST_DTO).build();

    @Test
    void shouldReturn400WhenSavePersonalInfoSectionIfImageUuidNotBelongsToUser() throws Exception {
        when(imageValidationService.isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(false);
        when(countryService.isCountryExist(CurriculumVitaeTestData.COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(CurriculumVitaeTestData.POSITION_ID)).thenReturn(true);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(
                                CurriculumVitaeTestData.CVS_URL_TEMPLATE
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("imageUuid").value("Image UUID is not valid!"));

        verify(imageValidationService, times(1)).isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID);
    }

    @Test
    void shouldReturn400WhenSavePersonalInfoSectionIfImageUuidIsUsedInPersonalDetails() throws Exception {

        when(imageValidationService.isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(false);
        when(countryService.isCountryExist(CurriculumVitaeTestData.COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(CurriculumVitaeTestData.POSITION_ID)).thenReturn(true);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(
                                CurriculumVitaeTestData.CVS_URL_TEMPLATE
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("imageUuid").value("Image UUID is not valid!"));

        verify(imageValidationService, times(1)).isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID);
        verify(imageValidationService, times(1)).validateImageForCv(CurriculumVitaeTestData.IMAGE_UUID);
    }

    @Test
    void shouldReturn400WhenSavePersonalInfoSectionIfCountryIdIsNotExist() throws Exception {

        when(imageValidationService.isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(CurriculumVitaeTestData.COUNTRY_ID)).thenReturn(false);
        when(positionService.isPositionExist(CurriculumVitaeTestData.POSITION_ID)).thenReturn(true);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(
                                CurriculumVitaeTestData.CVS_URL_TEMPLATE
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("countryId").value("Invalid id: country not found"));

        verify(countryService, times(1)).isCountryExist(CurriculumVitaeTestData.COUNTRY_ID);
    }

    @Test
    void shouldReturn400WhenSavePersonalInfoSectionIfPositionIdIsNotExist() throws Exception {

        when(imageValidationService.isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(CurriculumVitaeTestData.COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(CurriculumVitaeTestData.POSITION_ID)).thenReturn(false);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(
                                CurriculumVitaeTestData.CVS_URL_TEMPLATE
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("positionId").value("Invalid id: position not found"));

        verify(positionService, times(1)).isPositionExist(CurriculumVitaeTestData.POSITION_ID);
    }

    @Test
    void shouldReturn400WhenSavePersonalInfoSectionIfCreationOfNewCvIsNotAvailable() throws Exception {

        when(imageValidationService.isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(CurriculumVitaeTestData.COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(CurriculumVitaeTestData.POSITION_ID)).thenReturn(true);
        when(curriculumVitaeService.isCreationCvAvailable()).thenReturn(false);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(
                                CurriculumVitaeTestData.CVS_URL_TEMPLATE
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("statusCode").value(400))
                .andExpect(jsonPath("message").value("Curriculum vitae creation limit exceeded"))
                .andExpect(jsonPath("timeStamp").exists());

        verify(curriculumVitaeService, times(1)).isCreationCvAvailable();
    }

    @Test
    void shouldReturn201WhenSaveValidInputPersonalInfoSection() throws Exception {

        when(imageValidationService.isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(CurriculumVitaeTestData.COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(CurriculumVitaeTestData.POSITION_ID)).thenReturn(true);
        when(curriculumVitaeService.isCreationCvAvailable()).thenReturn(true);

        mockMvc.perform(
                MockMvcRequestBuilders.post(
                        CurriculumVitaeTestData.CVS_URL_TEMPLATE
                ).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
        ).andExpect(status().isCreated());
    }

    @Test
    void shouldReturnPersonalInfoSectionWhenSaveValidPersonalInfoSectionAndCallBusinessLogic() throws Exception {

        when(imageValidationService.isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(CurriculumVitaeTestData.COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(CurriculumVitaeTestData.POSITION_ID)).thenReturn(true);
        when(curriculumVitaeService.isCreationCvAvailable()).thenReturn(true);
        when(curriculumVitaeService.save(CURRICULUM_VITAE_REQUEST_DTO)).thenReturn(CURRICULUM_VITAE_RESPONSE_DTO);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(
                                CurriculumVitaeTestData.CVS_URL_TEMPLATE
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("imageUuid").value(CURRICULUM_VITAE_REQUEST_DTO.imageUuid()))
                .andExpect(jsonPath("name").value(CURRICULUM_VITAE_REQUEST_DTO.name()))
                .andExpect(jsonPath("surname").value(CURRICULUM_VITAE_REQUEST_DTO.surname()))
                .andExpect(jsonPath("positionId").value(CURRICULUM_VITAE_REQUEST_DTO.positionId()))
                .andExpect(jsonPath("countryId").value(CURRICULUM_VITAE_REQUEST_DTO.countryId()))
                .andExpect(jsonPath("city").value(CURRICULUM_VITAE_REQUEST_DTO.city()))
                .andExpect(jsonPath("isReadyToRelocate").value(CURRICULUM_VITAE_REQUEST_DTO.isReadyToRelocate()))
                .andExpect(jsonPath("isReadyForRemoteWork").value(CURRICULUM_VITAE_REQUEST_DTO.isReadyForRemoteWork()))
                .andExpect(jsonPath("uuid").exists());

        verify(curriculumVitaeService, times(1)).save(CURRICULUM_VITAE_REQUEST_DTO);
    }

    @Test
    void shouldReturn400WhenSaveCvRequestWithInvalidNameField() throws Exception {

        CurriculumVitaeRequestDto CvRequestDtoWithInvalidName =
                CurriculumVitaeTestData.getValidCvRequestDto().withName(INVALID_NAME).build();

        when(imageValidationService.isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(CurriculumVitaeTestData.COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(CurriculumVitaeTestData.POSITION_ID)).thenReturn(true);
        when(curriculumVitaeService.isCreationCvAvailable()).thenReturn(true);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(
                                CurriculumVitaeTestData.CVS_URL_TEMPLATE
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CvRequestDtoWithInvalidName))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("name").value("Maximum length of name is 50 symbols"));
    }

    @Test
    void shouldReturn400WhenSaveCvRequestWithInvalidSurnameField() throws Exception {

        CurriculumVitaeRequestDto CvRequestDtoWithInvalidSurname =
                CurriculumVitaeTestData.getValidCvRequestDto().withSurname(INVALID_SURNAME).build();

        when(imageValidationService.isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(CurriculumVitaeTestData.COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(CurriculumVitaeTestData.POSITION_ID)).thenReturn(true);
        when(curriculumVitaeService.isCreationCvAvailable()).thenReturn(true);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(
                                CurriculumVitaeTestData.CVS_URL_TEMPLATE
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CvRequestDtoWithInvalidSurname))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("surname").value("Maximum length of surname is 50 symbols"));
    }

    @Test
    void shouldReturn400WhenSaveCvRequestWithInvalidCityField() throws Exception {

        CurriculumVitaeRequestDto CvRequestDtoWithInvalidCity =
                CurriculumVitaeTestData.getValidCvRequestDto().withCity(INVALID_CITY).build();

        when(imageValidationService.isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(CurriculumVitaeTestData.COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(CurriculumVitaeTestData.POSITION_ID)).thenReturn(true);
        when(curriculumVitaeService.isCreationCvAvailable()).thenReturn(true);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(
                                CurriculumVitaeTestData.CVS_URL_TEMPLATE
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CvRequestDtoWithInvalidCity))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("city").value("Maximum length of city name is 50 symbols"));
    }

    @Test
    void shouldReturn200WhenGetPersonalInfoSection() throws Exception {

        when(curriculumVitaeService.isCurriculumVitaeExists(CurriculumVitaeTestData.CV_UUID)).thenReturn(true);

        mockMvc.perform(
                        MockMvcRequestBuilders.get(
                                CurriculumVitaeTestData.CVS_UUID_URL_TEMPLATE,
                                CurriculumVitaeTestData.CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnCvResponseDtoWhenGetPersonalInfoSectionAndCallBusinessLogic() throws Exception {

        when(curriculumVitaeService.isCurriculumVitaeExists(CurriculumVitaeTestData.CV_UUID)).thenReturn(true);
        when(curriculumVitaeService.getCvOfUser(CurriculumVitaeTestData.CV_UUID)).thenReturn(CURRICULUM_VITAE_RESPONSE_DTO);

        mockMvc.perform(
                        MockMvcRequestBuilders.get(
                                CurriculumVitaeTestData.CVS_UUID_URL_TEMPLATE,
                                CurriculumVitaeTestData.CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("uuid").value(CURRICULUM_VITAE_RESPONSE_DTO.uuid()))
                .andExpect(jsonPath("imageUuid").value(CURRICULUM_VITAE_RESPONSE_DTO.imageUuid()))
                .andExpect(jsonPath("name").value(CURRICULUM_VITAE_RESPONSE_DTO.name()))
                .andExpect(jsonPath("surname").value(CURRICULUM_VITAE_RESPONSE_DTO.surname()))
                .andExpect(jsonPath("positionId").value(CURRICULUM_VITAE_RESPONSE_DTO.positionId()))
                .andExpect(jsonPath("position").value(CURRICULUM_VITAE_RESPONSE_DTO.position()))
                .andExpect(jsonPath("countryId").value(CURRICULUM_VITAE_RESPONSE_DTO.countryId()))
                .andExpect(jsonPath("country").value(CURRICULUM_VITAE_RESPONSE_DTO.country()))
                .andExpect(jsonPath("city").value(CURRICULUM_VITAE_RESPONSE_DTO.city()))
                .andExpect(jsonPath("isReadyToRelocate").value(CURRICULUM_VITAE_RESPONSE_DTO.isReadyToRelocate()))
                .andExpect(jsonPath("isReadyForRemoteWork").value(CURRICULUM_VITAE_RESPONSE_DTO.isReadyForRemoteWork()))
                .andExpect(jsonPath("isContactsExists").value(CURRICULUM_VITAE_RESPONSE_DTO.isContactsExists()))
                .andExpect(jsonPath("isAboutExists").value(CURRICULUM_VITAE_RESPONSE_DTO.isAboutExists()))
                .andExpect(jsonPath("status").value(CURRICULUM_VITAE_RESPONSE_DTO.status()));

        verify(curriculumVitaeService, times(1)).getCvOfUser(CurriculumVitaeTestData.CV_UUID);
    }

    @Test
    void shouldReturn404WhenGetPersonalInfoSectionIfCvUuidIsNotExist() throws Exception {

        when(curriculumVitaeService.isCurriculumVitaeExists(CurriculumVitaeTestData.CV_UUID)).thenReturn(false);

        mockMvc.perform(
                        MockMvcRequestBuilders.get(
                                CurriculumVitaeTestData.CVS_UUID_URL_TEMPLATE,
                                CurriculumVitaeTestData.CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200WhenGetAllCvsOfUserIfAtLeastOneCvExist() throws Exception {

        when(curriculumVitaeService.getAllCvOfUser()).thenReturn(List.of(CURRICULUM_VITAE_RESPONSE_DTO));

        mockMvc.perform(
                        MockMvcRequestBuilders.get(
                                CurriculumVitaeTestData.CVS_URL_TEMPLATE
                        ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnListOfCvResponseDtoWhenGetAllCvsOfUserIfAtLeastOneCvExistAndCallBusinessLogic() throws Exception {

        when(curriculumVitaeService.getAllCvOfUser()).thenReturn(List.of(CURRICULUM_VITAE_RESPONSE_DTO));

        mockMvc.perform(
                        MockMvcRequestBuilders.get(
                                CurriculumVitaeTestData.CVS_URL_TEMPLATE
                        ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[0].uuid").value(CURRICULUM_VITAE_RESPONSE_DTO.uuid()))
                .andExpect(jsonPath("$[0].imageUuid").value(CURRICULUM_VITAE_RESPONSE_DTO.imageUuid()))
                .andExpect(jsonPath("$[0].name").value(CURRICULUM_VITAE_RESPONSE_DTO.name()))
                .andExpect(jsonPath("$[0].surname").value(CURRICULUM_VITAE_RESPONSE_DTO.surname()))
                .andExpect(jsonPath("$[0].positionId").value(CURRICULUM_VITAE_RESPONSE_DTO.positionId()))
                .andExpect(jsonPath("$[0].position").value(CURRICULUM_VITAE_RESPONSE_DTO.position()))
                .andExpect(jsonPath("$[0].countryId").value(CURRICULUM_VITAE_RESPONSE_DTO.countryId()))
                .andExpect(jsonPath("$[0].country").value(CURRICULUM_VITAE_RESPONSE_DTO.country()))
                .andExpect(jsonPath("$[0].city").value(CURRICULUM_VITAE_RESPONSE_DTO.city()))
                .andExpect(jsonPath("$[0].isReadyToRelocate").value(CURRICULUM_VITAE_RESPONSE_DTO.isReadyToRelocate()))
                .andExpect(jsonPath("$[0].isReadyForRemoteWork").value(CURRICULUM_VITAE_RESPONSE_DTO.isReadyForRemoteWork()))
                .andExpect(jsonPath("$[0].isContactsExists").value(CURRICULUM_VITAE_RESPONSE_DTO.isContactsExists()))
                .andExpect(jsonPath("$[0].isAboutExists").value(CURRICULUM_VITAE_RESPONSE_DTO.isAboutExists()))
                .andExpect(jsonPath("$[0].status").value(CURRICULUM_VITAE_RESPONSE_DTO.status()));

        verify(curriculumVitaeService, times(1)).getAllCvOfUser();
        verify(authService, times(1)).getUsername();
    }

    @Test
    void shouldReturn204WhenGetAllCvsOfUserIfDontHaveCv() throws Exception {

        when(curriculumVitaeService.getAllCvOfUser()).thenReturn(List.of());

        mockMvc.perform(
                        MockMvcRequestBuilders.get(
                                CurriculumVitaeTestData.CVS_URL_TEMPLATE
                        ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn400WhenPutPersonalInfoSectionIfImageUuidNotBelongsToUser() throws Exception {

        when(imageValidationService.isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(false);
        when(countryService.isCountryExist(CurriculumVitaeTestData.COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(CurriculumVitaeTestData.POSITION_ID)).thenReturn(true);

        mockMvc.perform(
                        MockMvcRequestBuilders.put(
                                CurriculumVitaeTestData.CVS_UUID_URL_TEMPLATE,
                                CurriculumVitaeTestData.CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("imageUuid").value("Image UUID is not valid!"));

        verify(imageValidationService, times(1)).isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID);
    }

    @Test
    void shouldReturn400WhenPutPersonalInfoSectionIfImageUuidIsUsedInPersonalDetails() throws Exception {

        when(imageValidationService.isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(false);
        when(countryService.isCountryExist(CurriculumVitaeTestData.COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(CurriculumVitaeTestData.POSITION_ID)).thenReturn(true);


        mockMvc.perform(
                        MockMvcRequestBuilders.put(
                                CurriculumVitaeTestData.CVS_UUID_URL_TEMPLATE,
                                CurriculumVitaeTestData.CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("imageUuid").value("Image UUID is not valid!"));

        verify(imageValidationService, times(1)).isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID);
        verify(imageValidationService, times(1)).validateImageForCv(CurriculumVitaeTestData.IMAGE_UUID);
    }

    @Test
    void shouldReturn400WhenPutPersonalInfoSectionIfCountryIdIsNotExist() throws Exception {

        when(imageValidationService.isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(CurriculumVitaeTestData.COUNTRY_ID)).thenReturn(false);
        when(positionService.isPositionExist(CurriculumVitaeTestData.POSITION_ID)).thenReturn(true);

        mockMvc.perform(
                        MockMvcRequestBuilders.put(
                                CurriculumVitaeTestData.CVS_UUID_URL_TEMPLATE,
                                CurriculumVitaeTestData.CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("countryId").value("Invalid id: country not found"));

        verify(countryService, times(1)).isCountryExist(CurriculumVitaeTestData.COUNTRY_ID);
    }

    @Test
    void shouldReturn400WhenPutPersonalInfoSectionIfPositionIdIsNotExist() throws Exception {

        when(imageValidationService.isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(CurriculumVitaeTestData.COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(CurriculumVitaeTestData.POSITION_ID)).thenReturn(false);

        mockMvc.perform(
                        MockMvcRequestBuilders.put(
                                CurriculumVitaeTestData.CVS_UUID_URL_TEMPLATE,
                                CurriculumVitaeTestData.CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("positionId").value("Invalid id: position not found"));

        verify(positionService, times(1)).isPositionExist(CurriculumVitaeTestData.POSITION_ID);
    }

    @Test
    void shouldReturn404WhenPutPersonalInfoSectionIfCvUuidIsNotExist() throws Exception {

        when(imageValidationService.isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(CurriculumVitaeTestData.COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(CurriculumVitaeTestData.POSITION_ID)).thenReturn(true);
        when(curriculumVitaeService.isCurriculumVitaeExists(CurriculumVitaeTestData.CV_UUID)).thenReturn(false);

        mockMvc.perform(
                        MockMvcRequestBuilders.put(
                                CurriculumVitaeTestData.CVS_UUID_URL_TEMPLATE,
                                CurriculumVitaeTestData.CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isNotFound())
                .andExpect(jsonPath("statusCode").value(404))
                .andExpect(jsonPath("message").value(String.format("CV with UUID %s not found!!!", CurriculumVitaeTestData.CV_UUID)))
                .andExpect(jsonPath("timeStamp").exists());

        verify(curriculumVitaeService, times(1)).isCurriculumVitaeExists(CurriculumVitaeTestData.CV_UUID);
    }

    @Test
    void shouldReturn200WhenUpdateValidInputPersonalInfoSection() throws Exception {

        when(imageValidationService.isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(CurriculumVitaeTestData.COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(CurriculumVitaeTestData.POSITION_ID)).thenReturn(true);
        when(curriculumVitaeService.isCurriculumVitaeExists(CurriculumVitaeTestData.CV_UUID)).thenReturn(true);

        mockMvc.perform(
                MockMvcRequestBuilders.put(
                        CurriculumVitaeTestData.CVS_UUID_URL_TEMPLATE,
                        CurriculumVitaeTestData.CV_UUID
                ).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
        ).andExpect(status().isOk());
    }

    @Test
    void shouldReturnCvResponseDtoWhenUpdateValidInputPersonalInfoSection() throws Exception {

        when(imageValidationService.isImageBelongsToUser(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(CurriculumVitaeTestData.IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(CurriculumVitaeTestData.COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(CurriculumVitaeTestData.POSITION_ID)).thenReturn(true);
        when(curriculumVitaeService.isCurriculumVitaeExists(CurriculumVitaeTestData.CV_UUID)).thenReturn(true);
        when(curriculumVitaeService.update(CurriculumVitaeTestData.CV_UUID, CURRICULUM_VITAE_REQUEST_DTO)).thenReturn(CURRICULUM_VITAE_RESPONSE_DTO);

        mockMvc.perform(
                        MockMvcRequestBuilders.put(
                                CurriculumVitaeTestData.CVS_UUID_URL_TEMPLATE,
                                CurriculumVitaeTestData.CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("uuid").value(CURRICULUM_VITAE_RESPONSE_DTO.uuid()))
                .andExpect(jsonPath("imageUuid").value(CURRICULUM_VITAE_RESPONSE_DTO.imageUuid()))
                .andExpect(jsonPath("name").value(CURRICULUM_VITAE_RESPONSE_DTO.name()))
                .andExpect(jsonPath("surname").value(CURRICULUM_VITAE_RESPONSE_DTO.surname()))
                .andExpect(jsonPath("positionId").value(CURRICULUM_VITAE_RESPONSE_DTO.positionId()))
                .andExpect(jsonPath("position").value(CURRICULUM_VITAE_RESPONSE_DTO.position()))
                .andExpect(jsonPath("countryId").value(CURRICULUM_VITAE_RESPONSE_DTO.countryId()))
                .andExpect(jsonPath("country").value(CURRICULUM_VITAE_RESPONSE_DTO.country()))
                .andExpect(jsonPath("city").value(CURRICULUM_VITAE_RESPONSE_DTO.city()))
                .andExpect(jsonPath("isReadyToRelocate").value(CURRICULUM_VITAE_RESPONSE_DTO.isReadyToRelocate()))
                .andExpect(jsonPath("isReadyForRemoteWork").value(CURRICULUM_VITAE_RESPONSE_DTO.isReadyForRemoteWork()))
                .andExpect(jsonPath("isContactsExists").value(CURRICULUM_VITAE_RESPONSE_DTO.isContactsExists()))
                .andExpect(jsonPath("isAboutExists").value(CURRICULUM_VITAE_RESPONSE_DTO.isAboutExists()))
                .andExpect(jsonPath("status").value(CURRICULUM_VITAE_RESPONSE_DTO.status()));

        verify(curriculumVitaeService, times(1)).update(CurriculumVitaeTestData.CV_UUID, CURRICULUM_VITAE_REQUEST_DTO);
    }
}