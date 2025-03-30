package br.com.washington.intuitive_care_test.util;

import java.time.LocalDate;

public class ConvertDate {

    public static LocalDate convert(String date) {
        date = date.replace("\"", "");
        date = date.replace("'", "");
        if(date.contains("/")){
            String[] split = date.split("/");
             return LocalDate.of(Integer.parseInt(split[2]), Integer.parseInt(split[1]), Integer.parseInt(split[0]));
        }
        return LocalDate.parse(date.trim());
    }
}
