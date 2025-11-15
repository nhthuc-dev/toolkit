package com.leratortech.toolkit.validation;

/**
 * Validates if string is JSON (object or array).
 */
public final class JsonValidator {
    private JsonValidator() {}

    public static boolean isJsonString(String s) {
        if (s == null || s.isBlank()) return false;
        s = s.trim();
        return (s.startsWith("{") && s.endsWith("}")) || (s.startsWith("[") && s.endsWith("]"));
    }
}
