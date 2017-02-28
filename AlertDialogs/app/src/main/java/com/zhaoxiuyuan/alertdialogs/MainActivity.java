package com.zhaoxiuyuan.alertdialogs;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnProgressDialog)
    Button btnProgressDialog;
    @BindView(R.id.btnDatePickerDialog)
    Button btnDatePickerDialog;
    @BindView(R.id.btnTimePickerDialog)
    Button btnTimePickerDialog;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;

    ProgressDialog progressDialog;
    ProgressDialog progressDialog2;

    //初始化属性
    int progressStart = 0;
    int add = 0;
    final int MESSAEG_PROGRESS_UPDATE = 9527;
    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MESSAEG_PROGRESS_UPDATE) {
                progressDialog2.setProgress(progressStart);
                if (progressStart >= 10000) {
                    progressDialog2.dismiss();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnProgressDialog)
    public void onClick() {
    }

    @OnClick({R.id.btnAlertDialogRadioGroup, R.id.btnAlertDialogCheckGroup, R.id.btnAlertDialog, R.id.btnCustomAlertDialog,R.id.btnCustomDialogFragment})
    public void OnAlertDialogClick(View view) {
        switch (view.getId()) {
            case R.id.btnAlertDialog:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("对话框标题")
                        .setMessage("这是对话框内容")
                        .setNegativeButton("NegativeButton", null)
                        .setPositiveButton("PositiveButton", null)
                        .setNeutralButton("NeutralButton", null)
                        .show();
                break;
            case R.id.btnAlertDialogRadioGroup: {
                final String[] fruits = new String[]{"苹果", "雪梨", "香蕉", "葡萄", "西瓜"};
                AlertDialog alert = null;
                builder = new AlertDialog.Builder(this);
                alert = builder.setIcon(R.mipmap.ic_launcher)
                        .setTitle("选择你喜欢的水果，只能选一个哦~")
                        .setSingleChoiceItems(fruits, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "你选择了" + fruits[which], Toast.LENGTH_SHORT).show();
                            }
                        }).create();
                alert.show();
            }
            break;
            case R.id.btnAlertDialogCheckGroup: {
                final String[] menu = new String[]{"水煮豆腐", "萝卜牛腩", "酱油鸡", "胡椒猪肚鸡"};
                //定义一个用来记录个列表项状态的boolean数组
                final boolean[] checkItems = new boolean[]{false, false, false, false};
                AlertDialog alert = null;
                builder = new AlertDialog.Builder(this);
                alert = builder.setIcon(R.mipmap.ic_launcher)
                        .setMultiChoiceItems(menu, checkItems, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                checkItems[which] = isChecked;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String result = "";
                                for (int i = 0; i < checkItems.length; i++) {
                                    if (checkItems[i])
                                        result += menu[i] + " ";
                                }
                                Toast.makeText(getApplicationContext(), "客官你点了:" + result, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();
                alert.show();
            }
            break;
            case R.id.btnCustomAlertDialog: {
                LayoutInflater layoutInflater = getLayoutInflater();
                View v = layoutInflater.inflate(R.layout.custom_alert, null, false);
                if (v.findViewById(R.id.btnCustom) != null) {
                    v.findViewById(R.id.btnCustom).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "点击了自定义按钮.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "找不到自定义按钮.", Toast.LENGTH_SHORT).show();
                }

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setView(v)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("对话框标题")
                        .setMessage("这是对话框内容,下面这个日历是自定义的View")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确认", null)
                        .create();
                dialog.show();
                Toast.makeText(this, "After custom AlertDialog show.", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.btnCustomDialogFragment:
            {
                // 使用DialogFragment来管理对话框，当旋转屏幕和按下后退键时可以更好的管理其声明周期，它和Fragment有着基本一致的声明周期。且DialogFragment也允许开发者把Dialog作为内嵌的组件进行重用，类似Fragment
                new MyDialogFragment().show(getFragmentManager(),"myDialog");
            }
            break;
        }
    }

    @OnClick({R.id.btnProgressDialog, R.id.btnProgressDialog2, R.id.btnProgressDialog3, R.id.btnDatePickerDialog, R.id.btnTimePickerDialog})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnProgressDialog:
                ProgressDialog.show(this, "这是一个进度条", "进度条是用来显示一个耗时过程的进度的", true, true, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(MainActivity.this, "进度取消", Toast.LENGTH_SHORT).show();
                    }
                }).setProgress(11);
                break;
            case R.id.btnProgressDialog2:
                progressDialog = new ProgressDialog(this);
                //依次设置标题,内容,是否用取消按钮关闭,是否显示进度
                progressDialog.setTitle("软件更新中");
                progressDialog.setMessage("软件正在更新中,请稍后...");
                progressDialog.setCancelable(true);
                //这里是设置进度条的风格,HORIZONTAL是水平进度条,SPINNER是圆形进度条
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setIndeterminate(true);
                //调用show()方法将ProgressDialog显示出来
                progressDialog.show();
                break;
            case R.id.btnProgressDialog3:
                //初始化属性
                progressStart = 0;
                add = 0;
                //依次设置一些属性
                progressDialog2 = new ProgressDialog(MainActivity.this);
                progressDialog2.setMax(10000);
                progressDialog2.setTitle("文件读取中");
                progressDialog2.setMessage("文件加载中,请稍后...");
                //这里设置为不可以通过按取消按钮关闭进度条
                progressDialog2.setCancelable(false);
                progressDialog2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                //这里设置的是是否显示进度,设为false才是显示的哦！
                progressDialog2.setIndeterminate(false);
                progressDialog2.show();
                //这里的话新建一个线程,重写run()方法,
                new Thread() {
                    public void run() {
                        int i = 0;
                        while (progressStart < 10000) {
                            progressStart = 2 * i++;
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            //把信息码发送给handle让更新界面
                            mHandler.sendEmptyMessage(MESSAEG_PROGRESS_UPDATE);
                        }

                        //把信息码发送给handle让更新界面
                        mHandler.sendEmptyMessage(MESSAEG_PROGRESS_UPDATE);
                    }
                }.start();
                break;
            case R.id.btnDatePickerDialog: {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Toast.makeText(MainActivity.this, String.format("%d-%d-%d", year, month, dayOfMonth), Toast.LENGTH_SHORT).show();
                    }
                };

                new DatePickerDialog(MainActivity.this, onDateSetListener, 2017, 2, 17).show();
            }
            break;
            case R.id.btnTimePickerDialog:
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Toast.makeText(MainActivity.this, String.format("%d:%d", hourOfDay, minute), Toast.LENGTH_SHORT).show();
                    }
                }, 10, 20, false).show();
                break;
        }
    }
}
