package com.zhaoxiuyuan.demos.layouts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.buttonTabLayout, R.id.buttonLinearLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonTabLayout: {
                Intent intent = new Intent(this, TabLayoutActivity.class);
                intent.setPackage(getPackageName());
                startActivity(intent);
            }
            break;
            case R.id.buttonLinearLayout: {
                Intent intent = new Intent(this, LinearLayoutActivity.class);
                intent.setPackage(getPackageName());
                startActivity(intent);
            }
            break;
        }
    }
}
