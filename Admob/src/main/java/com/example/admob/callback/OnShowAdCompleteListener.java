package com.example.admob.callback;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.initialization.InitializationStatus;

public interface OnShowAdCompleteListener {
    void isAdAvailable(boolean isAdAvailable);
    void onAdDismissedFullScreenContent();
    void onAdShowedFullScreenContent();

    void onAdFailedToShowFullScreenContent(AdError err);
}
