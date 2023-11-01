package com.example.admob.banner;

import android.app.Activity;
import android.app.Application;
import androidx.annotation.NonNull;
import com.example.admob.AdmobManager;
import com.example.admob.Constants;
import com.example.admob.callback.OnShowAdCompleteListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

public class BannerAdManager extends AdmobManager {
  private boolean isLoadingAd = false;
  private AdView adView;

  public BannerAdManager(Application application) {
    super(application);
  }


  @Override
  public void loadAds() {
    if (isLoadingAd){
      return;
    }
    isLoadingAd = true;
    adView = new AdView(getApplication());
    adView.setAdSize(AdSize.FULL_BANNER);
    adView.setAdUnitId(Constants.BANNER);
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
      }

      @Override
      public void onAdImpression() {
        super.onAdImpression();
      }

      @Override
      public void onAdLoaded() {
        super.onAdLoaded();
        isLoadingAd = false;
      }

      @Override
      public void onAdOpened() {
        super.onAdOpened();
        isLoadingAd = false;
      }

      @Override
      public void onAdSwipeGestureClicked() {
        super.onAdSwipeGestureClicked();
      }
    });
  }

  @Override
  public void showAds(Activity activity, OnShowAdCompleteListener mCallback) {
    if (adView  == null){
      loadAds();
      return;
    }
    adView.loadAd(getAdRequest());
  }
}
