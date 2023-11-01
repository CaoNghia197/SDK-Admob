package com.example.admob.callback;

public interface OnLoadCallback {
    void onAdLoaded(String key);
    void onAdFailedToLoad(String message);
}
