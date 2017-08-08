package com.zhaoxiuyuan.demos.envdetect.receivers;

import android.content.Context;
import android.content.Intent;

/**
 * @author zhaohaiyang
 *         Created at 2017/8/8.
 */
public class AllBroadcastReceiver extends BaseReceiver {
    private static final String TAG = "AllBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        logReceivedKeys(TAG, intent);
    }
}
