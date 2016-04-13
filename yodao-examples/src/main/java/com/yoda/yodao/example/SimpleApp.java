package com.yoda.yodao.example;

import android.app.Application;

public class SimpleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化
        MySQLiteOpenHelper.getInstance(this).init();
    }

}
