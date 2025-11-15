package com.leratortech.toolkit.number;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Utility class for common math operations.
 */
public final class MathUtils {

    private MathUtils() {}

    /** Sum a list of BigDecimal, returns 0 if list null or empty */
    public static BigDecimal sum(List<BigDecimal> numbers) {
        if (numbers == null || numbers.isEmpty()) return BigDecimal.ZERO;
        return numbers.stream()
                .filter(n -> n != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /** Average of list of BigDecimal, returns 0 if empty */
    public static BigDecimal average(List<BigDecimal> numbers, int scale) {
        if (numbers == null || numbers.isEmpty()) return BigDecimal.ZERO;
        BigDecimal total = sum(numbers);
        return total.divide(BigDecimal.valueOf(numbers.size()), scale, RoundingMode.HALF_UP);
    }

    /** Min of list of BigDecimal, returns null if empty */
    public static BigDecimal min(List<BigDecimal> numbers) {
        if (numbers == null || numbers.isEmpty()) return null;
        return numbers.stream().filter(n -> n != null).min(BigDecimal::compareTo).orElse(null);
    }

    /** Max of list of BigDecimal, returns null if empty */
    public static BigDecimal max(List<BigDecimal> numbers) {
        if (numbers == null || numbers.isEmpty()) return null;
        return numbers.stream().filter(n -> n != null).max(BigDecimal::compareTo).orElse(null);
    }
}
