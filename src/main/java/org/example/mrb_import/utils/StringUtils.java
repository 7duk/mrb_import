package org.example.mrb_import.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class StringUtils {
    private static Set<String> generatedStrings = new HashSet<>();
    private static final String CHARACTERS = "0123456789abcde";
    private static final int LENGTH = 14;
    private static Random random = new Random();
    public static Integer ConvertToInt(String text) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (Character.isDigit(currentChar)) { // Kiểm tra nếu ký tự là số
                str.append(currentChar);
            } else {
                if (str.length() > 0) {
                    break;
                }
            }
        }
        return str.length() > 0 ? Integer.parseInt(str.toString()) : null;
    }

    public static Double ConvertToDouble(String text) {
        StringBuilder str = new StringBuilder();
        boolean decimalPointFound = false;

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            if (Character.isDigit(currentChar)) { // Check if the character is a digit
                str.append(currentChar);
            } else if (currentChar == '.' && !decimalPointFound) {
                str.append(currentChar);
                decimalPointFound = true;
            } else {
                if (str.length() > 0) {
                    break;
                }
            }
        }

        return str.length() > 0 ? Double.parseDouble(str.toString()) : null;
    }

    public static String generateUniqueRandomString() {
        String randomString;
        do {
            randomString = generateRandomString();
        } while (generatedStrings.contains(randomString));  // Ensure no duplicates

        generatedStrings.add(randomString);  // Add to set to track uniqueness
        return randomString;
    }

    // Helper method to generate a random string
    private static String generateRandomString() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }


}
