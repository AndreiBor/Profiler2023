package by.javaguru.profiler.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.javaguru.profiler.persistence.model.Skill;
import by.javaguru.profiler.usecasses.dto.SkillResponseDto;

public final class SkillTestData {
    
    private SkillTestData() {}
    
    public static Skill createSkill() {
        return Skill.builder().withId(1L)
                .withName(".NET")
                .withPositions(new ArrayList<>())
                .build();
    }

    public static SkillResponseDto.SkillResponseDtoBuilder createSkillResponseDto(){
        return SkillResponseDto.builder()
                .withId(1L)
                .withName("Java Core");
    }

    public static String getJsonSkillsResponseDto(){
        return "[{\"id\":1,\"name\":\"Java Core\"}]";
    }

    public static List<SkillResponseDto> createSkillResponseDtoList() {
        return Arrays.asList(
                SkillResponseDto.builder()
                        .withId(1L)
                        .withName(".NET")
                        .build(),
                SkillResponseDto.builder()
                        .withId(2L)
                        .withName("Java Core")
                        .build(),
                SkillResponseDto.builder()
                        .withId(3L)
                        .withName("JavaScript")
                        .build()
        );
    }
}
