package com.zhaoxiuyuan.demos.base;

import android.app.Activity;
import android.widget.Toast;

/**
 * @author zhaohaiyang
 *         Created at 2018/2/8.
 */
public class BaseActivity extends Activity {
    protected void showToast(final String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
