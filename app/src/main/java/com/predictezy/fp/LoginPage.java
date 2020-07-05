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
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseUser;

/* renamed from: com.predictezy.fp.LoginPage */
public class LoginPage extends AppCompatActivity {
    EditText email;
    TextView forgotpassword;
    Button login;
    AdView mAdView;
    AuthStateListener mAuthStateListener;
    FirebaseAuth mFirebaseAuth;
    EditText password;
    TextView signup;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.login_page);
        
        this.mFirebaseAuth = FirebaseAuth.getInstance();
        this.email = (EditText) findViewById(R.id.email);
        this.password = (EditText) findViewById(R.id.password);
        this.login = (Button) findViewById(R.id.buttonLogin);
        this.signup = (TextView) findViewById(R.id.signup);
        this.forgotpassword = (TextView) findViewById(R.id.forgotpassword);



        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-2924639732611391/8661030671");
        MobileAds.initialize((Context) this, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        this.mAdView = (AdView) findViewById(R.id.ads);
        this.mAdView.loadAd(new Builder().build());




        this.signup.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LoginPage.this.startActivity(new Intent(LoginPage.this.getBaseContext(), Register.class));
            }
        });


        this.forgotpassword.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LoginPage.this.startActivity(new Intent(LoginPage.this.getBaseContext(), ForgotPassword.class));
            }
        });


        this.mAuthStateListener = new AuthStateListener() {
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null && currentUser.isEmailVerified()) {
                    LoginPage.this.startActivity(new Intent(LoginPage.this, HomePage.class));
                    LoginPage.this.finish();
                }
            }
        };


        this.login.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String obj = LoginPage.this.email.getText().toString();
                String obj2 = LoginPage.this.password.getText().toString();
                if (obj.isEmpty()) {
                    LoginPage.this.email.setError("Please Enter an Email");
                    LoginPage.this.email.requestFocus();
                } else if (obj2.isEmpty()) {
                    LoginPage.this.password.setError("Please Enter a Password");
                    LoginPage.this.password.requestFocus();
                } else if (obj.length() <= 1 || obj2.length() <= 1) {
                    Toast.makeText(LoginPage.this, "There was some error. Please try again. If the problem persists, please contact the developer", Toast.LENGTH_SHORT).show();
                } else {
                    LoginPage.this.mFirebaseAuth.signInWithEmailAndPassword(obj, obj2).addOnCompleteListener((Activity) LoginPage.this, new OnCompleteListener<AuthResult>() {
                        public void onComplete(Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginPage.this, "Log In Error", Toast.LENGTH_SHORT).show();
                            } else if (LoginPage.this.mFirebaseAuth.getCurrentUser().isEmailVerified()) {
                                Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                LoginPage.this.startActivity(new Intent(LoginPage.this, HomePage.class));
                            } else {
                                Toast.makeText(LoginPage.this, "Please Verify Your Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        this.mAuthStateListener.onAuthStateChanged(this.mFirebaseAuth);
    }


    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.mFirebaseAuth.addAuthStateListener(this.mAuthStateListener);
    }
}
