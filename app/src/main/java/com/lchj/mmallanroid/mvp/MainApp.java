package com.lchj.mmallanroid.mvp;

import android.content.Context;

import com.jess.arms.base.BaseApplication;

import androidx.multidex.MultiDex;
public class MainApp extends BaseApplication {
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
