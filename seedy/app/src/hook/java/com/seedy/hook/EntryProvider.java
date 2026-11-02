package com.seedy.hook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * 早注入入口桩：目标 APK 的 AndroidManifest 注册本 Provider 后，
 * onCreate 会在 Application.onCreate 之前执行，即完成 hook 安装（免 root）。
 */
public class EntryProvider extends ContentProvider {

    @Override
    public boolean onCreate() {
        if (getContext() != null) SigHook.entry(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] p, String s, String[] a, String o) {
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
    public int delete(Uri uri, String s, String[] a) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues v, String s, String[] a) {
        return 0;
    }
}
