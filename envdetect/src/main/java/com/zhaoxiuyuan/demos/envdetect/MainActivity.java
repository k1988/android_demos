package com.zhaoxiuyuan.demos.envdetect;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Thread t = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                builder.append("getPackageName: ").append(MainActivity.this.getPackageName()).append("\n");
                builder.append("getPackageResourcePath: ").append(MainActivity.this.getPackageResourcePath()).append("\n");
                builder.append("getPackageCodePath: ").append(MainActivity.this.getPackageCodePath()).append("\n\n");

                builder.append("getCacheDir: ").append(MainActivity.this.getCacheDir().getPath()).append("\n");
                builder.append("getCodeCacheDir: ").append(MainActivity.this.getCodeCacheDir().getPath()).append("\n");

                //  builder.append("getDataDir: ").append(MainActivity.this.getDataDir() != null?MainActivity.this.getDataDir().getPath():"null").append("\n");
                builder.append("getFilesDir: ").append(MainActivity.this.getFilesDir().getPath()).append("\n");
                builder.append("getDatabasePath(test): ").append(MainActivity.this.getDatabasePath("test").getPath()).append("\n");
                builder.append("getNoBackupFilesDir: ").append(MainActivity.this.getNoBackupFilesDir().getPath()).append("\n\n");

                builder.append("getExternalFilesDir(null): ").append(MainActivity.this.getExternalFilesDir(null).getPath()).append("\n");
                builder.append("getExternalCacheDir(): ").append(MainActivity.this.getExternalCacheDir().getPath()).append("\n");
                builder.append("getObbDir: ").append(MainActivity.this.getObbDir().getPath()).append("\n\n");

                builder.append("\tEnvironment:\n");
                builder.append("getDataDirectory(): ").append(Environment.getDataDirectory().getPath()).append("\n");
                builder.append("getDownloadCacheDirectory(): ").append(Environment.getDownloadCacheDirectory().getPath()).append("\n");
                builder.append("getExternalStorageDirectory(): ").append(Environment.getExternalStorageDirectory().getPath()).append("\n");
                builder.append("getExternalStorageState(): ").append(Environment.getExternalStorageState()).append("\n");
                builder.append("getRootDirectory(): ").append(Environment.getRootDirectory().getPath()).append("\n");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String text = builder.toString();
                        mTextView.setText(text);
                        Log.d("info", text);
                    }
                });
            }
        });
        t.setDaemon(true);
        t.run();
    }
}
