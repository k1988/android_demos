package com.zhaoxiuyuan.demos.proguard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    @InjectView(R.id.fab)
    FloatingActionButton fab;

    long mClickCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.testLog("final", "normal");
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.d(TAG, "onNavigationItemSelected() called with: item = [" + item.toString() + "]");// 字符串 + 函数参数，proguard移除日志，但会留下字符串拼接
        Log.d(TAG, "onNavigationItemSelected() called with: item = []");
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @NonNull
    public void testLog(final String finalArg, String normalArg) {
        mClickCount++;
        int count = 2;
        Log.d(TAG, "onClick() called"); // 纯字符串，proguard移除日志及字符串
        count++;
        Log.e(TAG, "no implements"); // 纯字符串，proguard移除日志及字符串
        Log.d(TAG, fab.toString()); //  成员变量，proguard移除日志
        Log.v(TAG, "the local var click count is " + count); // 字符串 + 局部变量，proguard移除日志和字符串拼接
        Log.i(TAG, Logger.getCallStack(5)); // 函数返回值，proguard移除日志和函数调用
        Log.i(TAG, "the final arg " + finalArg); // 字符串 + 字符串参数，proguard移除日志和字符串拼接
        Log.i(TAG, "the normal arg " + normalArg); // 字符串 + 字符串参数，proguard移除日志和字符串拼接

        Log.v(TAG, "the member var click count is " + mClickCount); // 字符串 + 成员变量，proguard移除日志，但会留下字符串拼接
        Log.i(TAG, "now is " + getNowTime()); // 字符串 + 函数返回值，proguard移除日志，但会留下函数调用和字符串拼接
        count++;
        if (!BuildConfig.DEBUG) {
            Log.v(TAG, "in condition the member var click count is " + mClickCount); // 字符串 + 成员变量，proguard移除日志，但会留下字符串拼接
        }
        Toast.makeText(this, "click" + count, Toast.LENGTH_SHORT).show();
    }

    private String getNowTime() {
        return (new Date()).toString();
    }
}
