package br.com.washington.intuitive_care_test.util;

import java.math.BigDecimal;

public class FormatMonetaryNumber {

    public static BigDecimal format(String number){
        number = number.replace(",", ".");
        number = number.replace("\"", "");
        return new BigDecimal(number.trim());
    }
}
