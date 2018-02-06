package com.zhaoxiuyuan.demos.envdetect;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhaoxiuyuan.demos.envdetect.receivers.WifiStateChangedReceiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.button_detect_path)
    Button mButtonDetectPath;
    @BindView(R.id.button_detect_net)
    Button mButtonDetectNet;

    WifiStateChangedReceiver mWifiStateChangedReceiver = new WifiStateChangedReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
        detectPath();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(mWifiStateChangedReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        this.registerReceiver(mWifiStateChangedReceiver, intentFilter);
    }

    private void detectPath() {
        Thread t = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                builder.append("getPackageName: ").append(MainActivity.this.getPackageName()).append("\n");
                builder.append("getPackageResourcePath: ").append(MainActivity.this.getPackageResourcePath()).append("\n");
                builder.append("getPackageCodePath: ").append(MainActivity.this.getPackageCodePath()).append("\n\n");

                builder.append("getCacheDir: ").append(MainActivity.this.getCacheDir().getPath()).append("\n");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.append("getCodeCacheDir: ").append(MainActivity.this.getCodeCacheDir().getPath()).append("\n");
                }

                //  builder.append("getDataDir: ").append(MainActivity.this.getDataDir() != null?MainActivity.this.getDataDir().getPath():"null").append("\n");
                builder.append("getFilesDir: ").append(MainActivity.this.getFilesDir().getPath()).append("\n");
                builder.append("getDatabasePath(test): ").append(MainActivity.this.getDatabasePath("test").getPath()).append("\n");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.append("getNoBackupFilesDir: ").append(MainActivity.this.getNoBackupFilesDir().getPath()).append("\n\n");
                }

                builder.append("getExternalFilesDir(null): ").append(MainActivity.this.getExternalFilesDir(null).getPath()).append("\n");
                builder.append("getExternalCacheDir(): ").append(MainActivity.this.getExternalCacheDir().getPath()).append("\n");
                builder.append("getObbDir: ").append(MainActivity.this.getObbDir().getPath()).append("\n\n");

                builder.append("=====> Environment:\n");
                builder.append("getDataDirectory(): ").append(Environment.getDataDirectory().getPath()).append("\n");
                builder.append("getDownloadCacheDirectory(): ").append(Environment.getDownloadCacheDirectory().getPath()).append("\n");
                builder.append("getExternalStorageDirectory(): ").append(Environment.getExternalStorageDirectory().getPath()).append("\n");
                builder.append("getExternalStorageState(): ").append(Environment.getExternalStorageState()).append("\n");
                builder.append("getRootDirectory(): ").append(Environment.getRootDirectory().getPath()).append("\n");

                String text = builder.toString();
                setDetectResult(text);
            }
        });
        t.setDaemon(true);
        t.run();
    }

    private void detectNet() {
        Thread t = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                WifiManager wifiManager = (WifiManager) MainActivity.this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

//                某手机上示例：
//               WifiManager.isWifiEnabled(): true
//               WifiManager.is5GHzBandSupported(): false
//               WifiManager.isP2pSupported(): true
//               WifiManager.isPreferredNetworkOffloadSupported(): false
//               WifiManager.isEnhancedPowerReportingSupported(): true
//               WifiManager.isTdlsSupported(): true
                builder.append("WifiManager.isWifiEnabled(): ").append(wifiManager.isWifiEnabled()).append("\n");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.append("WifiManager.is5GHzBandSupported(): ").append(wifiManager.is5GHzBandSupported()).append("\n");
                    builder.append("WifiManager.isP2pSupported(): ").append(wifiManager.isP2pSupported()).append("\n");
                    builder.append("WifiManager.isPreferredNetworkOffloadSupported(): ").append(wifiManager.isPreferredNetworkOffloadSupported()).append("\n");
                    builder.append("WifiManager.isEnhancedPowerReportingSupported(): ").append(wifiManager.isEnhancedPowerReportingSupported()).append("\n");
                    builder.append("WifiManager.isTdlsSupported(): ").append(wifiManager.isTdlsSupported()).append("\n");
                }
                builder.append("\n");

//               ConnectivityManager.isDefaultNetworkActive(): false
//               ConnectivityManager.isActiveNetworkMetered(): false
//               ConnectivityManager.getActiveNetwork(): 147
//               ConnectivityManager.getActiveNetworkInfo(): [type: WIFI[], state: CONNECTED/CONNECTED, reason: (unspecified), extra: "fanyou_Network", roaming: false, failover: false, isAvailable: true]
//               ConnectivityManager.getBoundNetworkForProcess(): null
//               ConnectivityManager.getDefaultProxy(): null
//               ConnectivityManager.getNetworkCapabilities(): [ Transports: WIFI Capabilities: INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VPN&VALIDATED LinkUpBandwidth>=1048576Kbps LinkDnBandwidth>=1048576Kbps]
                ConnectivityManager connectivityManager = (ConnectivityManager) MainActivity.this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                builder.append("ConnectivityManager.isDefaultNetworkActive(): ").append(connectivityManager.isDefaultNetworkActive()).append("\n");
                builder.append("ConnectivityManager.isActiveNetworkMetered(): ").append(connectivityManager.isActiveNetworkMetered()).append("\n");
                builder.append("ConnectivityManager.getActiveNetwork(): ").append(connectivityManager.getActiveNetwork().toString()).append("\n");
                builder.append("ConnectivityManager.getActiveNetworkInfo(): ").append(dumpNetworkInfo(connectivityManager.getActiveNetworkInfo())).append("\n");
                builder.append("ConnectivityManager.getBoundNetworkForProcess(): ").append(connectivityManager.getBoundNetworkForProcess()).append("\n");
                builder.append("ConnectivityManager.getDefaultProxy(): ").append(connectivityManager.getDefaultProxy()).append("\n");
                builder.append("ConnectivityManager.getNetworkCapabilities(): ").append(connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork())).append("\n");
                String text = builder.toString();
                setDetectResult(text);
            }

            private String dumpNetworkInfo(NetworkInfo networkInfo) {
                return networkInfo.toString();
            }
        });
        t.setDaemon(true);
        t.run();
    }

    private void detectSystem() {
        Thread t = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                Map<String, String> envs = System.getenv();

                builder.append("System.currentTimeMillis(): ").append(System.currentTimeMillis()).append("\n\n");

                builder.append("=====> cat /proc/version: \n");
                builder.append(getProcVersion()).append("\n\n");

                // android.os.Build.xxx
                builder.append("=====> android.os.Build.xxx: \n");
                String phoneInfo = "Product: " + android.os.Build.PRODUCT;
                phoneInfo += "\ncpu_abi: " + android.os.Build.CPU_ABI;
				phoneInfo += "\nsupport_abi: " + getAbi();
                phoneInfo += "\ntags: " + android.os.Build.TAGS;
                phoneInfo += "\nversion_codes.base: " + android.os.Build.VERSION_CODES.BASE;
                phoneInfo += "\nmodel: " + android.os.Build.MODEL;
                phoneInfo += "\nsdk: " + android.os.Build.VERSION.SDK;
                phoneInfo += "\nversion.release: " + android.os.Build.VERSION.RELEASE;
                phoneInfo += "\ndevice: " + android.os.Build.DEVICE;
                phoneInfo += "\ndisplay: " + android.os.Build.DISPLAY;
                phoneInfo += "\nbrand: " + android.os.Build.BRAND;
                phoneInfo += "\nboard: " + android.os.Build.BOARD;
                phoneInfo += "\nfingerprint: " + android.os.Build.FINGERPRINT;
                phoneInfo += "\nid: " + android.os.Build.ID;
                phoneInfo += "\nmanufacturer: " + android.os.Build.MANUFACTURER;
                phoneInfo += "\nuser: " + android.os.Build.USER;
                builder.append(phoneInfo);
                builder.append("\n");

                builder.append("=====> System.getProperties()\n");
                Properties properties = System.getProperties();
                for (Map.Entry<Object, Object> e : properties.entrySet()) {
                    builder.append(e.getKey().toString().toLowerCase()).append(" : ").append(e.getValue()).append("\n");
                }
                builder.append("\n");

                builder.append("=====> 环境变量\n");
                for (Map.Entry<String, String> e : envs.entrySet()) {
                    builder.append(e.getKey().toLowerCase()).append(" : ").append(e.getValue()).append("\n");
                }
                builder.append("\n");

                String text = builder.toString();
                setDetectResult(text);
            }
        });
        t.setDaemon(true);
        t.run();
    }

    private String getAbi() {
        String[] abis = new String[]{};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            abis = Build.SUPPORTED_ABIS;
        } else {
            abis = new String[]{Build.CPU_ABI, Build.CPU_ABI2};
        }
        StringBuilder abiStr = new StringBuilder();
        for (String abi : abis) {
            abiStr.append(abi);
            abiStr.append(',');
        }
        return abiStr.toString();
    }

    void setDetectResult(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText(text);
                Log.i("info", text);
            }
        });
    }

    @OnClick({R.id.button_detect_path, R.id.button_detect_net, R.id.button_detect_system})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_detect_path:
                detectPath();
                break;
            case R.id.button_detect_net:
                detectNet();
                break;
            case R.id.button_detect_system:
                detectSystem();
                break;
        }
    }

    public static String getProcVersion() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("cat /proc/version");
        } catch (IOException e) {
            Log.e(TAG, "read /proc/version failed", e);
            return "";
        }

        // get the output line
        InputStream outs = process.getInputStream();
        InputStreamReader isrout = new InputStreamReader(outs);
        BufferedReader brout = new BufferedReader(isrout, 8 * 1024);

        String result = "";
        String line;
        // get the whole standard output string
        try {
            while ((line = brout.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            Log.e(TAG, "read /proc/version failed", e);
            return "";
        }
        return result;
    }

    /**
     * INNER-VER
     * 内部版本
     * return String
     */

    public static String getInner_Ver() {
        String ver = "";

        if (android.os.Build.DISPLAY.contains(android.os.Build.VERSION.INCREMENTAL)) {
            ver = android.os.Build.DISPLAY;
        } else {
            ver = android.os.Build.VERSION.INCREMENTAL;
        }
        return ver;

    }
}
