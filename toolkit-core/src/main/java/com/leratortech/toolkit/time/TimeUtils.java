package com.leratortech.toolkit.time;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Utility class for date and time operations.
 * <p>
 * Designed for Java Web / E-commerce applications.
 * Supports LocalDate, LocalDateTime, ZonedDateTime, timestamps, formatting, parsing.
 */
public final class TimeUtils {

    private TimeUtils() {}

    // -------------------------------------------------------------------------
    // Now
    // -------------------------------------------------------------------------

    /** Returns current LocalDateTime in system default zone */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /** Returns current LocalDate in system default zone */
    public static LocalDate today() {
        return LocalDate.now();
    }

    /** Returns current UTC ZonedDateTime */
    public static ZonedDateTime utcNow() {
        return ZonedDateTime.now(ZoneOffset.UTC);
    }

    // -------------------------------------------------------------------------
    // Formatting & Parsing
    // -------------------------------------------------------------------------

    /**
     * Formats LocalDateTime using pattern.
     * Example pattern: "yyyy-MM-dd HH:mm:ss"
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null || pattern == null) return null;
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Formats LocalDate using pattern.
     * Example pattern: "yyyy-MM-dd"
     */
    public static String format(LocalDate date, String pattern) {
        if (date == null || pattern == null) return null;
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Parses string to LocalDateTime using pattern.
     */
    public static LocalDateTime parseToLocalDateTime(String text, String pattern) {
        if (text == null || pattern == null) return null;
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Parses string to LocalDate using pattern.
     */
    public static LocalDate parseToLocalDate(String text, String pattern) {
        if (text == null || pattern == null) return null;
        return LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    // -------------------------------------------------------------------------
    // Conversion
    // -------------------------------------------------------------------------

    /** Converts LocalDateTime to ZonedDateTime with system default zone */
    public static ZonedDateTime toZonedDateTime(LocalDateTime ldt) {
        return ldt == null ? null : ldt.atZone(ZoneId.systemDefault());
    }

    /** Converts LocalDateTime to UTC ZonedDateTime */
    public static ZonedDateTime toUtcZonedDateTime(LocalDateTime ldt) {
        return ldt == null ? null : ldt.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
    }

    /** Converts ZonedDateTime to LocalDateTime (system default zone) */
    public static LocalDateTime toLocalDateTime(ZonedDateTime zdt) {
        return zdt == null ? null : zdt.toLocalDateTime();
    }

    /** Converts Instant to LocalDateTime (system default zone) */
    public static LocalDateTime toLocalDateTime(Instant instant) {
        return instant == null ? null : LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /** Converts LocalDateTime to Instant (system default zone) */
    public static Instant toInstant(LocalDateTime ldt) {
        return ldt == null ? null : ldt.atZone(ZoneId.systemDefault()).toInstant();
    }

    // -------------------------------------------------------------------------
    // Difference & Calculation
    // -------------------------------------------------------------------------

    /** Returns difference in days between two LocalDate */
    public static long daysBetween(LocalDate start, LocalDate end) {
        if (start == null || end == null) return 0;
        return ChronoUnit.DAYS.between(start, end);
    }

    /** Returns difference in hours between two LocalDateTime */
    public static long hoursBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) return 0;
        return ChronoUnit.HOURS.between(start, end);
    }

    /** Returns difference in minutes between two LocalDateTime */
    public static long minutesBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) return 0;
        return ChronoUnit.MINUTES.between(start, end);
    }

    /** Adds days to LocalDate */
    public static LocalDate addDays(LocalDate date, long days) {
        return date == null ? null : date.plusDays(days);
    }

    /** Adds hours to LocalDateTime */
    public static LocalDateTime addHours(LocalDateTime dateTime, long hours) {
        return dateTime == null ? null : dateTime.plusHours(hours);
    }

    /** Adds minutes to LocalDateTime */
    public static LocalDateTime addMinutes(LocalDateTime dateTime, long minutes) {
        return dateTime == null ? null : dateTime.plusMinutes(minutes);
    }

    /** Checks if LocalDate is before today */
    public static boolean isBeforeToday(LocalDate date) {
        return date != null && date.isBefore(today());
    }

    /** Checks if LocalDate is after today */
    public static boolean isAfterToday(LocalDate date) {
        return date != null && date.isAfter(today());
    }
}
