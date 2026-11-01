package com.yan.tool.shell;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import java.util.Objects;

/* loaded from: classes.dex */
public class ProxyApplication extends Application {
    public String a = "";
    public Application b = null;

    public final void a() {
        if (!a.b || TextUtils.isEmpty(this.a)) {
            return;
        }
        Application application = (Application) JniBridge.ra(this.a);
        this.b = application;
        application.getClass();
        JniBridge.craoc(this.a);
        a.b = false;
    }

    @Override // android.content.ContextWrapper
    public final void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        Objects.toString(context.getClassLoader());
        if (!a.a) {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (applicationInfo == null) {
                throw new NullPointerException("application info is null");
            }
            a.c(applicationInfo.sourceDir, applicationInfo.dataDir);
            JniBridge.a(applicationInfo.dataDir);
            JniBridge.ia();
            JniBridge.cbde(context.getClassLoader());
            a.a = true;
        }
        this.a = JniBridge.rapn();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final Context createPackageContext(String str, int i) {
        if (TextUtils.isEmpty(this.a)) {
            return super.createPackageContext(str, i);
        }
        a();
        return this.b;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final String getPackageName() {
        return !TextUtils.isEmpty(this.a) ? "" : super.getPackageName();
    }

    @Override // android.app.Application
    public final void onCreate() {
        super.onCreate();
        a();
    }
}
