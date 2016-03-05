package com.zhitian.mybole.base;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;

public class BaseApplication extends Application {
    private static final String KEY_TOAST_MARGIN_BOTTOM_HEIGHT = "key_";
    private static String PREF_NAME = "creativelockerV2.pref";
    static Context _context;
    static Resources _resource;

    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();
        _resource = _context.getResources();
        init();
    }

    protected void init() {
    }

    public static synchronized BaseApplication context() {
        return (BaseApplication) _context;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static SharedPreferences getPreferences() {
        SharedPreferences pre = context().getSharedPreferences(PREF_NAME,
                Context.MODE_MULTI_PROCESS);
        return pre;
    }

    public static int[] getDisplaySize() {
        return new int[]{getPreferences().getInt("screen_width", 480),
                getPreferences().getInt("screen_height", 854)};
    }

    public static String string(int id) {
        return _resource.getString(id);
    }

    public static String string(int id, Object... args) {
        return _resource.getString(id, args);
    }

}
