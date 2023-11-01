package com.example.firebase;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.admob.AdmobSDK;
import com.example.admob.Constants;
import com.example.admob.app_open.AppOpenAdManager;
import com.example.admob.banner.BannerAdManager;
import com.example.admob.callback.OnLoadCallback;
import com.example.admob.callback.OnShowAdCompleteListener;
import com.example.admob.rewarded.RewardedAdManager;
import com.google.android.gms.ads.AdError;

public class App extends Application implements Application.ActivityLifecycleCallbacks, DefaultLifecycleObserver,
    OnLoadCallback,OnShowAdCompleteListener{
    private static final String TAG = "MyApplication";
    private static App sInstance;
    private Activity activity;
    private MutableLiveData<String> liveData = new MutableLiveData<>();

    @Override
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        AdmobSDK.getInstance().onStart(this);
        sInstance = this;
    }

    public MutableLiveData<String> getLiveData() {
        return liveData;
    }

    public static App getInstance(){
        return sInstance;
    }

    public AppOpenAdManager getAppOpenAdManager() {
        return (AppOpenAdManager) AdmobSDK.getInstance().getOpenApp();
    }   
    
    public RewardedAdManager getRewardedAdManagar() {
        return (RewardedAdManager) AdmobSDK.getInstance().getRewarded();
    }

    public BannerAdManager getBannerAds() {
        return (BannerAdManager) AdmobSDK.getInstance().getBanner();
    }

    public OnShowAdCompleteListener getCallback (){
        return this;
    }
        @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStart(owner);
            AdmobSDK.getInstance().getOpenApp().showAds(activity,this);
        }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    @Override
    public void isAdAvailable(boolean isAdAvailable) {
        Log.d(TAG, "isAdAvailable: ");
    }

    @Override
    public void onAdDismissedFullScreenContent() {
        Log.d(TAG, "onAdDismissedFullScreenContent: ");
    }

    @Override
    public void onAdShowedFullScreenContent() {
        Log.d(TAG, "onAdShowedFullScreenContent: ");
    }

    @Override
    public void onAdFailedToShowFullScreenContent(AdError err) {
        Log.d(TAG, "onAdFailedToShowFullScreenContent: " + err.getMessage());
    }

    @Override
    public void onAdLoaded(String key) {
        liveData.setValue(key);
    }

    @Override
    public void onAdFailedToLoad(String message) {
        Log.d(TAG, "onAdFailedToLoad: " + message);
        liveData.setValue(message);
    }
}
