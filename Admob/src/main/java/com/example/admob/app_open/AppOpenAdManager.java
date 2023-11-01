package com.example.admob.app_open;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.admob.AdmobManager;
import com.example.admob.Constants;
import com.example.admob.R;
import com.example.admob.callback.OnLoadCallback;
import com.example.admob.callback.OnShowAdCompleteListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Date;

public class AppOpenAdManager extends AdmobManager {
    private static final String LOG_TAG = "AppOpenAdManager";
    private AppOpenAd appOpenAd = null;
    private boolean isLoadingAd = false;
    private boolean isShowingAd = false;
    private long loadTime = 0;
    private final OnLoadCallback loadCallback;

  public AppOpenAdManager(Application application) {
    super(application);
    this.loadCallback = (OnLoadCallback) application;
  }


    private boolean isAdAvailable() {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo();
    }
    /** Check if ad was loaded more than n hours ago. */
    private boolean wasLoadTimeLessThanNHoursAgo() {
        long dateDifference = (new Date()).getTime() - loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * 4));
    }



  @Override
  public void loadAds() {
    if (isLoadingAd || isAdAvailable()) {
      return;
    }
    isLoadingAd = true;
    AppOpenAd.load(
        getApplication(), Constants.OPEN_APP, getAdRequest(),
        AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
        new AppOpenAd.AppOpenAdLoadCallback() {
          @Override
          public void onAdLoaded(@NonNull AppOpenAd ad) {
            Log.d(LOG_TAG, "Ad was loaded.");
            appOpenAd = ad;
            isLoadingAd = false;
            loadTime = (new Date()).getTime();
            loadCallback.onAdLoaded(Constants.KEY_OPEN_APP);
          }

          @Override
          public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
            Log.d(LOG_TAG, loadAdError.getMessage());
            isLoadingAd = false;
            loadCallback.onAdFailedToLoad(loadAdError.getMessage());
          }
        });
  }

  @Override
  public void showAds(Activity activity, OnShowAdCompleteListener mCallback) {
    OnShowAdCompleteListener onShowAdCompleteListener = (OnShowAdCompleteListener) getApplication();
    if (isShowingAd) {
      Log.d(LOG_TAG, "The app open ad is already showing.");
      return;
    }

    // If the app open ad is not available yet, invoke the callback then load the ad.
    if (!isAdAvailable()) {
      Log.d(LOG_TAG, "The app open ad is not ready yet.");
      onShowAdCompleteListener.isAdAvailable(false);
      loadAds();
      return;
    }

    Log.d(LOG_TAG, "Will show ad.");

    appOpenAd.setFullScreenContentCallback(
        new FullScreenContentCallback() {
          /** Called when full screen content is dismissed. */
          @Override
          public void onAdDismissedFullScreenContent() {
            // Set the reference to null so isAdAvailable() returns false.
            appOpenAd = null;
            isShowingAd = false;

            Log.d(LOG_TAG, "onAdDismissedFullScreenContent.");
            Toast.makeText(activity, "onAdDismissedFullScreenContent", Toast.LENGTH_SHORT).show();

            onShowAdCompleteListener.onAdDismissedFullScreenContent();
            loadAds();
          }

          /** Called when fullscreen content failed to show. */
          @Override
          public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
            appOpenAd = null;
            isShowingAd = false;

            Log.d(LOG_TAG, "onAdFailedToShowFullScreenContent: " + adError.getMessage());
            Toast.makeText(activity, "onAdFailedToShowFullScreenContent", Toast.LENGTH_SHORT)
                .show();

            onShowAdCompleteListener.onAdFailedToShowFullScreenContent(adError);
            loadAds();
          }

          /** Called when fullscreen content is shown. */
          @Override
          public void onAdShowedFullScreenContent() {
            Log.d(LOG_TAG, "onAdShowedFullScreenContent.");
            Toast.makeText(activity, "onAdShowedFullScreenContent", Toast.LENGTH_SHORT).show();
            onShowAdCompleteListener.onAdShowedFullScreenContent();
          }
        });

    isShowingAd = true;
    appOpenAd.show(activity);
  }
}
