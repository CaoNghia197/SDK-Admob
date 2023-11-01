package com.example.admob.rewarded;

import android.app.Activity;
import android.app.Application;
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
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions;

public class RewardedAdManager extends AdmobManager {
  private RewardedAd rewardedAd;
  private OnLoadCallback loadCallback;
  private static final String TAG = "RewardedAdManager";

  public RewardedAdManager(Application application) {
    super(application);
    this.loadCallback = (OnLoadCallback) application;
  }

  private static void onUserEarnedReward(RewardItem rewardItem) {
// Handle the reward.
    Log.d("TAG", "The user earned the reward.");
  }

  @Override
  public void loadAds() {
    if (rewardedAd  == null) {
      RewardedAd.load(getApplication(), Constants.REWARDED,
          getAdRequest(), new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
              // Handle the error.
              Log.d(TAG, loadAdError.toString());
              rewardedAd = null;
              loadCallback.onAdFailedToLoad(loadAdError.getMessage());
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd ad) {
              rewardedAd = ad;
              Log.d(TAG, "Ad was loaded.");

              ServerSideVerificationOptions options = new ServerSideVerificationOptions
                  .Builder()
                  .setCustomData("SAMPLE_CUSTOM_DATA_STRING")
                  .build();
              rewardedAd.setServerSideVerificationOptions(options);

              loadCallback.onAdLoaded(Constants.KEY_REWARDED_AD);
            }
          });
    }
  }

  @Override
  public void showAds(Activity activity, OnShowAdCompleteListener mCallback) {
    if (rewardedAd == null){
      Log.d("TAG", "The rewarded ad wasn't ready yet.");
      loadAds();
      return;
    }
    rewardedAd.setFullScreenContentCallback(
        new FullScreenContentCallback() {
          @Override
          public void onAdShowedFullScreenContent() {
            // Called when ad is shown.
            Log.d(TAG, "onAdShowedFullScreenContent");
            Toast.makeText(activity, "onAdShowedFullScreenContent", Toast.LENGTH_SHORT)
                .show();
            mCallback.onAdShowedFullScreenContent();
          }

          @Override
          public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
            // Called when ad fails to show.
            Log.d(TAG, "onAdFailedToShowFullScreenContent");
            // Don't forget to set the ad reference to null so you
            // don't show the ad a second time.
            rewardedAd = null;
            Toast.makeText(
                    activity, "onAdFailedToShowFullScreenContent", Toast.LENGTH_SHORT)
                .show();
            mCallback.onAdFailedToShowFullScreenContent(adError);
          }

          @Override
          public void onAdDismissedFullScreenContent() {
            // Called when ad is dismissed.
            // Don't forget to set the ad reference to null so you
            // don't show the ad a second time.
            rewardedAd = null;
            Log.d(TAG, "onAdDismissedFullScreenContent");
            Toast.makeText(activity, "onAdDismissedFullScreenContent", Toast.LENGTH_SHORT)
                .show();
              loadAds();
              mCallback.onAdDismissedFullScreenContent();
          }
        });
    rewardedAd.show(
        activity,
        RewardedAdManager::onUserEarnedReward);

  }

}
