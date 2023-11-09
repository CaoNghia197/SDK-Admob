package com.example.admob.interstitial;

import android.app.Activity;
import android.app.Application;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.admob.AdmobManager;
import com.example.admob.Constants;
import com.example.admob.callback.OnLoadCallback;
import com.example.admob.callback.OnShowAdCompleteListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class InterstitialAdManager extends AdmobManager {
    private InterstitialAd mInterstitialAd;
    private static final String TAG = "InterstitialAdManager";
    private final OnLoadCallback loadCallback;
    public InterstitialAdManager(Application application) {
        super(application);
        this.loadCallback = (OnLoadCallback) application;
    }

    @Override
    public void loadAds() {
        InterstitialAd.load(getApplication(), Constants.INTERSTITIAL, getAdRequest(),
            new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    mInterstitialAd = interstitialAd;
                    Log.i(TAG, "onAdLoaded");
                    loadCallback.onAdLoaded(Constants.KEY_INTERSTITIAL);
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    Log.d(TAG, loadAdError.toString());
                    mInterstitialAd = null;
                    loadCallback.onAdFailedToLoad(loadAdError.getMessage());
                }
            });

    }

    @Override
    public void showAds(Activity activity, OnShowAdCompleteListener mCallback) {
        if (mInterstitialAd != null) {
            onSetupAd(activity,mCallback);
        } else {
            loadAds();
        }
    }

    private void onSetupAd(Activity activity, OnShowAdCompleteListener mCallback) {
        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
            @Override
            public void onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.");
              mCallback.isAdAvailable(true);
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad dismissed fullscreen content.");
                mInterstitialAd = null;
                mCallback.onAdDismissedFullScreenContent();
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                Log.e(TAG, "Ad failed to show fullscreen content.");
                mInterstitialAd = null;
                mCallback.onAdFailedToShowFullScreenContent(adError);
            }

            @Override
            public void onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed fullscreen content.");
                mCallback.onAdShowedFullScreenContent();
            }
        });
        mInterstitialAd.show(activity);
    }
}
