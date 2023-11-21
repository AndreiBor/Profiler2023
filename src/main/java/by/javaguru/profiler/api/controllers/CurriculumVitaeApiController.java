package by.javaguru.profiler.api.controllers;

import by.javaguru.profiler.api.exception.BadRequestException;
import by.javaguru.profiler.usecasses.CurriculumVitaeService;
import by.javaguru.profiler.usecasses.annotation.IsCvExists;
import by.javaguru.profiler.usecasses.dto.CurriculumVitaeRequestDto;
import by.javaguru.profiler.usecasses.dto.CurriculumVitaeResponseDto;
import by.javaguru.profiler.usecasses.util.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "CurriculumVitae Controller", description = "API for working with CV's")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cvs")
@Slf4j
@Validated
public class CurriculumVitaeApiController {

    private final CurriculumVitaeService curriculumVitaeService;

    private final AuthService authService;

    @PostMapping
    @Operation(summary = "Save CurriculumVitae")
    public ResponseEntity<CurriculumVitaeResponseDto> save(@RequestBody @Valid CurriculumVitaeRequestDto curriculumVitaeRequestDto) {
        if (curriculumVitaeService.isCreationCvAvailable()) {
            log.debug("Input data for creating CV: {} ", curriculumVitaeRequestDto);
            CurriculumVitaeResponseDto responseResume = curriculumVitaeService.save(curriculumVitaeRequestDto);
            return new ResponseEntity<>(responseResume, HttpStatus.CREATED);
        }
        throw new BadRequestException("Curriculum vitae creation limit exceeded");
    }

    @GetMapping
    @Operation(summary = "Get all CurriculumVitae of user")
    public ResponseEntity<List<CurriculumVitaeResponseDto>> getAllCvOfUser() {
        List<CurriculumVitaeResponseDto> curriculumVitaeList = curriculumVitaeService.getAllCvOfUser();
        String username = authService.getUsername();
        if (curriculumVitaeList.isEmpty()) {
            log.warn("User {} has no CV yet", username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.debug("Getting {} CV of user {} from database ", curriculumVitaeList.size(), username);
        return new ResponseEntity<>(curriculumVitaeList, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get CurriculumVitae", description = "Get CurriculumVitae by uuid")
    public ResponseEntity<CurriculumVitaeResponseDto> getCvOfUser(@PathVariable(name = "uuid") @IsCvExists String uuid) {
        log.debug("Input UUID of CV: {} ", uuid);
        CurriculumVitaeResponseDto curriculumVitae = curriculumVitaeService.getCvOfUser(uuid);
        log.debug("Getting CV from database: {} ", curriculumVitae);
        return new ResponseEntity<>(curriculumVitae, HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Update CurriculumVitae", description = "Update CurriculumVitae by UUID")
    public ResponseEntity<CurriculumVitaeResponseDto> update(@RequestBody @Valid CurriculumVitaeRequestDto curriculumVitaeRequestDto,
                                                             @PathVariable(name = "uuid") @IsCvExists String uuid) {
        log.debug("Updating CV with UUID {} by the data: {} ", uuid, curriculumVitaeRequestDto);
        CurriculumVitaeResponseDto responseCurriculumVitae = curriculumVitaeService.update(uuid, curriculumVitaeRequestDto);
        return new ResponseEntity<>(responseCurriculumVitae, HttpStatus.OK);
    }
}
