package com.leratortech.toolkit.collection;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.*;

public final class ArrayUtils {

    private ArrayUtils() {}

    /** Null-safe: length = 0 nếu mảng null */
    public static int length(Object[] arr) {
        return arr == null ? 0 : arr.length;
    }

    /** Null-safe: true nếu mảng null hoặc rỗng */
    public static boolean isEmpty(Object[] arr) {
        return arr == null || arr.length == 0;
    }

    /** Null-safe: false nếu mảng null hoặc rỗng */
    public static boolean isNotEmpty(Object[] arr) {
        return !isEmpty(arr);
    }

    /** Lấy phần tử an toàn */
    public static <T> T safeGet(T[] arr, int index, T defaultValue) {
        return (arr == null || index < 0 || index >= arr.length)
                ? defaultValue
                : arr[index];
    }

    /** Loại bỏ null trong array */
    public static <T> T[] compact(T[] arr, Class<T> type) {
        if (arr == null) return newArray(type, 0);

        var filtered = Arrays.stream(arr)
                .filter(Objects::nonNull)
                .toList();

        return filtered.toArray(size -> newArray(type, size));
    }

    /** Flatten mảng 2 chiều */
    public static <T> T[] flatten(T[][] arr, Class<T> type) {
        if (arr == null) return newArray(type, 0);

        return Arrays.stream(arr)
                .filter(Objects::nonNull)
                .flatMap(Arrays::stream)
                .toArray(size -> newArray(type, size));
    }

    /** Tạo mảng mới theo type */
    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(Class<T> type, int size) {
        return (T[]) Array.newInstance(type, size);
    }

    /** Map phần tử trong mảng */
    public static <T, R> R[] map(T[] arr, Class<R> type, java.util.function.Function<T, R> mapper) {
        if (arr == null) return newArray(type, 0);

        return Arrays.stream(arr)
                .map(mapper)
                .toArray(size -> newArray(type, size));
    }

    /** Filter mảng */
    public static <T> T[] filter(T[] arr, Class<T> type, java.util.function.Predicate<T> pred) {
        if (arr == null) return newArray(type, 0);

        return Arrays.stream(arr)
                .filter(pred)
                .toArray(size -> newArray(type, size));
    }

    /** Tìm phần tử thỏa điều kiện */
    public static <T> Optional<T> find(T[] arr, java.util.function.Predicate<T> pred) {
        if (arr == null) return Optional.empty();
        return Arrays.stream(arr).filter(pred).findFirst();
    }

    /** Contains (dùng equals, null-safe) */
    public static <T> boolean contains(T[] arr, T value) {
        return arr != null && Arrays.stream(arr).anyMatch(e -> Objects.equals(e, value));
    }

    /** Join thành String */
    public static <T> String join(T[] arr, String sep) {
        return arr == null ? "" : Arrays.stream(arr).map(String::valueOf).collect(Collectors.joining(sep));
    }

    /** Reverse mảng */
    public static <T> T[] reverse(T[] arr, Class<T> type) {
        if (arr == null) return newArray(type, 0);

        var list = Arrays.asList(arr.clone());
        Collections.reverse(list);

        return list.toArray(size -> newArray(type, size));
    }

    /** Lấy phần tử ngẫu nhiên */
    public static <T> T random(T[] arr) {
        if (isEmpty(arr)) return null;
        return arr[new Random().nextInt(arr.length)];
    }
}
