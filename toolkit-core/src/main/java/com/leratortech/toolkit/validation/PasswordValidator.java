package com.leratortech.toolkit.validation;

import java.util.regex.Pattern;

/**
 * Validates passwords.
 * Minimum 8 chars, at least 1 letter and 1 number.
 */
public final class PasswordValidator {
    private PasswordValidator() {}

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$");

    public static boolean isValid(String password) {
        return password != null && !password.isBlank() && PASSWORD_PATTERN.matcher(password).matches();
    }
}
