package __NGZZ_PKG__;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

public class NGZZInit extends ContentProvider {

    @Override
    public boolean onCreate() {
        try {
            Context ctx = getContext();
            if (ctx != null) {
                Context appCtx = ctx.getApplicationContext();
                if (appCtx instanceof Application) {
                    NGZZGuard.install((Application) appCtx);
                }
            }
        } catch (Throwable ignored) {
        }
        return true;
    }

    public static void init(Application app) {
        NGZZGuard.install(app);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        return null;
    }
}
