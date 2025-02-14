package com.example.coursehubmanager.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursehubmanager.R;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.dao.UserDao;
import com.example.coursehubmanager.database.entities.User;
import com.example.coursehubmanager.databinding.ActivityCreateAccountBinding;
import com.example.coursehubmanager.helpers.SharedPreferencesHelper;

public class CreateAccountActivity extends AppCompatActivity {
    ActivityCreateAccountBinding binding;
    AppDatabase db;
    UserDao dao;
    String username;
    String email;
    String password;
    private boolean isShow = false;
    SharedPreferencesHelper prefsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = AppDatabase.getInstance(this);
        dao = db.userDao();
        prefsHelper = new SharedPreferencesHelper(this);

        binding.btnSignUp.setOnClickListener(v -> {
            username = binding.editFullName.getText().toString().trim();
            email = binding.editEmail.getText().toString().trim();
            password = binding.editPassword.getText().toString();
            if (validateInputs(username, email, password)) {
                insertUser();
            }
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


    private void insertUser() {
        User user = new User(username, email, password);
        dao.insertUser(user);
        Log.d("TAG", "createUser:user " + user);
        startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
    }

    private boolean validateInputs(String username, String email, String password) {
        if (username.isEmpty()) {
            showError(getString(R.string.username_cannot_be_empty));
            return false;
        }
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
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password.trim().length() >= 8;
    }

}