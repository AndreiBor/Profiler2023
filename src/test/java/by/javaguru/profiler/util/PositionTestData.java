package by.javaguru.profiler.util;

import by.javaguru.profiler.persistence.model.Position;
import by.javaguru.profiler.usecasses.dto.PositionDto;

public class PositionTestData {

    public static final String POSITION_URL_TEMPLATE = "/api/v1/positions";
    public static final Integer EXPECTED_NUMBER_OF_POSITIONS = 34;

    private PositionTestData() {
    }

    public static PositionDto.PositionDtoBuilder createPositionDto() {
        return PositionDto.builder().withId(1L).withName("Java developer");
    }

    public static Position.PositionBuilder createPosition() {
        return Position.builder().withId(1L).withName("Java developer");
    }
}
