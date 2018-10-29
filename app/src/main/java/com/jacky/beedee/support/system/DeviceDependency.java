package com.jacky.beedee.support.system;

/**
 * 2018/10/29.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class DeviceDependency {
    public static DeviceDependency current;

    public Device device;

    public DeviceDependency(Device device) {
        this.device = device;
    }
}
