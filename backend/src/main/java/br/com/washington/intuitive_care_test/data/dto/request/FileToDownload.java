package br.com.washington.intuitive_care_test.data.dto.request;

import java.util.Arrays;
import java.util.function.Function;

public record FileToDownload(String name, String url) {

    public FileToDownload {
        String type = Arrays.stream(url.split("\\.")).toList().getLast();
        name = formatText(type).apply(name);
    }
    private static Function<String, String> formatText (String type){
        return text -> {
            text.replaceAll( "^[\\p{L}\\s]+$","");
            return text.endsWith(".".concat(type)) ? text : text.concat(".".concat(type));
        };
    }

}
