package com.predictezy.fp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.google.firebase.auth.FirebaseAuth;

/* renamed from: com.predictezy.fp.HomePage */
public class HomePage extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    AdView mAdView;
    private InterstitialAd mInterstitialAd;

    /* access modifiers changed from: protected */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.home_page);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        MobileAds.initialize(HomePage.this, new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        this.mInterstitialAd.loadAd(new AdRequest.Builder().build());

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-2924639732611391/6773233939");
        MobileAds.initialize((Context) this, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        this.mAdView = (AdView) findViewById(R.id.ads);
        this.mAdView.loadAd(new AdRequest.Builder().build());

        RelativeLayout relativeLayout0 = (RelativeLayout)findViewById(R.id.PremierLeague);
        RelativeLayout relativeLayout1 = (RelativeLayout)findViewById(R.id.LaLiga);
        RelativeLayout relativeLayout2 = (RelativeLayout)findViewById(R.id.Bundesliga);
        RelativeLayout relativeLayout3 = (RelativeLayout)findViewById(R.id.SerieA);
        RelativeLayout relativeLayout4 = (RelativeLayout)findViewById(R.id.Ligue1);

        relativeLayout0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HomePage.this.startActivity(new Intent(HomePage.this, PremierLeagueMatches.class));
            }
        });
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HomePage.this.startActivity(new Intent(HomePage.this, LaLigaMatches.class));
            }
        });
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HomePage.this.startActivity(new Intent(HomePage.this, BundesligaMatches.class));
            }
        });
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HomePage.this.startActivity(new Intent(HomePage.this, SerieAMatches.class));
            }
        });
        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HomePage.this.startActivity(new Intent(HomePage.this, Ligue1Matches.class));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        this.mFirebaseAuth = FirebaseAuth.getInstance();
        if (menuItem.getItemId() == R.id.notice) {
            startActivity(new Intent(this, Notice.class));
        } else if (menuItem.getItemId() == R.id.requestleague) {
            startActivity(new Intent(this, RequestLeague.class));
        } else if (menuItem.getItemId() == R.id.askquestions) {
            startActivity(new Intent(this, AskQuestions.class));
        } else if (menuItem.getItemId() == R.id.logout) {
            this.mFirebaseAuth.signOut();
            Toast.makeText(this, "Successfully Logged Out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginPage.class));
            finishAffinity();
        }
        return true;
    }
}
