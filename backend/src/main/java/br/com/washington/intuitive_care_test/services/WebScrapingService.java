package br.com.washington.intuitive_care_test.services;

import br.com.washington.intuitive_care_test.data.dto.request.FileToDownload;
import br.com.washington.intuitive_care_test.data.dto.request.RequestFilesToScraping;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public interface WebScrapingService {

    Path webScrapingFiles(RequestFilesToScraping filesToScraping);

    CompletableFuture<Path> financialStatementsWebScraping(String year);

    CompletableFuture<Path> downloadFiles(FileToDownload fileToDownload, Path currentDirectory);
}
