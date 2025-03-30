package br.com.washington.intuitive_care_test.services.impl;

import br.com.washington.intuitive_care_test.services.TransformDataService;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

@Log4j2
@RequiredArgsConstructor
@Service
public class TransformDataServiceImpl implements TransformDataService {

    private final Path path;

    @Override
    public void pdfToCsv(Path currentPath) {
        File[] getFiles = currentPath.toFile().listFiles();
        if (getFiles == null) throw new RuntimeException("No files found");
        Stream.of(getFiles).map(File::getAbsoluteFile).forEach(setFiles -> {
            CompletableFuture<String> stringCompletableFuture = convertPDFToCSV(setFiles, currentPath);
            stringCompletableFuture.join();
        });
        Stream.of(getFiles).filter(file -> file.getName().endsWith(".pdf")).forEach(File::delete);
    }

    private CompletableFuture<String> convertPDFToCSV(File file, Path currentPath) {
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        return CompletableFuture.supplyAsync(() -> {
            String csvFilePath = file.getName().replace(".pdf", ".csv");

            try (InputStream in = Files.newInputStream(path.resolve(file.getAbsolutePath()));
                 PDDocument document = PDDocument.load(in);
                 CSVWriter csvWriter = new CSVWriter(new FileWriter(currentPath.resolve(csvFilePath).toFile()))
            ) {


                SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
                PageIterator pi = new ObjectExtractor(document).extract();

                while (pi.hasNext()) {
                    Page page = pi.next();
                    List<Table> tables = sea.extract(page);

                    for (Table table : tables) {
                        List<List<RectangularTextContainer>> rows = table.getRows();

                        for (List<RectangularTextContainer> cells : rows) {

                            String[] rowArray = cells.stream()
                                    .map(cell -> cell.getText()
                                            .replace("OD", "Odontológica".toUpperCase())
                                            .replace("AMB", "Ambulatorial".toUpperCase())
                                            .replace("\r", " "))
                                    .toArray(String[]::new);


                            csvWriter.writeNext(rowArray);
                        }
                    }
                }

                log.info("CSV file created: {}", csvFilePath);

            } catch (IOException e) {
                log.error(e.getMessage());
            }
            return csvFilePath;
        }, executorService);
    }
}
