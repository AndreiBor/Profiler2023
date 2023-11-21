package by.javaguru.profiler.util;

import by.javaguru.profiler.usecasses.dto.AboutDto;

public final class AboutTestData {

    public static final String ABOUTS_URL_TEMPLATE = String.format("/api/v1/cvs/%s/about", CurriculumVitaeTestData.CV_UUID);

    private AboutTestData(){
    }

    public static AboutDto.AboutDtoBuilder createAboutDto(){
        return AboutDto.builder()
                .withDescription("Hi everyone! My name is Kate. I'm a beginner UX")
                .withSelfPresentation("https://docs.google.com/rndm");
    }
}
