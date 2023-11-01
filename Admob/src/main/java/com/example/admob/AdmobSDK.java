package com.example.admob;

import android.app.Application;
import android.content.Context;

import com.example.admob.app_open.AppOpenAdManager;
import com.example.admob.banner.BannerAdManager;
import com.example.admob.rewarded.RewardedAdManager;
import com.google.android.gms.ads.MobileAds;

public class AdmobSDK {
    private static AdmobSDK instance;
    private AdmobManager banner;
    private AdmobManager openApp;
    private AdmobManager rewarded;

    public static AdmobSDK getInstance() {
        if(instance == null){
            instance = new AdmobSDK();
        }
        return instance;
    }

    public void onStart(Application application){
        openApp = new AppOpenAdManager(application);
        rewarded = new RewardedAdManager(application);
        banner = new BannerAdManager(application);
    }


    public AdmobManager getBanner() {
        return banner;
    }

    public AdmobManager getOpenApp() {
        return openApp;
    }

    public AdmobManager getRewarded() {
        return rewarded;
    }
}
