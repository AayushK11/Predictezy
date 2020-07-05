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

/* renamed from: com.predictezy.fp.AskQuestions */
public class AskQuestions extends AppCompatActivity {
    Button askQ;
    Button back;
    EditText email;
    String emailText;
    AdView mAdView;
    EditText name;
    String nameText;
    EditText question;
    String questionText;
    InterstitialAd mInterstitialAd;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.ask_questions);
        this.back = (Button) findViewById(R.id.backButton);
        this.askQ = (Button) findViewById(R.id.buttonAskQuestion);
        this.email = (EditText) findViewById(R.id.email);
        this.name = (EditText) findViewById(R.id.name);
        this.question = (EditText) findViewById(R.id.question);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2924639732611391/7072616832");
        MobileAds.initialize(AskQuestions.this, new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        this.mInterstitialAd.loadAd(new AdRequest.Builder().build());

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-2924639732611391/6027128473");
        MobileAds.initialize((Context) this, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        this.mAdView = (AdView) findViewById(R.id.ads);
        this.mAdView.loadAd(new Builder().build());


        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AskQuestions.this.startActivity(new Intent(AskQuestions.this.getBaseContext(), HomePage.class));
            }
        });
        this.askQ.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AskQuestions AskQuestions = AskQuestions.this;
                AskQuestions.nameText = AskQuestions.name.getText().toString();
                AskQuestions AskQuestions2 = AskQuestions.this;
                AskQuestions2.emailText = AskQuestions2.email.getText().toString();
                AskQuestions AskQuestions3 = AskQuestions.this;
                AskQuestions3.questionText = AskQuestions3.question.getText().toString();
                if (AskQuestions.this.nameText.isEmpty()) {
                    AskQuestions.this.name.setError("Please Enter your Name");
                    AskQuestions.this.name.requestFocus();
                } else if (AskQuestions.this.emailText.isEmpty()) {
                    AskQuestions.this.email.setError("Please Enter an Email");
                    AskQuestions.this.email.requestFocus();
                } else if (AskQuestions.this.questionText.isEmpty()) {
                    AskQuestions.this.question.setError("Please Ask a Valid Question");
                    AskQuestions.this.question.requestFocus();
                } else {
                    Toast.makeText(AskQuestions.this, "Connecting", Toast.LENGTH_SHORT).show();
                    StringRequest r1 = new StringRequest(1, "https://script.google.com/macros/s/AKfycbwFBBbcdFT_2xlZuV02-XYpzVn7gq4Hb3_9fagetmcb97BN6wXx/exec", new Listener<String>() {
                        public void onResponse(String str) {
                            Toast.makeText(AskQuestions.this, "Question Sent Successfully. Expect a Response Within a Few Days", Toast.LENGTH_SHORT).show();
                            AskQuestions.this.startActivity(new Intent(AskQuestions.this, HomePage.class));
                        }
                    }, new ErrorListener() {
                        public void onErrorResponse(VolleyError volleyError) {
                        }
                    }) {
                        /* access modifiers changed from: protected */
                        public Map<String, String> getParams() {
                            HashMap hashMap = new HashMap();
                            hashMap.put("action", "addItem");
                            hashMap.put("Name", AskQuestions.this.nameText);
                            hashMap.put("Email", AskQuestions.this.emailText);
                            hashMap.put("Question", AskQuestions.this.questionText);
                            return hashMap;
                        }
                    };
                    r1.setRetryPolicy(new DefaultRetryPolicy(50000, 0, 1.0f));
                    Volley.newRequestQueue(AskQuestions.this).add(r1);
                }
            }
        });
    }
}
