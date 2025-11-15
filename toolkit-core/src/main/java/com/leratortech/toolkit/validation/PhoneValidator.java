package com.leratortech.toolkit.validation;

import java.util.regex.Pattern;

/**
 * Validates phone numbers.
 */
public final class PhoneValidator {
    private PhoneValidator() {}

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^(\\+\\d{1,3}|0)\\d{8,11}$");

    public static boolean isValid(String phone) {
        return phone != null && !phone.isBlank() && PHONE_PATTERN.matcher(phone).matches();
    }
}
