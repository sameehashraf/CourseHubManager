package com.example.coursehubmanager.fragments;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursehubmanager.database.entities.User;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.databinding.FragmentProfileBinding;
import com.example.coursehubmanager.helpers.SharedPreferencesHelper;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private boolean isShow = false;
    private MainViewModel viewModel;
    User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        SharedPreferencesHelper prefsHelper = new SharedPreferencesHelper(requireContext());
        int userId = prefsHelper.getInt("userId", -1);
        user = viewModel.getUserById(userId);

        binding.editEmail.setText(user.getEmail());
        binding.editPassword.setText(user.getPassword());
        binding.editFullName.setText(user.getUserName());

        binding.imgEdit.setOnClickListener(v -> {
            binding.editFullName.setEnabled(true);
            binding.editEmail.setEnabled(true);
            binding.editPassword.setEnabled(true);
            binding.btnSaveChanges.setVisibility(View.VISIBLE);
        });

        binding.imgEye.setOnClickListener(v -> {
            isShow = !isShow;
            if (isShow) {
                binding.editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                binding.editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        binding.btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setUserName(binding.editFullName.getText().toString());
                user.setEmail(binding.editEmail.getText().toString());
                user.setPassword(binding.editPassword.getText().toString());
                viewModel.updateUser(user);
                binding.editFullName.setEnabled(false);
                binding.editEmail.setEnabled(false);
                binding.editPassword.setEnabled(false);
                isShow = false;
                binding.btnSaveChanges.setVisibility(View.INVISIBLE);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}