package com.app.imaggallery;

import android.app.Application;
import android.content.Context;

public class AppApplication extends Application {

    public static AppApplication appInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
