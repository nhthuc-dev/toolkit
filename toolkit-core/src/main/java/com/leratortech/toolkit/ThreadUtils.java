package com.leratortech.toolkit;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Thread / Async Utilities
 * - Null-safe, thread-safe
 * - Không cố định ExecutorService, có thể inject executor tùy chỉnh
 * - Hỗ trợ async task, timeout, đo thời gian, awaitAll
 */
public final class ThreadUtils {

    // Default executor, có thể override
    private static ExecutorService defaultExecutor = Executors.newCachedThreadPool();

    private ThreadUtils() {}

    // -----------------------------
    // Executor management
    // -----------------------------

    /** Set default executor for library usage */
    public static void setDefaultExecutor(ExecutorService executor) {
        if (executor != null) defaultExecutor = executor;
    }

    /** Get current default executor */
    public static ExecutorService getDefaultExecutor() {
        return defaultExecutor;
    }

    // -----------------------------
    // Async helpers
    // -----------------------------

    /** Run Runnable async using default executor */
    public static CompletableFuture<Void> runAsync(Runnable task) {
        return CompletableFuture.runAsync(task, defaultExecutor);
    }

    /** Run Runnable async using provided executor */
    public static CompletableFuture<Void> runAsync(Runnable task, Executor executor) {
        return CompletableFuture.runAsync(task, executor);
    }

    /** Run Supplier async using default executor */
    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, defaultExecutor);
    }

    /** Run Supplier async using provided executor */
    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier, Executor executor) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

    // -----------------------------
    // Synchronization helpers
    // -----------------------------

    /** Wait for all futures to complete */
    public static void awaitAll(List<CompletableFuture<?>> futures) {
        if (futures == null || futures.isEmpty()) return;
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    /** Sleep for millis, null-safe, wrap InterruptedException */
    public static void sleepMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // -----------------------------
    // Timeout / measure
    // -----------------------------

    /** Run Runnable with timeout (ms), cancel if exceeded */
    public static void runWithTimeout(Runnable task, long timeoutMillis) throws TimeoutException {
        runWithTimeout(task, timeoutMillis, defaultExecutor);
    }

    /** Run Runnable with timeout (ms) using specific executor */
    public static void runWithTimeout(Runnable task, long timeoutMillis, ExecutorService executor) throws TimeoutException {
        Future<?> future = executor.submit(task);
        try {
            future.get(timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            throw e;
        } catch (Exception ignored) {}
    }

    /** Measure execution time of a Runnable task in milliseconds */
    public static long measureTime(Runnable task) {
        long start = System.currentTimeMillis();
        task.run();
        return System.currentTimeMillis() - start;
    }

    // -----------------------------
    // Utility / convenience
    // -----------------------------

    /** Run Runnable async and ignore exceptions */
    public static void runAsyncIgnore(Runnable task) {
        runAsync(() -> {
            try { task.run(); } catch (Exception ignored) {}
        });
    }

    /** Run Supplier async and return fallback if exception */
    public static <T> CompletableFuture<T> supplyAsyncFallback(Supplier<T> supplier, T fallback) {
        return supplyAsync(() -> {
            try { return supplier.get(); }
            catch (Exception e) { return fallback; }
        });
    }

    /** Shutdown default executor gracefully */
    public static void shutdownDefaultExecutor() {
        defaultExecutor.shutdown();
    }
}
