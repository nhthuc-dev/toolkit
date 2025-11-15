package com.leratortech.toolkit;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Reflection / Class Utilities
 * Java 17, null-safe
 */
public final class ClassUtils {

    private ClassUtils() {}

    /** Check if source class can be assigned to target class */
    public static boolean isAssignable(Class<?> source, Class<?> target) {
        if (source == null || target == null) return false;
        return target.isAssignableFrom(source);
    }

    /** Create new instance of class, wrap exception */
    public static <T> T newInstance(Class<T> clazz) {
        if (clazz == null) return null;
        try {
            Constructor<T> ctor = clazz.getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Cannot create instance of " + clazz.getName(), e);
        }
    }

    /** Get all fields including superclass */
    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null && clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /** Get value of field by name */
    public static Object getFieldValue(Object obj, String fieldName) {
        if (obj == null || fieldName == null) return null;
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Set value of field by name */
    public static void setFieldValue(Object obj, String fieldName, Object value) {
        if (obj == null || fieldName == null) return;
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Invoke method by name with arguments */
    public static Object invokeMethod(Object obj, String methodName, Object... args) {
        if (obj == null || methodName == null) return null;
        try {
            Class<?> clazz = obj.getClass();
            Method method = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(m -> m.getName().equals(methodName))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchMethodException(methodName));
            method.setAccessible(true);
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Get generic parameter type */
    public static Class<?> getGenericType(Class<?> clazz, int index) {
        if (clazz == null) return null;
        Type generic = clazz.getGenericSuperclass();
        if (generic instanceof ParameterizedType pt) {
            Type[] args = pt.getActualTypeArguments();
            if (index >= 0 && index < args.length && args[index] instanceof Class<?> c) {
                return c;
            }
        }
        return null;
    }
}
