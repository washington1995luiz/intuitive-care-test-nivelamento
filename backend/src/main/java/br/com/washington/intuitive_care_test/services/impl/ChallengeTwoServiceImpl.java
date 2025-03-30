package br.com.washington.intuitive_care_test.services.impl;

import br.com.washington.intuitive_care_test.data.dto.request.RequestFilesToScraping;
import br.com.washington.intuitive_care_test.services.ChallengeTwoService;
import br.com.washington.intuitive_care_test.services.TransformDataService;
import br.com.washington.intuitive_care_test.services.WebScrapingService;
import br.com.washington.intuitive_care_test.services.ZipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class ChallengeTwoServiceImpl implements ChallengeTwoService {

    private final Path path;
    private final WebScrapingService scrapingService;
    private final TransformDataService transformDataService;
    private final ZipService zipService;

    @Override
    public String transform(RequestFilesToScraping filesToScraping) {
        Path currentDirectory = scrapingService.webScrapingFiles(filesToScraping);
        transformDataService.pdfToCsv(currentDirectory);
        File[] files = currentDirectory.toFile().listFiles();
        zipService.zipFiles(currentDirectory, filesToScraping.zipName());
        if(files == null) throw new RuntimeException("No files found");
        Stream.of(files).forEach(File::delete);
        currentDirectory.toFile().delete();
        return path.resolve(filesToScraping.zipName()).toFile().getAbsolutePath();
    }

}
