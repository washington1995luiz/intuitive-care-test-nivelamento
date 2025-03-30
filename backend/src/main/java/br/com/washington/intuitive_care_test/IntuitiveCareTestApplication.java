package br.com.washington.intuitive_care_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class IntuitiveCareTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntuitiveCareTestApplication.class, args);
    }

}
