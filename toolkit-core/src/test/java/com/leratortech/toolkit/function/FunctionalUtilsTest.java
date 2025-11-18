package com.leratortech.toolkit.function;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalUtilsTest {

    // -----------------------------
    // mapOrElse
    // -----------------------------
    @Test
    void testMapOrElse_nonNull() {
        String value = "hello";
        String result = FunctionalUtils.mapOrElse(value, String::toUpperCase, "default");
        assertEquals("HELLO", result);
    }

    @Test
    void testMapOrElse_null() {
        String result = FunctionalUtils.mapOrElse("hello", String::toUpperCase, "default");
        assertEquals("HELLO", result);
    }

    // -----------------------------
    // ifPresent
    // -----------------------------
    @Test
    void testIfPresent_nonNull() {
        AtomicBoolean flag = new AtomicBoolean(false);
        FunctionalUtils.ifPresent("test", s -> flag.set(true));
        assertTrue(flag.get());
    }

    @Test
    void testIfPresent_null() {
        AtomicBoolean flag = new AtomicBoolean(false);
        FunctionalUtils.ifPresent(null, s -> flag.set(true));
        assertFalse(flag.get());
    }

    // -----------------------------
    // mapNullable
    // -----------------------------
    @Test
    void testMapNullable_nonNull() {
        String result = FunctionalUtils.mapNullable("hello", s -> s.toUpperCase());
        assertEquals("HELLO", result);
    }

    @Test
    void testMapNullable_null() {
        String n = null;
        String result = FunctionalUtils.mapNullable(n, s -> s.toUpperCase());
        assertNull(result);
    }



    // -----------------------------
    // unchecked supplier
    // -----------------------------
    @Test
    void testUncheckedSupplier_success() {
        String result = FunctionalUtils.unchecked(() -> "ok");
        assertEquals("ok", result);
    }

    @Test
    void testUncheckedSupplier_exception() {
        assertThrows(RuntimeException.class, () ->
                FunctionalUtils.unchecked(() -> { throw new Exception("fail"); })
        );
    }

    // -----------------------------
    // unchecked runnable
    // -----------------------------
    @Test
    void testUncheckedRunnable_success() {
        AtomicBoolean flag = new AtomicBoolean(false);
        FunctionalUtils.unchecked(() -> flag.set(true));
        assertTrue(flag.get());
    }

    @Test
    void testUncheckedRunnable_exception() {
        assertThrows(RuntimeException.class, () ->
                FunctionalUtils.unchecked(() -> { throw new Exception("fail"); })
        );
    }

    // -----------------------------
    // chain
    // -----------------------------
    @Test
    void testChain_allNonNull() {
        String result = FunctionalUtils.chain("abc", String::toUpperCase, s -> s + "!", "default");
        assertEquals("ABC!", result);
    }

    @Test
    void testChain_firstNull() {
        String n = null;
        String result = FunctionalUtils.chain(n, String::toUpperCase, s -> s + "!", "default");
        assertEquals("default", result);
    }

    @Test
    void testChain_secondNull() {
        String result = FunctionalUtils.chain("abc", s -> null, s -> s + "!", "default");
        assertEquals("default", result);
    }
}
