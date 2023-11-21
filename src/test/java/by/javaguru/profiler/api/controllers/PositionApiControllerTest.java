package by.javaguru.profiler.api.controllers;

import by.javaguru.profiler.usecasses.PositionService;
import by.javaguru.profiler.usecasses.dto.PositionDto;
import by.javaguru.profiler.util.PositionTestData;
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

@WebMvcTest(controllers = PositionApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class PositionApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PositionService positionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn200WhenGettingPositionsList() throws Exception {
        List<PositionDto> positionsResponseDtos = List.of(PositionTestData.createPositionDto().build());

        when(positionService.getPositions()).thenReturn(positionsResponseDtos);

        mockMvc.perform(MockMvcRequestBuilders.get(PositionTestData.POSITION_URL_TEMPLATE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnExpectedPositionResponse() throws Exception {
        PositionDto positionDtoPython = PositionTestData.createPositionDto().withId(3L).withName("Python developer").build();
        List<PositionDto> positionsResponseDtos = List.of(PositionTestData.createPositionDto().build(), positionDtoPython);
        PositionDto positionDto = positionsResponseDtos.get(0);

        when(positionService.getPositions()).thenReturn(positionsResponseDtos);

        mockMvc.perform(MockMvcRequestBuilders.get(PositionTestData.POSITION_URL_TEMPLATE))
                .andDo(print())
                .andExpect(jsonPath("$[0].name").value(positionDto.name()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnExpectedJsonResponseForPositionList() throws Exception {
        PositionDto positionDtoPython = PositionTestData.createPositionDto().withId(3L).withName("Python developer").build();
        List<PositionDto> positionsResponseDtos = List.of(PositionTestData.createPositionDto().build(), positionDtoPython);
        String expectedJson = objectMapper.writeValueAsString(positionsResponseDtos);

        when(positionService.getPositions()).thenReturn(positionsResponseDtos);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(PositionTestData.POSITION_URL_TEMPLATE))
                .andDo(print())
                .andReturn();

        String resultJson = mvcResult.getResponse().getContentAsString();
        assertEquals(resultJson, expectedJson);
    }

    @Test
    void shouldInvokePositionServiceWhenGettingPositionList() throws Exception {
        PositionDto positionDtoPython = PositionTestData.createPositionDto().withId(3L).withName("Python developer").build();
        List<PositionDto> positionsResponseDtos = List.of(PositionTestData.createPositionDto().build(), positionDtoPython);

        when(positionService.getPositions()).thenReturn(positionsResponseDtos);

        mockMvc.perform(MockMvcRequestBuilders.get(PositionTestData.POSITION_URL_TEMPLATE));
        verify(positionService, times(1)).getPositions();
    }
}
