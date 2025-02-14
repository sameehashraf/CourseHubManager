package com.example.coursehubmanager.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursehubmanager.R;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.dao.UserDao;
import com.example.coursehubmanager.database.entities.Lesson;
import com.example.coursehubmanager.database.entities.User;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.databinding.ActivityLoginBinding;
import com.example.coursehubmanager.helpers.MyApplication;
import com.example.coursehubmanager.helpers.SharedPreferencesHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    AppDatabase db;
    UserDao dao;
    String email = "";
    String password = "";
    private boolean isShow = false;
    SharedPreferencesHelper prefsHelper;
    MainViewModel viewModel;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        prefsHelper = new SharedPreferencesHelper(this);
        viewModel = new ViewModelProvider(LoginActivity.this).get(MainViewModel.class);
        db = AppDatabase.getInstance(this);
        dao = db.userDao();

        binding.btnLogin.setOnClickListener(v -> {
            email = binding.editEmail.getText().toString().trim();
            password = binding.editPassword.getText().toString();
            if (validateInputs(email, password)) {
                new AuthenticateUserTask().execute(email, password);
            }
        });

        binding.txtCreateAccount.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateAccountActivity.class));
        });

        binding.imgEye.setOnClickListener(v -> {
            isShow = !isShow;
            if (isShow) {
                binding.editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                binding.editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

    }

    private boolean validateInputs(String email, String password) {
        if (email.isEmpty() || !isValidEmail(email)) {
            showError(getString(R.string.enter_a_valid_email_address));
            return false;
        }
        if (password.isEmpty() || password.length() < 8 || !isValidPassword(password)) {
            showError(getString(R.string.password_must_be_at_least_8_characters));
            return false;
        }
        return true;
    }

    private void showError(String errorMessage) {
        Toast.makeText(MyApplication.getInstance().getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password.trim().length() >= 8;
    }


    private class AuthenticateUserTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... params) {
            // Authenticate the user in the background
            String email = params[0];
            String password = params[1];
            return db.userDao().login(email, password);
        }
        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            if (user != null) {
                if (email.equals("Admin@Admin.com") && password.equals("AdminAdmin")) {
                    startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                } else {
                    if (binding.checkbox.isChecked()) {
                        Log.d("TAG", "onPostExecute:getUserId "+user.getUserId());
                        prefsHelper.saveBoolean("is_logged_in", true);
                        prefsHelper.saveInt("userId", user.getUserId());
                    }
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            } else {
                // If authentication failed, show an error message
                Toast.makeText(LoginActivity.this, getString(R.string.invalid_email_or_password), Toast.LENGTH_SHORT).show();
            }
        }
    }

}