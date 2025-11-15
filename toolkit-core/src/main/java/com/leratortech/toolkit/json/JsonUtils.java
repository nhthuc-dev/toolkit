package com.leratortech.toolkit.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;

import java.util.List;
import java.util.Map;

/**
 * Enhanced JSON Utility for Java Web applications (E-commerce ready)
 * <p>
 * Features:
 * - JSON ↔ Object (DTO/Entity)
 * - JSON ↔ List / Map
 * - Nested objects supported
 * - Null-safe
 */
public final class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.findAndRegisterModules(); // support Java 8 date/time types
    }

    private JsonUtils() {}

    // -------------------------------------------------------------------------
    // Serialize
    // -------------------------------------------------------------------------

    /** Object -> JSON string (compact) */
    public static String toJson(Object obj) {
        if (obj == null) return null;
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }

    /** Object -> JSON string (pretty print) */
    public static String toPrettyJson(Object obj) {
        if (obj == null) return null;
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object to pretty JSON", e);
        }
    }

    // -------------------------------------------------------------------------
    // Deserialize
    // -------------------------------------------------------------------------

    /** JSON string -> Object of type T */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || json.isBlank()) return null;
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize JSON", e);
        }
    }

    /** JSON string -> List<T> */
    public static <T> List<T> fromJsonToList(String json, Class<T> clazz) {
        if (json == null || json.isBlank()) return List.of();
        try {
            return mapper.readValue(json,
                    mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize JSON to List", e);
        }
    }

    /** JSON string -> Map<String, Object> (nested) */
    public static Map<String, Object> fromJsonToMap(String json) {
        if (json == null || json.isBlank()) return Map.of();
        try {
            return mapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize JSON to Map", e);
        }
    }

    /** Object -> Map<String, Object> (nested) */
    public static Map<String, Object> toMap(Object obj) {
        if (obj == null) return Map.of();
        return mapper.convertValue(obj, new TypeReference<>() {
        });
    }

    /** Map<String, Object> -> Object (DTO/Entity) */
    public static <T> T fromMap(Map<String, Object> map, Class<T> clazz) {
        if (map == null || map.isEmpty()) return null;
        return mapper.convertValue(map, clazz);
    }

    /** List<T> -> JSON string */
    public static String listToJson(List<?> list) {
        return list == null ? null : toJson(list);
    }

    /** Map<String, Object> -> JSON string */
    public static String mapToJson(Map<?, ?> map) {
        return map == null ? null : toJson(map);
    }
}
