package com.uae_barq.uaebarqtasks.app_config;

import android.app.Application;
import android.content.ContextWrapper;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.pixplicity.easyprefs.library.Prefs;

public class AppConfig extends Application {

    private static AppConfig mInstance;

    public static synchronized AppConfig getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the Prefs class
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        mInstance = this;

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
