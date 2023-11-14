package com.example.admob.banner;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import com.example.admob.AdmobManager;
import com.example.admob.Constants;
import com.example.admob.callback.OnLoadCallback;
import com.example.admob.callback.OnShowAdCompleteListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

public class BannerAdManager extends AdmobManager {
  private boolean isLoadingAd = false;
  private AdView adView;
  private static final String TAG = "BannerAdManager";
  private final OnLoadCallback loadCallback;

  public BannerAdManager(Application application) {
    super(application);
    this.loadCallback = (OnLoadCallback) application;
  }

  @Override
  public void loadAds() {

  }

  @Override
  public void showAds(Activity activity, OnShowAdCompleteListener mCallback) {

  }

  public void showAdsBanner(AdView addView, OnShowAdCompleteListener mCallback) {
    if (adView  == null){
      loadAdsBanner(addView);
      return;
    }
    adView.loadAd(getAdRequest());
  }

  private void loadAdsBanner(AdView adView) {
    if (isLoadingAd){
      return;
    }
    isLoadingAd = true;
    this.adView = adView;
    adView.setAdSize(AdSize.FULL_BANNER);
    adView.setAdUnitId(Constants.BANNER);
    adView.loadAd(getAdRequest());
    adView.setAdListener(new AdListener() {
      @Override
      public void onAdClicked() {
        super.onAdClicked();
      }

      @Override
      public void onAdClosed() {
        super.onAdClosed();
        isLoadingAd = false;
      }

      @Override
      public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
        super.onAdFailedToLoad(loadAdError);
        Log.d(TAG, "onAdFailedToLoad: " + loadAdError.getMessage());
        Log.d(TAG, "onAdFailedToLoad: " + loadAdError);
      }

      @Override
      public void onAdImpression() {
        super.onAdImpression();
      }

      @Override
      public void onAdLoaded() {
        super.onAdLoaded();
        isLoadingAd = false;
        Log.d(TAG, "Load Banner Success");
        loadCallback.onAdLoaded(Constants.KEY_OPEN_APP);
      }

      @Override
      public void onAdOpened() {
        super.onAdOpened();
        isLoadingAd = false;
        Log.d(TAG, "Show Banner Success");
      }

      @Override
      public void onAdSwipeGestureClicked() {
        super.onAdSwipeGestureClicked();
      }
    });
  }
}
