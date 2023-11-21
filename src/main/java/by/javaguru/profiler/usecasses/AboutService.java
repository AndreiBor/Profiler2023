package by.javaguru.profiler.usecasses;

import by.javaguru.profiler.usecasses.dto.AboutDto;

public interface AboutService {

    AboutDto save(String uuid, AboutDto aboutDto);

    AboutDto update(String uuid, AboutDto aboutDto);

    AboutDto getAbout(String uuid);
}
