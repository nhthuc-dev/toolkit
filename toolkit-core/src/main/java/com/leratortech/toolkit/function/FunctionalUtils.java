package com.leratortech.toolkit.function;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utility class for functional programming helpers in Java 17.
 */
public final class FunctionalUtils {

    private FunctionalUtils() {}

    // -------------------------------------------------------------------------
    // Optional helpers
    // -------------------------------------------------------------------------

    /** Map value if present, else return default */
    public static <T, R> R mapOrElse(T obj, Function<T, R> mapper, R defaultValue) {
        return Optional.ofNullable(obj).map(mapper).orElse(defaultValue);
    }

    /** Execute consumer if value is present (non-null) */
    public static <T> void ifPresent(T obj, Consumer<T> consumer) {
        if (obj != null) consumer.accept(obj);
    }

    /** Apply function if value is non-null, else return null */
    public static <T, R> R mapNullable(T obj, Function<T, R> mapper) {
        return obj != null ? mapper.apply(obj) : null;
    }

    // -------------------------------------------------------------------------
    // Exception handling helpers
    // -------------------------------------------------------------------------

    /** Execute supplier, wrap checked exception into RuntimeException */
    public static <T> T unchecked(SupplierWithException<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Execute runnable, wrap checked exception into RuntimeException */
    public static void unchecked(RunnableWithException runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Functional interface to allow lambda with checked exception (Supplier) */
    @FunctionalInterface
    public interface SupplierWithException<T> {
        T get() throws Exception;
    }

    /** Functional interface to allow lambda with checked exception (Runnable) */
    @FunctionalInterface
    public interface RunnableWithException {
        void run() throws Exception;
    }

    // -------------------------------------------------------------------------
    // Null-safe chaining helpers
    // -------------------------------------------------------------------------

    /** Chain two functions safely, returns default if any step null */
    public static <T, R, S> S chain(T obj, Function<T, R> f1, Function<R, S> f2, S defaultValue) {
        if (obj == null) return defaultValue;
        R r = f1.apply(obj);
        return r != null ? f2.apply(r) : defaultValue;
    }
}
