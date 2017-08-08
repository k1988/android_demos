package com.zhaoxiuyuan.demos.envdetect.receivers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.zhaoxiuyuan.demos.envdetect.BuildConfig;

/**
 * Wifi的任何一个属性发生变化时都会触发{@link WifiManager#NETWORK_STATE_CHANGED_ACTION}广播，包括不限于：
 * <ol>
 * <li>wifi切换到另一个wifi</li>
 * <li>ip地址变化</li>
 * <li>其它wifi属性变化</li>
 * </ol>
 * @author zhaohaiyang
 *         Created at 2017/8/8.
 */
public class WifiStateChangedReceiver extends BaseReceiver {
    private static final String TAG = "WifiStateChangedReceiver";

    @SuppressLint("LongLogTag")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (BuildConfig.DEBUG && !intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            throw new RuntimeException("action not expect!");
        }

        NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        Log.d(TAG, "networkinfo: " + networkInfo);
        if (networkInfo != null && networkInfo.isConnected()) {
            String bssid = intent.getStringExtra(WifiManager.EXTRA_BSSID);
            WifiInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
            Log.d(TAG, "bssid: " + bssid);
            Log.d(TAG, "wifiInfo: " + wifiInfo);
        }
    }
}
