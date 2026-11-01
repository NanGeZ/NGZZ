package com.yan.hook;

import java.lang.reflect.Field;

/**
 * 反射 / sun.misc.Unsafe 字段读写工具。
 *
 * 说明：DPT 加固的 hook.dex 大量使用混淆状态机把真实的 getDeclaredField /
 * Field.get / Field.set 藏进 C0000~C0004 这些代理类里。这里还原成等价的
 * 直白实现。之所以要走 Unsafe，是因为要写的目标字段（如 SigningInfo.mSigningDetails、
 * SigningDetails.signatures）都是 @hide / 私有字段，正常 Field.set 在高版本
 * Android 上会被 hidden API 限制拦掉，Unsafe 直接按内存偏移读写可以绕过。
 */
final class Reflect {

    private static final sun.misc.Unsafe UNSAFE = unsafe();

    private static sun.misc.Unsafe unsafe() {
        try {
            Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (sun.misc.Unsafe) f.get(null);
        } catch (Throwable t) {
            return null;
        }
    }

    /** 在类继承链里按名字查找字段（含父类），并 setAccessible(true)。 */
    static Field findField(Class<?> cls, String name) {
        for (Class<?> c = cls; c != null; c = c.getSuperclass()) {
            try {
                Field f = c.getDeclaredField(name);
                f.setAccessible(true);
                return f;
            } catch (NoSuchFieldException ignored) {
                // 继续往父类找
            }
        }
        return null;
    }

    /** 反射读取 obj 的某个字段值（按字段名，含父类），失败返回 null。 */
    static Object fieldValue(Object obj, String name) {
        if (obj == null) return null;
        try {
            Field f = findField(obj.getClass(), name);
            if (f == null) return null;
            return f.get(obj);
        } catch (Throwable t) {
            return null;
        }
    }

    /** 用 Unsafe 读字段（对被 hidden-API 拉黑的字段更稳）。 */
    static Object uGet(Object obj, Field field) {
        if (field == null || obj == null) return null;
        try {
            if (UNSAFE != null) {
                long off = UNSAFE.objectFieldOffset(field);
                // 依据字段类型分派，取出后自动装箱
                if (field.getType() == int.class) return UNSAFE.getInt(obj, off);
                if (field.getType() == long.class) return UNSAFE.getLong(obj, off);
                if (field.getType() == boolean.class) return UNSAFE.getBoolean(obj, off);
                return UNSAFE.getObject(obj, off);
            }
            field.setAccessible(true);
            return field.get(obj);
        } catch (Throwable t) {
            return null;
        }
    }

    /** 用 Unsafe 写字段，成功返回 true。 */
    static boolean uSet(Object obj, Field field, Object value) {
        if (field == null || obj == null) return false;
        try {
            if (UNSAFE != null) {
                long off = UNSAFE.objectFieldOffset(field);
                Class<?> t = field.getType();
                if (t == int.class) UNSAFE.putInt(obj, off, (Integer) value);
                else if (t == long.class) UNSAFE.putLong(obj, off, (Long) value);
                else if (t == boolean.class) UNSAFE.putBoolean(obj, off, (Boolean) value);
                else UNSAFE.putObject(obj, off, value);
                return true;
            }
            field.setAccessible(true);
            field.set(obj, value);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    /** int[] → String（还原混淆里的 S(int...) 字符串解码器）。 */
    static String decode(int[] codes) {
        char[] c = new char[codes.length];
        for (int i = 0; i < codes.length; i++) c[i] = (char) codes[i];
        return new String(c);
    }

    private Reflect() {}
}
