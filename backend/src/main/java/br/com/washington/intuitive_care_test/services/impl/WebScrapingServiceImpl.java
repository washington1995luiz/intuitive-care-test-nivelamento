package br.com.washington.intuitive_care_test.services.impl;

import br.com.washington.intuitive_care_test.data.dto.request.FileToDownload;
import br.com.washington.intuitive_care_test.data.dto.request.RequestFilesToScraping;
import br.com.washington.intuitive_care_test.services.DownloadFilesService;
import br.com.washington.intuitive_care_test.services.WebScrapingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@RequiredArgsConstructor
@Service
public class WebScrapingServiceImpl implements WebScrapingService {

    private final Path path;

    private final DownloadFilesService downloadFiles;

    @Value("${websites.financial-statements}")
    private String financialStatementsWebSite;

    private final Function<Element, FileToDownload> extractDatas =
            extract -> new FileToDownload(extract.text(), extract.attr("href"));

    private final Function<String, String> formatText =
            text -> text.replaceAll( "^[\\p{L}\\s]+$","");


    @Override
    public Path webScrapingFiles(RequestFilesToScraping filesToScraping) {
        try {

            Path newDirectory = Path.of(path.toAbsolutePath().normalize() + "/" + UUID.randomUUID());
            Path currentDirectory = Files.createDirectories(newDirectory);
            Document website = Jsoup.connect(filesToScraping.siteToScraping()).get();

            // class "internal-link"
            Elements elementsByClass = website.getElementsByClass(filesToScraping.classToSelect());
            elementsByClass = elementsByClass.stream()
                    .filter(el -> el.attr("href").endsWith(filesToScraping.type()))
                    .map(el -> el.text(formatText.apply(el.text())))
                    .collect(Collectors.toCollection(Elements::new));
            elementsByClass = elementsByClass.stream()
                    .filter(el -> filesToScraping.filesName().contains(el.text()))
                    .collect(Collectors.toCollection(Elements::new));

            if(elementsByClass.isEmpty()){
                log.info("No web scraping files found");
            }
            Stream<CompletableFuture<Path>> completableFutureStream = elementsByClass.stream()
                    .map(element -> downloadFiles(extractDatas.apply(element), currentDirectory));

            completableFutureStream
                    .toList()
                    .stream()
                    .map(CompletableFuture::join)
                    .toList();

            return currentDirectory;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public CompletableFuture<Path> financialStatementsWebScraping(String year) {
    // web scraping site https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        return CompletableFuture.supplyAsync(() -> {
            try {
                String url = financialStatementsWebSite.concat(year).concat("/");
                Path newDirectory = Path.of(path.toAbsolutePath().normalize() + "/" + year + "-" + UUID.randomUUID());
                Path currentDirectory = Files.createDirectories(newDirectory);
                Document website = Jsoup.connect(url).get();

                Elements elementsByClass = website.getElementsByAttribute("href");
                elementsByClass = elementsByClass.stream()
                        .filter(el -> el.text().endsWith(".zip"))
                        .map(el -> el.attr("href", url.concat(el.text())))
                        .collect(Collectors.toCollection(Elements::new));


                if (elementsByClass.isEmpty()) {
                    log.info("No web scraping files found");
                }

                log.info(elementsByClass.getFirst().attributes());

                elementsByClass.forEach(element -> log.info(extractDatas.apply(element)));

                Stream<CompletableFuture<Path>> completableFutureStream = elementsByClass.stream()
                        .map(element -> downloadFiles.download(extractDatas.apply(element), currentDirectory));

                completableFutureStream
                        .toList()
                        .stream()
                        .map(CompletableFuture::join)
                        .toList();

                return currentDirectory;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }, executorService);

    }

    @Override
    public CompletableFuture<Path> downloadFiles(FileToDownload fileToDownload, Path currentDirectory) {
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
