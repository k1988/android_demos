package com.zhaoxiuyuan.demos.flavortest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.zhaoxiuyuan.demos.BuildTypeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.tvHello)
    TextView mTvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTvHello.setText(BuildTypeUtils.Test());
    }
}
