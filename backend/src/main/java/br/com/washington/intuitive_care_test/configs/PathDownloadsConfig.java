package br.com.washington.intuitive_care_test.configs;

import br.com.washington.intuitive_care_test.exception.CannotCreateDirectoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Configuration
public class PathDownloadsConfig implements CommandLineRunner {

    @Value("${path.downloads}")
    private String pathDownloads;

    @Bean
    Path path (){
        return Paths.get(pathDownloads).toAbsolutePath().normalize();
    }


    @Override
    public void run(String... args) throws Exception {
        Path path = Paths.get(pathDownloads).toAbsolutePath().normalize();
        try{
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new CannotCreateDirectoryException(e.getMessage());
        }
    }

}
