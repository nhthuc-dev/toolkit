package com.leratortech.toolkit.validation;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Validates URLs.
 */
public final class UrlValidator {
    private UrlValidator() {}

    public static boolean isValid(String url) {
        if (url == null || url.isBlank()) return false;
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
