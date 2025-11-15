package com.leratortech.toolkit.collection;

import java.util.*;
import java.util.stream.*;

public final class SetUtils {

    private SetUtils() {}

    public static <T> Set<T> union(Set<T> a, Set<T> b) {
        return Stream.of(a, b)
                .filter(Objects::nonNull)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public static <T> Set<T> intersect(Set<T> a, Set<T> b) {
        if (a == null || b == null) return Set.of();
        var set = new HashSet<>(b);
        return a.stream().filter(set::contains).collect(Collectors.toSet());
    }

    public static <T> Set<T> difference(Set<T> a, Set<T> b) {
        if (a == null) return Set.of();
        return a.stream()
                .filter(x -> b == null || !b.contains(x))
                .collect(Collectors.toSet());
    }
}
