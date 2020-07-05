package com.predictezy.fp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/* renamed from: com.predictezy.fp.ForgotPassword */
public class ForgotPassword extends AppCompatActivity {
    EditText email;
    Button forgotpassword;
    TextView login;
    AdView mAdView;
    FirebaseAuth mFirebaseAuth;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.forgot_password);
        
        this.mFirebaseAuth = FirebaseAuth.getInstance();
        this.email = (EditText) findViewById(R.id.email);
        this.login = (TextView) findViewById(R.id.login);
        this.forgotpassword = (Button) findViewById(R.id.buttonForgotPassword);



        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-2924639732611391/6034867331");
        MobileAds.initialize((Context) this, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        this.mAdView = (AdView) findViewById(R.id.ads);
        this.mAdView.loadAd(new Builder().build());



        this.login.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ForgotPassword.this.startActivity(new Intent(ForgotPassword.this, LoginPage.class));
            }
        });

        this.forgotpassword.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String obj = ForgotPassword.this.email.getText().toString();
                if (obj.isEmpty()) {
                    ForgotPassword.this.email.setError("Please Enter an Email");
                    ForgotPassword.this.email.requestFocus();
                    return;
                }
                ForgotPassword.this.mFirebaseAuth.sendPasswordResetEmail(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassword.this, "Please Check Your Email", Toast.LENGTH_SHORT).show();
                            ForgotPassword.this.startActivity(new Intent(ForgotPassword.this, LoginPage.class));
                            return;
                        }
                        Toast.makeText(ForgotPassword.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
