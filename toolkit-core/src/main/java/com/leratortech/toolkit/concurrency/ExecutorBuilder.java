package com.leratortech.toolkit.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * ExecutorBuilder: xây thread pool linh hoạt, an toàn, dễ sử dụng cho production.
 * Hỗ trợ:
 * - Fixed / Cached / Scheduled thread pool
 * - ThreadFactory đặt tên thread và xử lý uncaught exception
 * - Submit nhiều tác vụ đồng thời
 * - Shutdown an toàn với timeout
 */
public final class ExecutorBuilder {

    private ExecutorBuilder() {}

    // -----------------------------
    // ThreadFactory
    // -----------------------------
    public static ThreadFactory threadFactory(String namePrefix) {
        AtomicInteger count = new AtomicInteger(1);
        return r -> {
            Thread t = new Thread(r);
            t.setName(namePrefix + "-" + count.getAndIncrement());
            t.setDaemon(false);
            t.setUncaughtExceptionHandler((thread, ex) -> {
                System.err.printf("Thread %s threw exception: %s%n", thread.getName(), ex);
            });
            return t;
        };
    }

    // -----------------------------
    // ExecutorService builders
    // -----------------------------
    public static ExecutorService fixed(int size, String name) {
        return new ThreadPoolExecutor(
                size, size,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                threadFactory(name),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    public static ExecutorService cached(String name) {
        return new ThreadPoolExecutor(
                0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                threadFactory(name),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    public static ScheduledExecutorService scheduled(int size, String name) {
        return Executors.newScheduledThreadPool(size, threadFactory(name));
    }

    // -----------------------------
    // Task helpers
    // -----------------------------
    public static <T> void invokeAll(ExecutorService executor, Iterable<? extends Callable<T>> tasks) {
        Collection<? extends Callable<T>> taskCollection = tasks instanceof Collection
                ? (Collection<? extends Callable<T>>) tasks
                : new ArrayList<>() {{
            tasks.forEach(this::add);
        }};

        try {
            executor.invokeAll(taskCollection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("invokeAll interrupted", e);
        }
    }

    // -----------------------------
    // Shutdown helpers
    // -----------------------------
    public static void shutdownGracefully(ExecutorService executor, long timeoutSeconds, Consumer<Exception> onError) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(timeoutSeconds, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(timeoutSeconds, TimeUnit.SECONDS)) {
                    onError.accept(new RuntimeException("Executor did not terminate"));
                }
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
            onError.accept(e);
        }
    }
}
