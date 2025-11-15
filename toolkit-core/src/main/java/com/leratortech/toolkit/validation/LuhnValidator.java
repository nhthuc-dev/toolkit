package com.leratortech.toolkit.validation;

/**
 * Validates numbers using the Luhn algorithm (credit card, payment numbers).
 */
public final class LuhnValidator {
    private LuhnValidator() {}

    public static boolean isValid(String number) {
        if (number == null || !number.matches("\\d+")) return false;
        int sum = 0;
        boolean alternate = false;
        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(number.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            sum += n;
            alternate = !alternate;
        }
        return sum % 10 == 0;
    }
}
