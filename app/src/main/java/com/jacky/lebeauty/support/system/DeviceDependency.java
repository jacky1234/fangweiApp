package com.jacky.lebeauty.support.system;

/**
 * 2018/10/29.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class DeviceDependency {
    public static DeviceDependency current;

    public Device device;
    public String baseUrl;

    public DeviceDependency(Device device, String baseUrl) {
        this.device = device;
        this.baseUrl = baseUrl;
    }
}
