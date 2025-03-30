package br.com.washington.intuitive_care_test.services.impl;

import br.com.washington.intuitive_care_test.data.dto.request.RequestFilesToScraping;
import br.com.washington.intuitive_care_test.services.ChallengeOneService;
import br.com.washington.intuitive_care_test.services.WebScrapingService;
import br.com.washington.intuitive_care_test.services.ZipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.stream.Stream;

@Log4j2
@RequiredArgsConstructor
@Service
public class ChallengeOneServiceImpl implements ChallengeOneService {

    private final Path path;
    private final WebScrapingService webScrapingService;
    private final ZipService zipService;

    @Override
    public String download(RequestFilesToScraping filesToScraping) {;
        Path currentDirectory = webScrapingService.webScrapingFiles(filesToScraping);
        zipService.zipFiles(currentDirectory, filesToScraping.zipName());
        File[] files = currentDirectory.toFile().listFiles(File::isFile);
        if (files != null){
            Stream.of(files).forEach(File::delete);
        }
        currentDirectory.toFile().delete();
        return path + "/" + filesToScraping.zipName();
    }

}
