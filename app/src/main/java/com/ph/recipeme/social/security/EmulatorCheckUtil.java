package com.ph.recipeme.social.security;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.content.pm.PackageManager;

import java.net.InetAddress;

public class EmulatorCheckUtil {

    // Method to check emulator based on device properties
    public static boolean isEmulator() {
        String brand = Build.BRAND;
        String device = Build.DEVICE;
        String model = Build.MODEL;
        String product = Build.PRODUCT;

        return (Build.FINGERPRINT.startsWith("generic")
                || brand.contains("generic")
                || device.contains("generic")
                || model.contains("google_sdk")
                || model.contains("Emulator")
                || model.contains("Android SDK built for x86")
                || product.contains("sdk_gphone")
                || product.contains("sdk"));
    }

    // Method to check for telephony features (SIM card)
    public static boolean hasTelephonyFeature(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && !tm.getNetworkOperatorName().toLowerCase().equals("android");
    }

    // Method to check hardware features like gyroscope
    public static boolean hasGyroscopeFeature(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE);
    }

    // Method to check for emulator IP
    public static boolean isEmulatorNetwork() {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            return ip.equals("10.0.2.15") || ip.startsWith("10.0.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Combined method to check if device is likely an emulator
    public static boolean isLikelyEmulator(Context context) {
        return isEmulator() || !hasTelephonyFeature(context) || !hasGyroscopeFeature(context) || isEmulatorNetwork();
    }
}
