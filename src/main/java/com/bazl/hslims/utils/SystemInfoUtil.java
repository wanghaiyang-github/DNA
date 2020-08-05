package com.bazl.hslims.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SystemInfoUtil {
    public static String getLocalIpAddress() {
        String ip = null;
        try {
            InetAddress ia = InetAddress.getLocalHost();
            ip = ia.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }
}
