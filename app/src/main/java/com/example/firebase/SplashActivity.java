/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.firebase;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import android.view.View.OnClickListener;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import com.example.admob.AdmobSDK;
import com.example.admob.Constants;
import com.example.admob.app_open.AppOpenAdManager;
import com.example.admob.banner.BannerAdManager;
import com.example.admob.callback.OnShowAdCompleteListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.nghia.comic.R;

/**
 * Splash Activity that inflates splash activity xml.
 */
public class SplashActivity extends AppCompatActivity implements OnClickListener {
    private static final String LOG_TAG = "SplashActivity";
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();
    }


    private void initViews() {
        findViewById(R.id.btnLoadAdsbutton).setOnClickListener(this);
        findViewById(R.id.btnRewarded).setOnClickListener(this);
        findViewById(R.id.btnInterstitialAd).setOnClickListener(this);
        App.getInstance().getLiveData().observe(this, key -> Toast.makeText(this, key, Toast.LENGTH_SHORT).show());
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnLoadAdsbutton){
            App.getInstance().getAppOpenAdManager().showAds(SplashActivity.this,
                (OnShowAdCompleteListener) getApplication());
        }else if(v.getId() == R.id.btnRewarded){
            App.getInstance().getRewardedAdManager().showAds(this,
                (OnShowAdCompleteListener) getApplication());
        }else if (v.getId() == R.id.btnInterstitialAd){
            App.getInstance().getInterstitialAdManager().showAds(this,
                (OnShowAdCompleteListener) getApplication());
        }

    }

}
