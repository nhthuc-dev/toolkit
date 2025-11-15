package com.leratortech.toolkit.collection;

import java.util.*;
import java.util.stream.*;

public final class ListUtils {

    private ListUtils() {}

    public static <T> T safeGet(List<T> list, int index, T defaultValue) {
        return (list == null || index < 0 || index >= list.size())
                ? defaultValue
                : list.get(index);
    }

    public static <T> List<T> distinctBy(List<T> list, java.util.function.Function<T, ?> keyExtractor) {
        if (list == null) return List.of();

        var set = new HashSet<>();
        return list.stream()
                .filter(e -> set.add(keyExtractor.apply(e)))
                .toList();
    }

    public static <T> List<T> diff(List<T> a, Collection<T> b) {
        return a == null
                ? List.of()
                : a.stream()
                .filter(x -> b == null || !b.contains(x))
                .toList();
    }

    public static <T> List<T> intersection(List<T> a, List<T> b) {
        if (a == null || b == null) return List.of();
        var set = new HashSet<>(b);
        return a.stream().filter(set::contains).toList();
    }
}
