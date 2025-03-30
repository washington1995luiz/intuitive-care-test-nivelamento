package br.com.washington.intuitive_care_test.util;

public class FormatString {

    public static String format(String text){
        text = text.startsWith("\"") ? text.substring(1) : text;
        text = text.endsWith("\"") ? text.substring(0, text.length() - 1) : text;
        return text.trim();
    }
}
