package br.com.washington.intuitive_care_test.services.impl;

import br.com.washington.intuitive_care_test.data.dto.request.FileToDownload;
import br.com.washington.intuitive_care_test.services.DownloadFilesService;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DownloadFilesServiceImpl implements DownloadFilesService {

    @Override
    public CompletableFuture<Path> download(FileToDownload fileToDownload, Path currentDirectory) {
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        return CompletableFuture.supplyAsync(() -> {
            Path targetLocation = currentDirectory.resolve(fileToDownload.name());
            InputStream inputStream = null;
            try {
                inputStream = URI.create(fileToDownload.url()).toURL().openStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
            try (FileOutputStream fileOutputStream = new FileOutputStream(targetLocation.toFile());
                 FileChannel fileChannel = fileOutputStream.getChannel()) {
                fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return targetLocation;
        }, executorService);
    }
}
