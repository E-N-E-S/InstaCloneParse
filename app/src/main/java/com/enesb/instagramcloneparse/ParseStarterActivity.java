package com.enesb.instagramcloneparse;

import android.app.Application;

import com.parse.Parse;

public class ParseStarterActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("YOUR-APPLICATION-ID-HERE")
                .clientKey("YOUR-CLIENT-KEY-HERE")
                .server("https://parseapi.back4app.com/")
                .build()
        );

    }
}
