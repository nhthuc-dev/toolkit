package com.leratortech.toolkit.collection;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CollectionUtilsTest {

    // -----------------------------
    // isEmpty / isNotEmpty
    // -----------------------------
    @Test
    void testIsEmpty_null() {
        assertTrue(CollectionUtils.isEmpty(null));
    }

    @Test
    void testIsEmpty_empty() {
        assertTrue(CollectionUtils.isEmpty(Collections.emptyList()));
    }

    @Test
    void testIsEmpty_notEmpty() {
        assertFalse(CollectionUtils.isEmpty(List.of("a")));
    }

    @Test
    void testIsNotEmpty() {
        assertTrue(CollectionUtils.isNotEmpty(List.of("x")));
        assertFalse(CollectionUtils.isNotEmpty(Collections.emptyList()));
        assertFalse(CollectionUtils.isNotEmpty(null));
    }

    // -----------------------------
    // first / last
    // -----------------------------
    @Test
    void testFirst() {
        List<String> list = List.of("a", "b", "c");
        assertEquals("a", CollectionUtils.first(list));
        assertNull(CollectionUtils.first(Collections.emptyList()));
        assertNull(CollectionUtils.first(null));
    }

    @Test
    void testLast() {
        List<String> list = List.of("a", "b", "c");
        assertEquals("c", CollectionUtils.last(list));
        assertNull(CollectionUtils.last(Collections.emptyList()));
        assertNull(CollectionUtils.last(null));
    }

    // -----------------------------
    // toMap
    // -----------------------------
    @Test
    void testToMap() {
        class Person {
            final String id;
            final String name;
            Person(String id, String name) { this.id = id; this.name = name; }
        }

        List<Person> people = List.of(
                new Person("1", "Alice"),
                new Person("2", "Bob"),
                new Person("1", "Charlie") // duplicate key, should keep last
        );

        Map<String, Person> map = CollectionUtils.toMap(people, p -> p.id);
        assertEquals(2, map.size());
        assertEquals("Charlie", map.get("1").name);
        assertEquals("Bob", map.get("2").name);
    }

    // -----------------------------
    // removeNulls
    // -----------------------------
    @Test
    void testRemoveNulls() {
        List<String> list = Arrays.asList("a", null, "b", null);
        List<String> result = CollectionUtils.removeNulls(list);
        assertEquals(2, result.size());
        assertTrue(result.contains("a"));
        assertTrue(result.contains("b"));

        assertTrue(CollectionUtils.removeNulls(null).isEmpty());
    }

    // -----------------------------
    // distinct
    // -----------------------------
    @Test
    void testDistinct() {
        List<String> list = Arrays.asList("a", "b", "a", "c", "b");
        List<String> result = CollectionUtils.distinct(list);
        assertEquals(List.of("a", "b", "c"), result);

        assertTrue(CollectionUtils.distinct(null).isEmpty());
        assertTrue(CollectionUtils.distinct(Collections.emptyList()).isEmpty());
    }

    // -----------------------------
    // getOrDefault
    // -----------------------------
    @Test
    void testGetOrDefault() {
        List<String> list = List.of("x", "y");
        assertEquals("x", CollectionUtils.getOrDefault(list, 0, "default"));
        assertEquals("y", CollectionUtils.getOrDefault(list, 1, "default"));
        assertEquals("default", CollectionUtils.getOrDefault(list, 2, "default"));
        assertEquals("default", CollectionUtils.getOrDefault(list, -1, "default"));
        assertEquals("default", CollectionUtils.getOrDefault(null, 0, "default"));
    }
}
