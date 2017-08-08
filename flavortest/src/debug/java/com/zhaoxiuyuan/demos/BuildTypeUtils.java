package com.zhaoxiuyuan.demos;

public class BuildTypeUtils {
    private static final String TAG = "BuildTypeUtils-debug";

    public static String Test() {
        android.util.Log.d(TAG, "Test() called");
        return TAG;
    }
}
