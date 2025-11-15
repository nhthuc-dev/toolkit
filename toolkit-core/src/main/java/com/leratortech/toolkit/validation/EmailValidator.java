package com.leratortech.toolkit.validation;

import java.util.regex.Pattern;

/**
 * Validates email addresses.
 */
public final class EmailValidator {
    private EmailValidator() {}

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    public static boolean isValid(String email) {
        return email != null && !email.isBlank() && EMAIL_PATTERN.matcher(email).matches();
    }
}
