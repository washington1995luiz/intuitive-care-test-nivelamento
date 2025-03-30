package br.com.washington.intuitive_care_test.services.impl;

import br.com.washington.intuitive_care_test.data.dto.request.FileToDownload;
import br.com.washington.intuitive_care_test.services.JdbcService;
import br.com.washington.intuitive_care_test.services.ZipService;
import br.com.washington.intuitive_care_test.util.ConvertDate;
import br.com.washington.intuitive_care_test.util.FormatMonetaryNumber;
import br.com.washington.intuitive_care_test.util.FormatString;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

@Log4j2
@RequiredArgsConstructor
@Service
public class ZipServiceImpl implements ZipService {

    private final Path path;
    @Value("${test-three.site}")
    private String webSite;

    @Value("${test-three.sql.tb-financial-statements}")
    private String sqlTbFinancialStatements;

    private final JdbcService jdbcService;

    private static final int BATCH_SIZE = 20_000;
    private static final String FINANCIAL_STATEMENT = "financial";
    private static final String REPORT = "report";


    private final Function<Element, FileToDownload> extractDatas =
            extract -> new FileToDownload(extract.text(), extract.attribute("href").getValue());


    @Override
    public void zipFiles(Path currentDirectory, String zipName) {
        String sourceDir = currentDirectory.toAbsolutePath().toString();

        try (FileOutputStream fos = new FileOutputStream(path.resolve(zipName).toFile());
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            File rootDir = new File(sourceDir);

            zipDirectoryContents(rootDir, zos);


        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void zipDirectoryContents(File folder, ZipOutputStream zos) throws IOException {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                String entryName = file.getName();

                FileInputStream fis = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(entryName);
                zos.putNextEntry(zipEntry);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                fis.close();

            }
        }
    }

    public void processZipFiles(File[] zipFilePaths) {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        for (File zipFilePath : zipFilePaths) {
            executor.submit(() -> {
                try {
                    processZipFile(zipFilePath);
                } catch (Exception e) {
                    log.error("Error trying to process ZIP: " + zipFilePath);
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void processZipFile(File zipFilePath) {
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".csv")) {
                    log.info("📂 Processing file: " + entry.getName());
                    try (InputStream is = zipFile.getInputStream(entry);
                         BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                        processCsv(br, FINANCIAL_STATEMENT, sqlTbFinancialStatements);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processCsv(BufferedReader br, String chooseEntity, String sql) {
        List<Object[]> batchArgs = new ArrayList<>();
        String line;
        try {

            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");

                if (values.length < 6) {
                    log.error("⚠ Line ignored: {}", Arrays.toString(values));
                    continue;
                }

                try {
                    batchArgs.add(choose(chooseEntity, values));
                } catch (NumberFormatException e) {
                    log.error("⚠ Error when trying to convert number: {}", Arrays.toString(values));
                }

                if (batchArgs.size() >= BATCH_SIZE) {
                    jdbcService.updateBatch(batchArgs, sql);
                    batchArgs.clear();
                }
            }

            if (!batchArgs.isEmpty()) {
                jdbcService.updateBatch(batchArgs, sql);
            }
            log.info("✅ Import completed!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Object[] financialBatch(String[] values) {
        return new Object[]{
                FormatString.format(values[1]),
                FormatString.format(values[2]),
                FormatString.format(values[3]),
                FormatMonetaryNumber.format(values[4]),
                FormatMonetaryNumber.format(values[5]),
                ConvertDate.convert(values[0])
        };
    }

    private Object[] reportBatch(String[] values) {
        return new Object[]{
                FormatString.format(values[0]), //registro_ans
                FormatString.format(values[1]), //cnpj
                FormatString.format(values[2]), //razao_social
                FormatString.format(values[3]), //nome_fantasia
                FormatString.format(values[4]), //modalidade
                FormatString.format(values[5]), //logradouro
                FormatString.format(values[6]), //numero
                FormatString.format(values[7]), //complemento
                FormatString.format(values[8]), //bairro
                FormatString.format(values[9]), //cidade
                FormatString.format(values[10]), //uf
                FormatString.format(values[11]), //cep
                FormatString.format(values[12]), //ddd
                FormatString.format(values[13]), //telefone
                FormatString.format(values[14]), //fax
                FormatString.format(values[15]), //endereco_eletronico
                FormatString.format(values[16]), //representante
                FormatString.format(values[17]), //cargo_representante
                FormatString.format(values[18]), //regiao_de_comercializacao
                ConvertDate.convert(values[19]) //data_registro_ans
        };
    }

    private Object[] choose(String chooseEntity, String[] values) {
        if (chooseEntity.equals(FINANCIAL_STATEMENT)) {
            return financialBatch(values);
        }
        if (chooseEntity.equals(REPORT)) {
            return reportBatch(values);
        }
        return null;
    }
}
