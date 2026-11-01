package android.util;

/** 编译桩：仅提供签名，无实际行为。 */
public final class Log {
    public static int i(String tag, String msg) { return 0; }
    public static int w(String tag, String msg) { return 0; }
    public static int w(String tag, String msg, Throwable tr) { return 0; }
    private Log() {}
}
