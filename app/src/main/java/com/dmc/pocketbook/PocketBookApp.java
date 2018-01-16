package com.dmc.pocketbook;

import android.app.Application;

import com.dmc.pocketbook.dagger.AppModule;
import com.dmc.pocketbook.dagger.DaggerAppComponent;
import com.dmc.pocketbook.dagger.AppComponent;
import com.dmc.pocketbook.dagger.DataModule;

/**
 * Created by chi on 16/1/18.
 */

public class PocketBookApp extends Application {
    private static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule("https://api.pocketbook.com.au", this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }
}
