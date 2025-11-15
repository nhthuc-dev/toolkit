package com.leratortech.toolkit.number;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utility class for money and currency operations in Java Web / E-commerce applications.
 */
public final class MoneyUtils {

    private MoneyUtils() {}

    /** Format BigDecimal amount to currency string by locale */
    public static String format(BigDecimal amount, Locale locale) {
        if (amount == null || locale == null) return null;
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        return nf.format(amount);
    }

    /** Format BigDecimal amount as VND */
    public static String formatVND(BigDecimal amount) {
        return format(amount, new Locale("vi", "VN"));
    }

    /** Parse string to BigDecimal (ignoring non-digit chars), returns null if invalid */
    public static BigDecimal parse(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return new BigDecimal(s.replaceAll("[^\\d.-]", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /** Round to scale */
    public static BigDecimal round(BigDecimal amount, int scale) {
        if (amount == null) return null;
        return amount.setScale(scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal ceil(BigDecimal amount, int scale) {
        if (amount == null) return null;
        return amount.setScale(scale, RoundingMode.CEILING);
    }

    public static BigDecimal floor(BigDecimal amount, int scale) {
        if (amount == null) return null;
        return amount.setScale(scale, RoundingMode.FLOOR);
    }

    /** Calculate percentage: amount * rate (%) */
    public static BigDecimal percent(BigDecimal amount, BigDecimal rate) {
        if (amount == null || rate == null) return null;
        return amount.multiply(rate).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    /** Apply discount: amount - discount% */
    public static BigDecimal applyDiscount(BigDecimal amount, BigDecimal discountPercent) {
        if (amount == null || discountPercent == null) return null;
        return amount.subtract(percent(amount, discountPercent));
    }

    /** Add VAT (%) to amount */
    public static BigDecimal addVAT(BigDecimal amount, BigDecimal vatPercent) {
        if (amount == null || vatPercent == null) return null;
        return amount.add(percent(amount, vatPercent));
    }

    /** Convert minor unit (cents) to major unit (dollars) */
    public static BigDecimal minorToMajor(long minor, int scale) {
        return BigDecimal.valueOf(minor).divide(BigDecimal.valueOf(100), scale, RoundingMode.HALF_UP);
    }

    /** Convert major unit (dollars) to minor unit (cents) */
    public static long majorToMinor(BigDecimal major) {
        if (major == null) return 0L;
        return major.multiply(BigDecimal.valueOf(100)).longValue();
    }
}
