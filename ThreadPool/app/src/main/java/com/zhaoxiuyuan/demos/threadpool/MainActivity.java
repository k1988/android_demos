package com.zhaoxiuyuan.demos.threadpool;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView mTextMessage;
    ExecutorService poolHome = ThreadPool.newThreadPool("Home", 5);

    // 使用core线程为n + SynchronousQueue的效果是，同时最多有maximumPoolSize个任务
    // 当execute的数量超过maximumPoolSize个时，会抛出异常
    ExecutorService pool2 = new ThreadPoolExecutor(2, 5,
                                60L, TimeUnit.SECONDS,
                                new SynchronousQueue<Runnable>());

    // 使用corePoolSize + LinkedBlockingQueue的效果是，最多同时有corePoolSize个线程。
    // 如果LinkedBlockingQueue是有个数限制的，则最多同时有maximumPoolSize个线程。剩余  等待中的线程不能超过个数限制，否则上会抛出异常
    ExecutorService pool3 = new ThreadPool("3", 2, 5, 10, new LinkedBlockingQueue<Runnable>(), false);

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    createThreads("home", 10, MainActivity.this.poolHome);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    createThreads("Dashboard", 10, MainActivity.this.pool2);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    createThreads("Notifications", 10, MainActivity.this.pool3);
                    return true;
            }
            return false;
        }

    };

    private void createThreads(final String tag, int num, ExecutorService pool) {
        for (int i = 0; i < num; i++) {
            final int finalI = i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, String.format("run: %s thread %d started", tag, finalI));
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, String.format("run: %s thread %d ended", tag, finalI));
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
