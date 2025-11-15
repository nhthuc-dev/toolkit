package com.leratortech.toolkit.validation;

/**
 * Validates numeric strings.
 */
public final class NumberValidator {
    private NumberValidator() {}

    public static boolean isNumeric(String s) {
        if (s == null || s.isBlank()) return false;
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInteger(String s) {
        if (s == null || s.isBlank()) return false;
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isPositive(String s) {
        return isNumeric(s) && Double.parseDouble(s) > 0;
    }
}
