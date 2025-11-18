package com.leratortech.toolkit.json;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilsTest {

    static class User {
        public String name;
        public int age;

        public User() {}
        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof User u)) return false;
            return age == u.age && (name != null ? name.equals(u.name) : u.name == null);
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + age;
            return result;
        }
    }

    // -----------------------------
    // toJson / toPrettyJson
    // -----------------------------
    @Test
    void testToJson() {
        User u = new User("Alice", 30);
        String json = JsonUtils.toJson(u);
        assertTrue(json.contains("\"name\":\"Alice\""));
        assertTrue(json.contains("\"age\":30"));
    }

    @Test
    void testToPrettyJson() {
        User u = new User("Bob", 25);
        String pretty = JsonUtils.toPrettyJson(u);
        assertTrue(pretty.contains("\"name\""));
        assertTrue(pretty.contains("\"age\""));
    }

    @Test
    void testToJson_null() {
        assertNull(JsonUtils.toJson(null));
        assertNull(JsonUtils.toPrettyJson(null));
    }

    // -----------------------------
    // fromJson
    // -----------------------------
    @Test
    void testFromJson() {
        String json = "{\"name\":\"Alice\",\"age\":30}";
        User u = JsonUtils.fromJson(json, User.class);
        assertEquals(new User("Alice", 30), u);
    }

    @Test
    void testFromJson_nullOrBlank() {
        assertNull(JsonUtils.fromJson(null, User.class));
        assertNull(JsonUtils.fromJson("  ", User.class));
    }

    // -----------------------------
    // fromJsonToList
    // -----------------------------
    @Test
    void testFromJsonToList() {
        String json = "[{\"name\":\"Alice\",\"age\":30},{\"name\":\"Bob\",\"age\":25}]";
        List<User> list = JsonUtils.fromJsonToList(json, User.class);
        assertEquals(2, list.size());
        assertEquals(new User("Alice", 30), list.get(0));
        assertEquals(new User("Bob", 25), list.get(1));
    }

    @Test
    void testFromJsonToList_nullOrBlank() {
        List<User> list1 = JsonUtils.fromJsonToList(null, User.class);
        List<User> list2 = JsonUtils.fromJsonToList(" ", User.class);
        assertTrue(list1.isEmpty());
        assertTrue(list2.isEmpty());
    }

    // -----------------------------
    // fromJsonToMap / toMap / fromMap
    // -----------------------------
    @Test
    void testFromJsonToMap() {
        String json = "{\"name\":\"Alice\",\"age\":30}";
        Map<String, Object> map = JsonUtils.fromJsonToMap(json);
        assertEquals("Alice", map.get("name"));
        assertEquals(30, map.get("age"));
    }

    @Test
    void testToMap_and_fromMap() {
        User u = new User("Bob", 25);
        Map<String, Object> map = JsonUtils.toMap(u);
        assertEquals("Bob", map.get("name"));
        assertEquals(25, map.get("age"));

        User u2 = JsonUtils.fromMap(map, User.class);
        assertEquals(u, u2);
    }

    @Test
    void testMap_nullOrEmpty() {
        assertTrue(JsonUtils.toMap(null).isEmpty());
        assertNull(JsonUtils.fromMap(null, User.class));
        assertNull(JsonUtils.fromMap(Map.of(), User.class));
    }

    // -----------------------------
    // listToJson / mapToJson
    // -----------------------------
    @Test
    void testListAndMapToJson() {
        List<User> users = List.of(new User("Alice", 30));
        String listJson = JsonUtils.listToJson(users);
        assertTrue(listJson.contains("Alice"));

        Map<String, Object> map = Map.of("x", 1, "y", "ok");
        String mapJson = JsonUtils.mapToJson(map);
        assertTrue(mapJson.contains("\"x\":1"));
        assertTrue(mapJson.contains("\"y\":\"ok\""));

        assertNull(JsonUtils.listToJson(null));
        assertNull(JsonUtils.mapToJson(null));
    }
}
