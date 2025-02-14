package com.example.coursehubmanager.activities;

import android.os.Bundle;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.example.coursehubmanager.R;

import com.example.coursehubmanager.databinding.ActivityMainBinding;
import com.example.coursehubmanager.helpers.SharedPreferencesHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferencesHelper prefsHelper = new SharedPreferencesHelper(this);

        Log.d("TAG", "run: ActivityMainBinding " + prefsHelper.getBoolean("is_logged_in", false));

        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

}