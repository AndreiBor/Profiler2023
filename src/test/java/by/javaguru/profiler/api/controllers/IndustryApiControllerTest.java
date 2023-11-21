package by.javaguru.profiler.api.controllers;

import by.javaguru.profiler.usecasses.IndustryService;
import by.javaguru.profiler.usecasses.dto.IndustryResponseDto;
import by.javaguru.profiler.util.IndustryTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = IndustryApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class IndustryApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IndustryService industryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn200WhenGettingIndustriesList() throws Exception {
        List<IndustryResponseDto> industryResponseDtos = List.of(IndustryTestData.createIndustryResponseDto().build());

        when(industryService.getIndustries()).thenReturn(industryResponseDtos);

        mockMvc.perform(MockMvcRequestBuilders.get(IndustryTestData.INDUSTRY_URL_TEMPLATE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnExpectedIndustryResponse() throws Exception {
        IndustryResponseDto sportResponseDto = IndustryTestData.createIndustryResponseDto().withName("Sport").build();
        List<IndustryResponseDto> industryResponseDtos = List.of(IndustryTestData.createIndustryResponseDto().build(), sportResponseDto);
        IndustryResponseDto industryResponseDto = industryResponseDtos.get(0);

        when(industryService.getIndustries()).thenReturn(industryResponseDtos);

        mockMvc.perform(MockMvcRequestBuilders.get(IndustryTestData.INDUSTRY_URL_TEMPLATE))
                .andDo(print())
                .andExpect(jsonPath("$[0].name").value(industryResponseDto.name()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnExpectedJsonResponseForIndustryList() throws Exception {
        IndustryResponseDto sportResponseDto = IndustryTestData.createIndustryResponseDto().withName("Sport").build();
        List<IndustryResponseDto> industryResponseDtos = List.of(IndustryTestData.createIndustryResponseDto().build(), sportResponseDto);
        String expectedJson = objectMapper.writeValueAsString(industryResponseDtos);

        when(industryService.getIndustries()).thenReturn(industryResponseDtos);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(IndustryTestData.INDUSTRY_URL_TEMPLATE))
                .andDo(print())
                .andReturn();

        String resultJson = mvcResult.getResponse().getContentAsString();
        assertEquals(resultJson, expectedJson);
    }

    @Test
    void shouldInvokeLanguageServiceWhenGettingIndustryList() throws Exception {
        IndustryResponseDto sportResponseDto = IndustryTestData.createIndustryResponseDto().withName("Sport").build();
        List<IndustryResponseDto> industryResponseDtos = List.of(IndustryTestData.createIndustryResponseDto().build(), sportResponseDto);

        when(industryService.getIndustries()).thenReturn(industryResponseDtos);

        mockMvc.perform(MockMvcRequestBuilders.get(IndustryTestData.INDUSTRY_URL_TEMPLATE));
        verify(industryService, times(1)).getIndustries();
    }

}
