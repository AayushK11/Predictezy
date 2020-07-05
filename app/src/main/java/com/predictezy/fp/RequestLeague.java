package com.predictezy.fp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.predictezy.fp.RequestLeague */
public class RequestLeague extends AppCompatActivity {
    Button back;
    EditText email;
    String emailText;
    EditText league;
    String leagueText;
    AdView mAdView;
    EditText name;
    String nameText;
    Button request;
    InterstitialAd mInterstitialAd;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.request_league);
        this.back = (Button) findViewById(R.id.backButton);
        this.request = (Button) findViewById(R.id.buttonRequest);
        this.email = (EditText) findViewById(R.id.email);
        this.name = (EditText) findViewById(R.id.name);
        this.league = (EditText) findViewById(R.id.league);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2924639732611391/4326732678");
        MobileAds.initialize(RequestLeague.this, new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        this.mInterstitialAd.loadAd(new AdRequest.Builder().build());


        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-2924639732611391/8461720129");
        MobileAds.initialize((Context) this, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        this.mAdView = (AdView) findViewById(R.id.ads);
        this.mAdView.loadAd(new Builder().build());


        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                RequestLeague.this.startActivity(new Intent(RequestLeague.this.getBaseContext(), HomePage.class));
            }
        });
        this.request.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                RequestLeague RequestLeague = RequestLeague.this;
                RequestLeague.nameText = RequestLeague.name.getText().toString();
                RequestLeague RequestLeague2 = RequestLeague.this;
                RequestLeague2.emailText = RequestLeague2.email.getText().toString();
                RequestLeague RequestLeague3 = RequestLeague.this;
                RequestLeague3.leagueText = RequestLeague3.league.getText().toString();
                if (RequestLeague.this.nameText.isEmpty()) {
                    RequestLeague.this.name.setError("Please Enter your Name");
                    RequestLeague.this.name.requestFocus();
                } else if (RequestLeague.this.emailText.isEmpty()) {
                    RequestLeague.this.email.setError("Please Enter an Email");
                    RequestLeague.this.email.requestFocus();
                } else if (RequestLeague.this.leagueText.isEmpty()) {
                    RequestLeague.this.league.setError("Please Enter a League");
                    RequestLeague.this.league.requestFocus();
                } else {
                    Toast.makeText(RequestLeague.this, "Connecting", Toast.LENGTH_SHORT).show();
                    StringRequest r1 = new StringRequest(1, "https://script.google.com/macros/s/AKfycbyOiH6YCxi2t4zkiw-L-Yx9O5c_bwL95SwKasHbOB61YEaJk7Q/exec", new Listener<String>() {
                        public void onResponse(String str) {
                            Toast.makeText(RequestLeague.this, "League Request Sent Successfully", Toast.LENGTH_SHORT).show();
                            RequestLeague.this.startActivity(new Intent(RequestLeague.this, HomePage.class));
                        }
                    }, new ErrorListener() {
                        public void onErrorResponse(VolleyError volleyError) {
                        }
                    }) {
                        /* access modifiers changed from: protected */
                        public Map<String, String> getParams() {
                            HashMap hashMap = new HashMap();
                            hashMap.put("action", "addItem");
                            hashMap.put("Name", RequestLeague.this.nameText);
                            hashMap.put("Email", RequestLeague.this.emailText);
                            hashMap.put("League", RequestLeague.this.leagueText);
                            return hashMap;
                        }
                    };
                    r1.setRetryPolicy(new DefaultRetryPolicy(50000, 0, 1.0f));
                    Volley.newRequestQueue(RequestLeague.this).add(r1);
                }
            }
        });
    }
}
