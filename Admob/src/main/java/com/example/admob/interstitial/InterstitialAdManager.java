package com.example.admob.interstitial;

import android.app.Activity;
import android.app.Application;

import com.example.admob.AdmobManager;
import com.example.admob.callback.OnShowAdCompleteListener;

public class InterstitialAdManager extends AdmobManager {
    public InterstitialAdManager(Application application) {
        super(application);
    }

    @Override
    public void loadAds() {

    }

    @Override
    public void showAds(Activity activity, OnShowAdCompleteListener mCallback) {

    }
}
