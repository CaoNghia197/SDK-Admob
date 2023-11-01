package com.example.admob;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.example.admob.callback.OnLoadCallback;
import com.example.admob.callback.OnShowAdCompleteListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AdmobManager {
  private final Application application;
  private static final String TAG = "AdmobManager";
  private final AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);
  private final AdRequest adRequest;

  public AdmobManager(Application application) {
    this.application = application;
    adRequest = new AdRequest.Builder().build();
    initConstants(application);
    initializeMobileAdsSdk();
    loadAds();
  }

  private void initConstants(Context context) {
    Constants.OPEN_APP = context.getString(R.string.APP_OPEN);
    Constants.INTERSTITIAL = context.getString(R.string.INTERSTITIAL);
    Constants.INTERSTITIAL_REWARDED = context.getString(R.string.INTERSTITIAL_REWARDED);
    Constants.BANNER = context.getString(R.string.BANNER);
    Constants.REWARDED = context.getString(R.string.REWARDED);
  }

  private void initializeMobileAdsSdk() {
    if (isMobileAdsInitializeCalled.getAndSet(true)) {
      return;
    }
    MobileAds.initialize(application, initializationStatus -> {
    });
  }

  public abstract void loadAds();

  public abstract void showAds(Activity activity , OnShowAdCompleteListener mCallback);

  public AdRequest getAdRequest() {
    return adRequest;
  }

  public Application getApplication() {
    return application;
  }
}
