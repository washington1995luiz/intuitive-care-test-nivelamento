package br.com.washington.intuitive_care_test.data.dto.request;

import java.util.List;
import java.util.function.Function;

public record RequestFilesToScraping(
        List<String> filesName,
        String type,
        String siteToScraping,
        String classToSelect,
        String zipName
) {

    public RequestFilesToScraping {
        zipName = zipName.endsWith(".zip") ? zipName : zipName + ".zip";
        type = type.startsWith(".") ? type : ".".concat(type);
        filesName = filesName.stream().map(formatText).toList();
    }
    private static final Function<String, String> formatText =
            text -> {
                    text.replaceAll( "^[\\p{L}\\s]+$","");
                    return text.endsWith(".") ? text : text.concat(".");
            };
}
