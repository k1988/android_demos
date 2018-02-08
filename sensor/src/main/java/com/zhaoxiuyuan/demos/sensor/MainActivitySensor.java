package com.zhaoxiuyuan.demos.sensor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivitySensor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sensor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        showAllSensor();
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
        getMenuInflater().inflate(R.menu.main_activity_sensor, menu);
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(this, AccelerometerActivity.class));
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

    private String getSensorName(int type){
        switch (type){
            case Sensor.TYPE_ACCELEROMETER: return "加速度传感器";
            case Sensor.TYPE_MAGNETIC_FIELD: return "磁力传感器";
            case Sensor.TYPE_ORIENTATION: return "方向传感器(已弃用)";
            case Sensor.TYPE_GYROSCOPE: return "陀螺仪传感器";
            case Sensor.TYPE_LIGHT: return "光线感应传感器";
            case Sensor.TYPE_PRESSURE: return "压力传感器";
            case Sensor.TYPE_TEMPERATURE: return "温度传感器(已弃用)";
            case Sensor.TYPE_PROXIMITY: return "距离传感器";
            case Sensor.TYPE_GRAVITY: return "重力传感器";
            case Sensor.TYPE_LINEAR_ACCELERATION: return "线性加速度传感器";
            case Sensor.TYPE_ROTATION_VECTOR: return "旋转矢量传感器";
            case Sensor.TYPE_RELATIVE_HUMIDITY: return "湿度传感器";
            case Sensor.TYPE_AMBIENT_TEMPERATURE: return "温度传感器";
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED: return "未校准磁力传感器";
            case Sensor.TYPE_GAME_ROTATION_VECTOR: return "游戏动作传感器";
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED: return "未校准陀螺仪传感器";
            case Sensor.TYPE_SIGNIFICANT_MOTION: return "特殊动作触发传感器";
            case Sensor.TYPE_STEP_DETECTOR: return "步行检测传感器";
            case Sensor.TYPE_STEP_COUNTER: return "计步传感器";
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR: return "地磁旋转矢量传感器";
            case 10002: return "huawei hall sensor 霍尔传感器";
        }
        return "未知";
    }

    private void showAllSensor(){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linerLayout);

        //获得Sensor管理器对象
        SensorManager sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        //获得所有类型的Sensor，并装进一个列表里
        List<Sensor> allSensors = sm.getSensorList(Sensor.TYPE_ALL);
        int n = 1;
        boolean b = false;
        //遍历Sensor列表
        for(Sensor s : allSensors)
        {
            //创建一个新的TextView用来显示Sensor信息
            TextView tvSensor = new TextView(this);
            //设置Sensor设置的内容
            tvSensor.setText(n+"    Name: " + s.getName() + "\n Vendor:" + s.getVendor() + "\n  Type:" + s.getType() + "\n Chinese Name: " + getSensorName(s.getType()));
            //将些TextView添加到LinearLayout中
            linearLayout.addView(tvSensor);
            //将n加1
            n++;
            //相邻的两个Sensor文字颜色不一样,方便查看.
            if(b)
            {
                tvSensor.setTextColor(Color.BLUE);
                b= false;
            }
            else
            {
                tvSensor.setTextColor(Color.BLACK);
                b = true;
            }
        }
    }
}
