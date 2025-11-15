package com.leratortech.toolkit.collection;

import java.util.*;
import java.util.stream.*;

public final class MapUtils {

    private MapUtils() {}

    public static <K, V> Map<K, V> filter(Map<K, V> map, java.util.function.Predicate<Map.Entry<K, V>> condition) {
        if (map == null) return Map.of();

        return map.entrySet()
                .stream()
                .filter(condition)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static <K, V> Map<K, V> merge(Map<K, V>... maps) {
        return Arrays.stream(maps)
                .filter(Objects::nonNull)
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldV, newV) -> newV
                ));
    }

    public static <K, V> Map<V, K> invert(Map<K, V> map) {
        return map == null
                ? Map.of()
                : map.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }
}
