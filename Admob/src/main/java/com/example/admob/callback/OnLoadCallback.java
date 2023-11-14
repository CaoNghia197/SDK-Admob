package com.example.admob.callback;

public interface OnLoadCallback {
    void onAdLoaded(Object key);
    void onAdFailedToLoad(String message);
}
