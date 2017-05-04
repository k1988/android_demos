package com.zhaoxiuyuan.demos.installevent;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 0;

    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.buttonJumpSecond)
    Button mButtonJumpSecond;
    @BindView(R.id.checkBoxUnknownSource)
    CheckBox mCheckBoxUnknownSource;
    @BindView(R.id.checkBoxAllowReplace)
    CheckBox mCheckBoxAllowReplace;
    @BindView(R.id.checkBoxReturnResult)
    CheckBox mCheckBoxReturnResult;
    @BindView(R.id.checkBoxFlagNewTask)
    CheckBox mCheckBoxFlagNewTask;
    @BindView(R.id.checkBoxStartForResult)
    CheckBox mCheckBoxStartForResult;
    private InstallEventReceiver installReceiver;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        showToast(String.format("Install onActivityResult(%d,%d,%s)", requestCode, resultCode, data != null ? data.toString() : "null"));

        // resultCode都是0，跟安装任务的参数有关
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 9527:
                installReceiver.endRecord();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //注册安装卸载广播
        installReceiver = new InstallEventReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        registerReceiver(installReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(installReceiver);
    }

    @OnClick(R.id.button)
    public void onClick() {
        File downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!downloadFolder.exists()) {
            if (!downloadFolder.mkdirs()) {
                showToast("下载目录创建失败!");
                return;
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            showToast("动态申请权限!");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            return;
        }

        File[] files = downloadFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                Log.d("Find", dir.getPath() + name);
                return name.equals("test.apk");
            }
        });

        if (files == null || files.length == 0) {
            showToast("没有找到apk文件，请将test.apk放在" + downloadFolder.getAbsolutePath());
            return;
        }

        Intent install = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        install.setDataAndType(Uri.fromFile(files[0]), "application/vnd.android.package-archive");

        // 此标志会让startActivityForResult对应的OnActivityResult立即返回，并且resultCode为0
        // 也会新增一个窗口栈
        if (mCheckBoxFlagNewTask.isChecked()) {
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        // 加上此方法，会使startActivityForResult对应的OnActivityResult返回正确的resultCode
        // 但是在安装完成后没有提供用户点击“完成”和“打开”的那个界面，而是直接返回此界面
        if (mCheckBoxReturnResult.isChecked()) {
            install.putExtra(Intent.EXTRA_RETURN_RESULT, true);
        }

        if (mCheckBoxAllowReplace.isChecked()) {
            install.putExtra(Intent.EXTRA_ALLOW_REPLACE, true);
        }

        if (mCheckBoxUnknownSource.isChecked()) {
            install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
        }

        installReceiver.beginRecord();
        if (mCheckBoxStartForResult.isChecked()) {
            startActivityForResult(install, 9527);
        } else {
            startActivity(install);
        }
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.i("Toast", msg);
    }

    @OnClick(R.id.buttonJumpSecond)
    public void onClickJump() {
        Intent intent = new Intent();
        intent.setClass(this, SecondActivity.class);
        startActivity(intent);
    }
}
