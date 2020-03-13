package com.uae_barq.uaebarqtasks.java.app_config;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class AppConfig extends Application {

    private static AppConfig mInstance;


    /**
     * Synchronized methods enable a simple strategy for preventing thread interference and memory
     * consistency errors: if an object is visible to more
     * than one thread,
     * all reads or writes to that object's variables are done through synchronized methods.
     * (An important exception: final fields, which cannot be modified after the object is constructed,
     * can be safely read through non-synchronized methods, once the object is constructed) This strategy
     * is effective,
     *
     * @return
     */
    public static synchronized AppConfig getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Installing crash report
        Fabric.with(this, new Crashlytics());

        mInstance = this;

    }
}
