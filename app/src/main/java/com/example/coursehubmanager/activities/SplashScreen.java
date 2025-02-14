package com.example.coursehubmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.coursehubmanager.R;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.helpers.SharedPreferencesHelper;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferencesHelper prefsHelper = new SharedPreferencesHelper(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                Log.d("TAG", "run: " + prefsHelper.getBoolean("is_logged_in", false));
                if (prefsHelper.getBoolean("is_logged_in", false)) {
                    Log.d("TAG", "run: " + prefsHelper.getBoolean("is_logged_in", false));
                    intent = new Intent(SplashScreen.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashScreen.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 10);

    }
}