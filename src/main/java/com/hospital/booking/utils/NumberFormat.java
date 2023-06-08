package com.hospital.booking.utils;

import java.util.Formatter;
import java.util.Locale;

public class NumberFormat {
    public static String formatNumber(double number) {
        try {
            StringBuilder sb = new StringBuilder();
            Formatter formatter = new Formatter(sb, Locale.US);
            formatter.format("%,.2f", number);
            return sb.toString();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
