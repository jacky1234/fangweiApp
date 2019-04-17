package com.jacky.labeauty.support;

import android.annotation.SuppressLint;
import android.content.Context;

import com.jacky.labeauty.BuildConfig;
import com.jacky.labeauty.logic.entity.module.MySelf;
import com.jacky.labeauty.support.system.Device;
import com.jacky.labeauty.support.system.DeviceDependency;
import com.jacky.labeauty.support.util.AndroidUtil;

/**
 * 2018/10/29.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 *
 *
 * 测试万能验证码： 0000
 *
 */
public class Starter {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private static final String debug_url = "https://api.labeauty.yituizhineng.top";
    private static final String release_url = "";

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
