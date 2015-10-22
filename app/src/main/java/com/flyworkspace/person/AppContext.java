package com.flyworkspace.person;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by jinpengfei on 15-10-21.
 */
public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
    }
}
