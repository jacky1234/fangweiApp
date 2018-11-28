package com.jacky.beedee.support;

import android.annotation.SuppressLint;
import android.content.Context;

import com.jacky.beedee.BuildConfig;
import com.jacky.beedee.logic.entity.module.MySelf;
import com.jacky.beedee.support.system.Device;
import com.jacky.beedee.support.system.DeviceDependency;
import com.jacky.beedee.support.util.AndroidUtil;

/**
 * 2018/10/29.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class Starter {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private static final String debug_url = "https://api.beedee.yituizhineng.top";
    private static final String release_url = "https://api.beedeemade.com";

    public static void init(Context context) {
        Starter.context = context;

        Device device = Device.create(Device.Type.ANDROID, android.os.Build.VERSION.RELEASE,
                AndroidUtil.getVersionName());


        if (BuildConfig.DEBUG) {
            DeviceDependency.current = new DeviceDependency(device, debug_url);
        } else {
            DeviceDependency.current = new DeviceDependency(device, release_url);
        }

        MySelf.init();
    }

    public static Context getContext() {
        return context;
    }
}
