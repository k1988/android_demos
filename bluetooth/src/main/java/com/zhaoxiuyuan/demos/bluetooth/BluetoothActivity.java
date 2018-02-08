package com.zhaoxiuyuan.demos.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.zhaoxiuyuan.demos.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BluetoothActivity extends BaseActivity {

    @BindView(R.id.button)
    Button mButtonScan;
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        ButterKnife.bind(this);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            Toast.makeText(this, "设备不支持蓝牙", Toast.LENGTH_SHORT).show();
            finish();
        } else if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "设备不支持蓝牙4.0", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();  //获取蓝牙适配器
        if (mBluetoothAdapter != null) {  //有蓝牙功能
                /*
                 * 获取与profile关联的profile代理，并实现BluetoothProfile.ServiceListener监听的两个方法
                 * 从而实现设备检测功能
                 */
            mBluetoothAdapter.getProfileProxy(this, new BluetoothProfile.ServiceListener() {
                @Override
                public void onServiceDisconnected(int profile) {
                    showToast("onServiceDisconnected");
                }

                @Override
                public void onServiceConnected(int profile, BluetoothProfile proxy) {
                    showToast("onServiceConnected");
                    List<BluetoothDevice> devices = proxy.getConnectedDevices();
                    for (BluetoothDevice d : devices) {
                        String name = d.getName();
                        showToast("connected name = " + name);
                    }
                }
            }, 4);  //4即 BluetoothProfile.INPUT_DEVICE的值，, 因为 是@hide状态，故此处只能显示写为对应的整型

            if (!mBluetoothAdapter.isEnabled()) {  //蓝牙未开启
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mBluetoothAdapter.enable();  //开启蓝牙（还有一种方法开启，我就不说了，自己查去）
                    }
                }).start();
            } else {
                if (!mBluetoothAdapter.isDiscovering()) {  //如果没有在扫描设备
                    mBluetoothAdapter.startDiscovery();//扫描附近蓝牙设备，然后做接下来的操作，比如扫描附近蓝牙等
                } else {
                    showToast("正在扫描");  //弹出Toast提示
                }
            }
        } else {  //无蓝牙功能
            showToast("当前设备未找到蓝牙功能");  //弹出Toast提示
        }
    }
}
