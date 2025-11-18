package com.leratortech.toolkit.concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/**
 * AsyncUtils: helper chạy task bất đồng bộ
 */
public final class AsyncUtils {

    private AsyncUtils() {}

    /**
     * Chạy task bất đồng bộ với Executor tùy chọn
     *
     * @param supplier task trả về giá trị
     * @param executor Executor để chạy
     * @param <T> kiểu trả về
     * @return CompletableFuture<T>
     */
    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier, Executor executor) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

    /**
     * Chạy task Runnable bất đồng bộ
     *
     * @param runnable task Runnable
     * @param executor Executor để chạy
     * @return CompletableFuture<Void>
     */
    public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor) {
        return CompletableFuture.runAsync(runnable, executor);
    }
}
