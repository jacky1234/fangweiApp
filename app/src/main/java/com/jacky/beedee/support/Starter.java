package com.jacky.beedee.support;

import android.annotation.SuppressLint;
import android.content.Context;

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

    public static void init(Context context) {
        Starter.context = context;

        Device device = Device.create(Device.Type.ANDROID, android.os.Build.VERSION.RELEASE,
                AndroidUtil.getVersionName());
        DeviceDependency.current = new DeviceDependency(device, "https://api.beedee.yituizhineng.top");
        MySelf.init();
    }

    public static Context getContext() {
        return context;
    }
}
