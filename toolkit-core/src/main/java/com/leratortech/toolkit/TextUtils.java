package com.leratortech.toolkit;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Text / String Utilities
 * Java 17, null-safe
 * Bao gồm:
 *  - Basic checks / modification
 *  - Random / UUID
 *  - Slug / Truncate / Repeat / Pad
 *  - Mask sensitive data
 *  - Join / Split / Template replacement
 */
public final class TextUtils {

    private static final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new Random();

    private TextUtils() {}

    // -----------------------------
    // Basic checks
    // -----------------------------

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isNotBlank(String str) {
        return str != null && !str.isBlank();
    }

    // -----------------------------
    // String modification
    // -----------------------------

    public static String capitalize(String str) {
        if (isEmpty(str)) return str;
        return str.substring(0,1).toUpperCase(Locale.ROOT) + str.substring(1);
    }

    public static String uncapitalize(String str) {
        if (isEmpty(str)) return str;
        return str.substring(0,1).toLowerCase(Locale.ROOT) + str.substring(1);
    }

    public static String reverse(String str) {
        if (str == null) return null;
        return new StringBuilder(str).reverse().toString();
    }

    public static String truncate(String str, int maxLen) {
        if (str == null) return null;
        return str.length() <= maxLen ? str : str.substring(0, maxLen);
    }

    public static String slugify(String str) {
        if (isEmpty(str)) return str;
        return str.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }

    public static String repeat(String str, int times) {
        if (isEmpty(str) || times <= 0) return "";
        return str.repeat(times);
    }

    public static String leftPad(String str, int length, char padChar) {
        if (str == null) str = "";
        if (str.length() >= length) return str;
        return "%1$" + length + "s".formatted(str).replace(' ', padChar);
    }

    public static String rightPad(String str, int length, char padChar) {
        if (str == null) str = "";
        if (str.length() >= length) return str;
        return "%1$-" + length + "s".formatted(str).replace(' ', padChar);
    }

    // -----------------------------
    // Random / UUID
    // -----------------------------

    public static String randomString(int length) {
        if (length <= 0) return "";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHANUM.charAt(RANDOM.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    // -----------------------------
    // Mask sensitive data
    // -----------------------------

    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) return email;
        String[] parts = email.split("@");
        String name = parts[0];
        if (name.length() <= 1) return "*" + "@" + parts[1];
        return name.charAt(0) + "*".repeat(name.length() - 1) + "@" + parts[1];
    }

    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) return phone;
        int visible = 3;
        int maskLen = phone.length() - visible - 3;
        if (maskLen <= 0) return "*".repeat(phone.length());
        return phone.substring(0, visible) + "*".repeat(maskLen) + phone.substring(phone.length() - 3);
    }

    public static String maskCard(String card) {
        if (card == null || card.length() < 4) return card;
        String visible = card.substring(card.length() - 4);
        return "**** **** **** " + visible;
    }

    // -----------------------------
    // Join / Split / Trim
    // -----------------------------

    public static String join(String delimiter, String... items) {
        if (items == null) return "";
        return Arrays.stream(items)
                .filter(s -> s != null)
                .collect(Collectors.joining(delimiter));
    }

    public static String[] splitAndTrim(String str, String delimiter) {
        if (str == null) return new String[0];
        return Arrays.stream(str.split(delimiter))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
    }

    // -----------------------------
    // Template replacement
    // -----------------------------

    public static String replaceTemplate(String template, Map<String,String> replacements) {
        if (template == null || replacements == null || replacements.isEmpty()) return template;
        String result = template;
        for (var entry : replacements.entrySet()) {
            result = result.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return result;
    }
}
