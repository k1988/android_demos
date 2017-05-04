package com.zhaoxiuyuan.demos.installevent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;

/**
 * @author zhaohaiyang
 *         Created at 2017/5/4.
 */
class InstallEventReceiver extends BroadcastReceiver {
    MainActivity mContext;

    public HashMap<String, Boolean> getmPackagesInstallResult() {
        return mPackagesInstallResult;
    }

    HashMap<String, Boolean> mPackagesInstallResult;
    boolean mRecording = false;

    public InstallEventReceiver(MainActivity activity) {
        super();
        mContext = activity;
        mPackagesInstallResult = new HashMap<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("action", action + "");
        if (action.equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            Log.d("Install", "packageName:" + packageName.substring(8) + "");
            mContext.showToast("Install " + packageName.substring(8));
            if (mRecording) {
                mPackagesInstallResult.put(packageName, true);
            }
        } else if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            Log.d("removed", "packageName:" + packageName.substring(8) + "");
            mContext.showToast("removed " + packageName.substring(8));
            if (mRecording) {
                mPackagesInstallResult.put(packageName, false);
            }
        }
    }

    public void beginRecord() {
        mPackagesInstallResult.clear();
        mRecording = true;
    }

    public void endRecord() {
        mRecording = false;
    }
}
