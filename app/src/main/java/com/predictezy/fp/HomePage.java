package com.predictezy.fp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, new FragmentMatches()).commit();


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        MobileAds.initialize(HomePage.this, new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        this.mInterstitialAd.loadAd(new AdRequest.Builder().build());


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            int itemId = menuItem.getItemId();
            if (itemId == R.id.table) {
                fragment = new FragmentStandings();
            } else {
                fragment = new FragmentMatches();
            }
            HomePage.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, fragment).commit();
            return true;
        }
    };

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
            Toast.makeText(this, "Successfully Logged Out", 0).show();
            startActivity(new Intent(this, LoginPage.class));
            finishAffinity();
        }
        return true;
    }
}
