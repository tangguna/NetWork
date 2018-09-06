package com.cyhc.network.network;

import android.app.Application;

import com.cyhc.network.library.net.RequestClient;

public class Myapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RequestClient.init(getApplicationContext(),"http://api.douban.com/v2/movie/",null);

    }
}
