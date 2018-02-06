package com.zhaoxiuyuan.demos.ndk_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.gl2jni.GL2JNIActivity;
import com.android.gles3jni.GLES3JNIActivity;
import com.example.hellojni.HelloJni;

public class NdkMainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    Intent intent = new Intent(NdkMainActivity.this, HelloJni.class);
                    startActivity(intent);
                    mTextMessage.setText(R.string.title_home);
                }
                return true;
                case R.id.navigation_dashboard: {
                    Intent intent = new Intent(NdkMainActivity.this, GL2JNIActivity.class);
                    startActivity(intent);
                    mTextMessage.setText(R.string.title_dashboard);
                }
                return true;
                case R.id.navigation_notifications:
                    Intent intent = new Intent(NdkMainActivity.this, GLES3JNIActivity.class);
                    startActivity(intent);
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
