package com.jacky.beedee.support.system;

import com.jacky.beedee.support.util.Strings;

/**
 * 2018/10/29.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class Device {
    /**
     * 8.0.0
     */
    private Type type;
    private String osVersion = Strings.empty;
    private String appVersion = Strings.empty;

    public Type getType() {
        return type;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    private Device() {
    }


    public static Device create(Type type, String osVersion, String appVersion) {
        Device device = new Device();
        device.osVersion = osVersion;
        device.appVersion = appVersion;
        device.type = type;
        return device;
    }

    /**
     * 设备类型
     */
    public enum Type {
        IOS("Ios"), ANDROID("Android"), DESKTOP("desktop");

        private String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Type from(String value) {
            if (value == null) {
                return DESKTOP;
            }
            for (Type type : Type.values()) {
                if (type.name().equalsIgnoreCase(value.trim())) {
                    return type;
                }
            }
            return DESKTOP;
        }
    }
}
