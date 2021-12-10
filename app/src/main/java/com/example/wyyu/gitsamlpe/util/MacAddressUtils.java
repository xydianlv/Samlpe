package com.example.wyyu.gitsamlpe.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;

import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.application.AppController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class MacAddressUtils {

    public static String getMacAddress() {
        String macAddress;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            macAddress = getMacAddressByIP();
            if (TextUtils.isEmpty(macAddress)) {
                macAddress = getMachineHardwareAddress();
            }
            if (TextUtils.isEmpty(macAddress)) {
                macAddress = getLocalMacAddressFromBusyBox();
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            macAddress = getMacAddressBetweenSS();
        } else {
            macAddress = getMacAddressUnderSix();
            if (TextUtils.isEmpty(macAddress)) {
                macAddress = getMacAddressBetweenSS();
            }
        }
        return macAddress;
    }

    /**
     * Android7.0 及以上，根据 IP 获取 MAC 地址的方法
     * 需要权限 android.permission.INTERNET
     *
     * @return MAC 地址
     */
    public static String getMacAddressByIP() {
        String macAddress = "";
        try {
            InetAddress ip = getLocalInetAddress();
            byte[] byteArray = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuilder stringBuilder = new StringBuilder();
            for (int index = 0; index < byteArray.length; index++) {
                if (index != 0) {
                    stringBuilder.append(":");
                }
                String str = Integer.toHexString(byteArray[index] & 0xFF);
                stringBuilder.append(str.length() == 1 ? 0 + str : str);
            }
            macAddress = stringBuilder.toString().toUpperCase();
        } catch (Exception exception) {
            ULog.show(exception.getMessage());
        }
        return macAddress;
    }

    private static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            Enumeration<NetworkInterface> networkInterfaceEnum = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaceEnum.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaceEnum.nextElement();
                Enumeration<InetAddress> inetAddressEnum = networkInterface.getInetAddresses();
                while (inetAddressEnum.hasMoreElements()) {
                    ip = inetAddressEnum.nextElement();
                    if (ip != null && ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {
                        break;
                    }
                }
                if (ip != null) {
                    break;
                }
            }
        } catch (Exception exception) {
            ULog.show(exception.getMessage());
        }
        return ip;
    }

    /**
     * Android7.0 及以上，扫描各个网络接口获得 Mac 地址
     * 需要权限 android.permission.INTERNET
     *
     * @return MAC 地址
     */
    public static String getMachineHardwareAddress() {
        Enumeration<NetworkInterface> networkInterfaceEnum = null;
        try {
            networkInterfaceEnum = NetworkInterface.getNetworkInterfaces();
        } catch (Exception e) {
            ULog.show(e.getMessage());
        }
        if (networkInterfaceEnum == null) {
            return null;
        }
        NetworkInterface networkInterface;
        String hardwareAddress = null;
        while (networkInterfaceEnum.hasMoreElements()) {
            networkInterface = networkInterfaceEnum.nextElement();
            if (networkInterface == null) {
                continue;
            }
            try {
                hardwareAddress = byteToString(networkInterface.getHardwareAddress());
                if (hardwareAddress != null) {
                    break;
                }
            } catch (Exception e) {
                ULog.show(e.getMessage());
            }
        }
        return hardwareAddress;
    }

    private static String byteToString(byte[] byteArray) {
        if (byteArray == null || byteArray.length <= 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : byteArray) {
            stringBuilder.append(String.format("%02X", b));
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    /**
     * Android7.0 及以上，通过 busyBox 获取本地存储的 MAC 地址
     *
     * @return MAC 地址
     */
    public static String getLocalMacAddressFromBusyBox() {
        StringBuilder result = new StringBuilder();
        String cmd = "busybox ifconfig";
        String line;
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null && !line.contains("HWaddr")) {
                result.append(line);
            }
        } catch (Exception e) {
            ULog.show(e.getMessage());
        }
        String resultStr = result.toString();
        if (TextUtils.isEmpty(resultStr)) {
            return "网络异常";
        }
        if (resultStr.length() > 0 && resultStr.contains("HWaddr")) {
            return resultStr.substring(resultStr.indexOf("HWaddr") + 6, resultStr.length() - 1);
        } else {
            return null;
        }
    }

    /**
     * Android6.0 以上及  Android7.0 以下，获得 MAC 地址
     *
     * @return MAC 地址
     */
    public static String getMacAddressBetweenSS() {
        String macAddress = "";
        try {
            Process process = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            LineNumberReader lineNumberReader = new LineNumberReader(inputStreamReader);

            while (macAddress != null) {
                macAddress = lineNumberReader.readLine();
                if (!TextUtils.isEmpty(macAddress)) {
                    macAddress = macAddress.trim();
                    break;
                }
            }
        } catch (Exception e) {
            ULog.show(e.getMessage());
        }
        if (TextUtils.isEmpty(macAddress)) {
            try {
                FileReader fileReader = new FileReader("/sys/class/net/eth0/address");
                StringBuilder stringBuilder = new StringBuilder();
                char[] buffer = new char[4096];
                int readLength = fileReader.read(buffer);
                while (readLength >= 0) {
                    stringBuilder.append(buffer, 0, readLength);
                    readLength = fileReader.read(buffer);
                }
                fileReader.close();
                macAddress = stringBuilder.toString();
                if (!TextUtils.isEmpty(macAddress)) {
                    macAddress = macAddress.toUpperCase().substring(0, 7);
                }
            } catch (Exception e) {
                ULog.show(e.getMessage());
            }
        }
        return macAddress;
    }

    /**
     * Android6.0 以下，获得MAC地址
     *
     * @return MAC 地址
     */
    @SuppressLint("HardwareIds")
    public static String getMacAddressUnderSix() {
        Context context = AppController.getAppContext();
        if (isAccessWifiStateAuthorized(context)) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            try {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                return wifiInfo.getMacAddress();
            } catch (Exception e) {
                ULog.show(e.getMessage());
            }
        }
        return null;
    }

    private static boolean isAccessWifiStateAuthorized(Context context) {
        if (PackageManager.PERMISSION_GRANTED == context.checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE")) {
            ULog.show("access wifi state is enabled");
            return true;
        } else {
            return false;
        }
    }
}
