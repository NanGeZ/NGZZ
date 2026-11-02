package com.seedy.hook;

import java.lang.reflect.Field;

/** 反射小工具：沿继承链查找字段。 */
public final class Reflect {
    private Reflect() {}

    public static Field findField(Object obj, String name) {
        Class<?> c = obj.getClass();
        while (c != null) {
            try {
                Field f = c.getDeclaredField(name);
                f.setAccessible(true);
                return f;
            } catch (NoSuchFieldException ignored) {
                c = c.getSuperclass();
            }
        }
        return null;
    }

    public static Object fieldValue(Object obj, String name) {
        try {
            Field f = findField(obj, name);
            return f == null ? null : f.get(obj);
        } catch (Throwable t) {
            return null;
        }
    }
}
