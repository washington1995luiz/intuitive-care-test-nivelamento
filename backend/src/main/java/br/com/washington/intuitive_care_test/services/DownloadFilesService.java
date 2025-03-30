package br.com.washington.intuitive_care_test.services;

import br.com.washington.intuitive_care_test.data.dto.request.FileToDownload;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public interface DownloadFilesService {

    CompletableFuture<Path> download(FileToDownload fileToDownload, Path currentDirectory);
}
