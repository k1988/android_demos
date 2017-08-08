package com.zhaoxiuyuan.demos.envdetect.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author zhaohaiyang
 *         Created at 2017/8/8.
 */
public class BaseReceiver extends BroadcastReceiver {
    private static final String TAG = "BaseReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        logReceivedKeys(TAG, intent);
    }

    protected void logReceivedKeys(final String TAG, Intent intent) {
        String keys = " keys: ";
        for (String key : intent.getExtras().keySet()) {
            keys += key;
            keys += ";";
        }
        Log.d(TAG, "received " + intent.getAction() + keys);
    }
}
