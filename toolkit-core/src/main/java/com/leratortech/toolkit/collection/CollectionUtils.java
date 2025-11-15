package com.leratortech.toolkit.collection;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CollectionUtils {

    private CollectionUtils() {}

    /**
     * Checks if a collection is null or empty.
     */
    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

    /**
     * Checks if a collection is not null and not empty.
     */
    public static boolean isNotEmpty(Collection<?> c) {
        return !isEmpty(c);
    }

    /**
     * Returns the first element of a list or null if empty.
     */
    public static <T> T first(List<T> list) {
        if (isEmpty(list)) return null;
        return list.get(0);
    }

    /**
     * Returns the last element of a list or null if empty.
     */
    public static <T> T last(List<T> list) {
        if (isEmpty(list)) return null;
        return list.get(list.size() - 1);
    }

    /**
     * Converts a list into a map using keyMapper. Duplicate keys keep the latest value.
     */
    public static <T, K> Map<K, T> toMap(List<T> list, Function<T, K> keyMapper) {
        if (isEmpty(list)) return Collections.emptyMap();
        return list.stream()
                .collect(Collectors.toMap(
                        keyMapper,
                        v -> v,
                        (a, b) -> b
                ));
    }

    /**
     * Removes null elements from the list.
     */
    public static <T> List<T> removeNulls(List<T> list) {
        if (list == null) return List.of();
        return list.stream().filter(Objects::nonNull).toList();
    }

    /**
     * Returns a new list with unique elements (preserving order).
     */
    public static <T> List<T> distinct(List<T> list) {
        if (isEmpty(list)) return List.of();
        return list.stream().distinct().toList();
    }

    /**
     * Safe get element from list. Returns defaultValue if out of range.
     */
    public static <T> T getOrDefault(List<T> list, int index, T defaultValue) {
        if (list == null || index < 0 || index >= list.size()) return defaultValue;
        return list.get(index);
    }
}
