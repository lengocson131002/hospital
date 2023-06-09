package com.hospital.booking.utils;

import com.hospital.booking.constants.DateTimeConstants;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatetimeUtils {
    public static LocalDate toDate(String dateString, String format) {
        if (dateString == null) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDate.parse(dateString, formatter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String toString(LocalDate date, String format) {
        if (date == null) {
            return "";
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return date.format(formatter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static LocalDateTime toDateTime(String dateString, String format) {
        if (dateString == null) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDateTime.parse(dateString, formatter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String toString(LocalDateTime date, String format) {
        if (date == null) {
            return "";
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return date.format(formatter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static List<LocalDate> getWeeklyDays(boolean fromTomorrow) {
        List<LocalDate> days = new ArrayList<>();

        LocalDate start = LocalDate.now();

        DayOfWeek currentDayOfWeek = start.getDayOfWeek();

        if (fromTomorrow) {
            start = start.plusDays(1);
        } else {
            start = start.minusDays(currentDayOfWeek.getValue() - DayOfWeek.MONDAY.getValue());
        }

        for (int i = 0; i <= (DayOfWeek.SUNDAY.getValue() - start.getDayOfWeek().getValue()); i++) {
            days.add(start.plusDays(i));
        }

        return days;
    }

    public static List<LocalDate> getWeeklyDays() {
        return getWeeklyDays(false);
    }


    public static void main(String[] args) {
        for (LocalDate time : DatetimeUtils.getWeeklyDays(false)) {
            System.out.println(DatetimeUtils.toString(time, DateTimeConstants.DATE_FORMAT));
        }
    }
}
