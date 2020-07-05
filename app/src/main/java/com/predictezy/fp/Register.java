package com.predictezy.fp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Objects;

/* renamed from: com.predictezy.fp.Register */
public class Register extends AppCompatActivity {
    EditText email;
    TextView login;
    AdView mAdView;
    FirebaseAuth mFirebaseAuth;
    EditText password;
    Button signup;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.register_page);

        this.mFirebaseAuth = FirebaseAuth.getInstance();
        this.email = (EditText) findViewById(R.id.email);
        this.password = (EditText) findViewById(R.id.password);
        this.signup = (Button) findViewById(R.id.buttonSignUp);
        this.login = (TextView) findViewById(R.id.login);


        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-2924639732611391/7335560461");
        MobileAds.initialize((Context) this, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        this.mAdView = (AdView) findViewById(R.id.ads);
        this.mAdView.loadAd(new Builder().build());


        this.login.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Register.this.startActivity(new Intent(Register.this.getBaseContext(), LoginPage.class));
            }
        });

        this.signup.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String obj = Register.this.email.getText().toString();
                String obj2 = Register.this.password.getText().toString();
                if (obj.isEmpty()) {
                    Register.this.email.setError("Please Enter an Email");
                    Register.this.email.requestFocus();
                } else if (obj2.isEmpty()) {
                    Register.this.password.setError("Please Enter a Password");
                    Register.this.password.requestFocus();
                } else if (obj.length() <= 1 || obj2.length() <= 1) {
                    Toast.makeText(Register.this, "There was some error. Please try again. If the problem persists, please contact the developer", Toast.LENGTH_SHORT).show();
                } else {
                    Register.this.mFirebaseAuth.createUserWithEmailAndPassword(obj, obj2).addOnCompleteListener((Activity) Register.this, new OnCompleteListener<AuthResult>() {
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                ((FirebaseUser) Objects.requireNonNull(Register.this.mFirebaseAuth.getCurrentUser())).sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Register.this, "Please Check Your Email", Toast.LENGTH_SHORT).show();
                                            Register.this.startActivity(new Intent(Register.this, LoginPage.class));
                                            return;
                                        }
                                        Toast.makeText(Register.this, "Account Creation Error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }
                            Toast.makeText(Register.this, "Account Already Exists", Toast.LENGTH_SHORT).show();
                            Register.this.startActivity(new Intent(Register.this, LoginPage.class));
                        }
                    });
                }
            }
        });
    }
}
