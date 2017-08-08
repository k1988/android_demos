package com.zhaoxiuyuan.demos.envdetect.receivers;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * 在wifi开关发生变化时触发广播{@link WifiManager#WIFI_STATE_CHANGED_ACTION},如果是关闭和打开wifi开关则见{@link WifiStateChangedReceiver}
 * @author zhaohaiyang
 *         Created at 2017/8/7.
 */
public class WifiChangeReceiver extends BaseReceiver {
    private static final String TAG = "WifiChangeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {//这个监听wifi的打开与关闭，与wifi的连接无关

            Log.v(TAG, "收到WIFI_STATE_CHANGED_ACTION");
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1111);
            int preWifiState = intent.getIntExtra(WifiManager.EXTRA_PREVIOUS_WIFI_STATE, -1111);

            String[] states = {
                    "WifiManager.WIFI_STATE_DISABLED", "WifiManager.WIFI_STATE_DISABLING", "WifiManager.WIFI_STATE_ENABLED", "WifiManager.WIFI_STATE_ENABLING", "WifiManager.WIFI_STATE_UNKNOWN"
            };
            WifiManager connectivityManager =  (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            Log.d(TAG, "isDataWIFIUp: " + isDataWIFIUp(context) + " isWifiEnabled " + connectivityManager.isWifiEnabled());

            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    Log.v(TAG, "收到" + "WIFI_STATE_DISABLED 前一状态：" + states[preWifiState]);
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    Log.v(TAG, "收到" + "WIFI_STATE_DISABLING 前一状态：" + states[preWifiState]);
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    Log.v(TAG, "收到" + "WIFI_STATE_ENABLED 前一状态：" + states[preWifiState]);
                    break;
                case WifiManager.WIFI_STATE_ENABLING:
                    Log.v(TAG, "收到" + "WIFI_STATE_ENABLING 前一状态：" + states[preWifiState]);
                    break;
                case WifiManager.WIFI_STATE_UNKNOWN:
                    Log.v(TAG, "收到 WIFI_STATE_UNKNOWN 前一状态：" + states[preWifiState]);
            }
        }

    }

    public boolean isDataWIFIUp(Context context) {
        ConnectivityManager connectivityManager =  (ConnectivityManager) context.getSystemService(Application.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}
