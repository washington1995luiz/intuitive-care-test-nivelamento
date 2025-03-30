package br.com.washington.intuitive_care_test.services;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Path;

public interface ZipService {

    void zipFiles(Path currentDirectory, String zipName);
    void processZipFiles(File[] zipFilePaths);
    void processCsv(BufferedReader br, String chooseEntity, String sql);
}
