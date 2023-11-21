package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.Skill;
import by.javaguru.profiler.usecasses.dto.SkillResponseDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static by.javaguru.profiler.util.SkillTestData.createSkill;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SkillMapperTest {

    private final SkillMapper skillMapper = Mappers.getMapper(SkillMapper.class);

    @Test
    void shouldMapCorrectlyAllFieldWhenInvokeFromEntityToDto() {
        Skill skill = createSkill();
        SkillResponseDto skillResponseDto = skillMapper.fromEntityToDto(skill);
        assertEquals(skill.getName(),skillResponseDto.name());
        assertEquals(skill.getId(),skillResponseDto.id());
    }



}
