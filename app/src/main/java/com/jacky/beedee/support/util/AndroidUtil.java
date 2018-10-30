package com.jacky.beedee.support.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.jacky.beedee.support.Starter;


/**
 * 2018/10/29.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class AndroidUtil {
    private static Handler uiHandle = new Handler(Looper.getMainLooper());

    /**
     * 获取应用版本信息
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName;
        // 获取当前应用版本号
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "1.0.0";
        }
    }

    public static void toast(int id) {
        toast(getString(id));
    }

    public static void toast(CharSequence msg) {
        Toast.makeText(Starter.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static CharSequence getString(int id) {
        return Starter.getContext().getString(id);
    }

    public static void runUI(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            uiHandle.post(runnable);
        }
    }

    public static void runUI(Runnable runnable, long delay) {
        uiHandle.postDelayed(runnable, delay);
    }
}
