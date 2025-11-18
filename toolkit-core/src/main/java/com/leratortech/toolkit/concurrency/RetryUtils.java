package com.leratortech.toolkit.concurrency;

import java.time.Duration;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * RetryUtils: retry task khi thất bại
 * Hỗ trợ:
 * - Retry cố định hoặc exponential backoff
 * - Predicate quyết định có retry hay không
 */
public final class RetryUtils {

    private RetryUtils() {}

    /**
     * Retry task với số lần, delay cố định
     *
     * @param action task trả về T
     * @param maxAttempts số lần retry
     * @param delay thời gian delay giữa các lần
     * @param retryOn predicate quyết định retry
     * @param <T> kiểu trả về
     * @return kết quả task
     */
    public static <T> T retry(Supplier<T> action, int maxAttempts, Duration delay, Predicate<Exception> retryOn) {
        Exception last = null;
        for (int i = 1; i <= maxAttempts; i++) {
            try {
                return action.get();
            } catch (Exception e) {
                last = e;
                if (i == maxAttempts || !retryOn.test(e)) {
                    throw new RuntimeException("Retry failed after " + i + " attempts", e);
                }
                sleep(delay);
            }
        }
        throw new RuntimeException(last);
    }

    /**
     * Retry task với exponential backoff
     *
     * @param action task trả về T
     * @param maxAttempts số lần retry
     * @param initialDelay delay lần đầu
     * @param retryOn predicate quyết định retry
     * @param <T> kiểu trả về
     * @return kết quả task
     */
    public static <T> T retryExponentialBackoff(Supplier<T> action, int maxAttempts, Duration initialDelay, Predicate<Exception> retryOn) {
        Duration current = initialDelay;
        Exception last = null;
        for (int i = 1; i <= maxAttempts; i++) {
            try {
                return action.get();
            } catch (Exception e) {
                last = e;
                if (i == maxAttempts || !retryOn.test(e)) {
                    throw new RuntimeException("Retry failed after " + i + " attempts", e);
                }
                sleep(current);
                current = current.multipliedBy(2);
            }
        }
        throw new RuntimeException(last);
    }

    private static void sleep(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
