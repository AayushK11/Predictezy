package com.predictezy.fp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.splash_screen);
        new Thread() {
            public void run() {
                try {
                    sleep(2500);
                    Intent intent = new Intent(SplashScreen.this, LoginPage.class);
                    startActivity(intent);
                    SplashScreen.this.finish();
                } catch (Exception ignored) {
                }
            }
        }.start();
    }
}
