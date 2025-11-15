package com.leratortech.toolkit.validation;

import java.util.regex.Pattern;

/**
 * Validates usernames.
 * Letters, numbers, underscores, length 3-20.
 */
public final class UsernameValidator {
    private UsernameValidator() {}

    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_]{3,20}$");

    public static boolean isValid(String username) {
        return username != null && !username.isBlank() && USERNAME_PATTERN.matcher(username).matches();
    }
}
