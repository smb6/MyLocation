package com.froggyzz.mylocation;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;


/**
 * Created by Pablo on 03/3/2015.
 */
public class MyApplication extends Application {

    private static final String TAG = "DEBUG-->";
    private final static String APP_ID = "SjVlzlRWBFBku8kzhOxdVGnw9Gkb291aqmqYfE0X";
    private final static String CLIENT_ID = "WxDukMgWtuWywRXgg7wlKtbQiKg21OU0nJzGZ4sZ";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "MyApplication.onCreate");
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, APP_ID, CLIENT_ID);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "MyApplication.onTerminate");
    }
}
