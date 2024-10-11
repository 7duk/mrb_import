package org.example.mrb_import.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {
    public static String convertJapaneseDateToVietnameseFormat(String input) {
        try {
            // Tách năm và tháng từ chuỗi
            String[] parts = input.split("年|月|築");
            String yearStr = parts[0].trim();
            String monthStr =parts[1].trim();
            if(monthStr =="" || yearStr=="") return null;
            int year = Integer.parseInt(yearStr);
            int month = Integer.parseInt(monthStr);
            LocalDate date = LocalDate.of(year, month, 1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return date.format(formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date format error: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Number conversion error: " + e.getMessage(), e);
        }
    }
}
