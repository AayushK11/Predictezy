package com.predictezy.fp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.predictezy.fp.R;

/* renamed from: com.predictezy.fp.Notice */
public class Notice extends AppCompatActivity {
    Button button;
    AdView mAdView;
    InterstitialAd mInterstitialAd;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.notice);
        this.button = (Button) findViewById(R.id.backButton);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2924639732611391/3324943513");
        MobileAds.initialize(Notice.this, new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        this.mInterstitialAd.loadAd(new AdRequest.Builder().build());

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-2924639732611391/2471026843");
        MobileAds.initialize((Context) this, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        this.mAdView = (AdView) findViewById(R.id.ads);
        this.mAdView.loadAd(new Builder().build());


        this.button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Notice.this.startActivity(new Intent(Notice.this.getBaseContext(), HomePage.class));
            }
        });
    }
}
