package __NGZZ_PKG__;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class NGZZProxy implements InvocationHandler {

    private final Object base;

    private NGZZProxy(Object base) {
        this.base = base;
    }

    public static Object wrap(Object base) {
        if (base == null) {
            return null;
        }
        Class<?>[] interfaces = base.getClass().getInterfaces();
        if (interfaces.length == 0) {
            return base;
        }
        return Proxy.newProxyInstance(base.getClass().getClassLoader(), interfaces, new NGZZProxy(base));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        NGZZGuard.install();
        return method.invoke(base, args);
    }
}
