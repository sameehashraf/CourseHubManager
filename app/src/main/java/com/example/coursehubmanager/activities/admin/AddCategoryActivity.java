package com.example.coursehubmanager.activities.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursehubmanager.R;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.entities.Category;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.databinding.ActivityAddCategoryBinding;
import com.example.coursehubmanager.databinding.ActivityAddCourseBinding;
import com.example.coursehubmanager.databinding.ActivityCreateAccountBinding;

import java.util.concurrent.Executors;

public class AddCategoryActivity extends AppCompatActivity {
    ActivityAddCategoryBinding binding;
    AppDatabase db;
    MainViewModel viewModel;
    String categoryName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = AppDatabase.getInstance(this);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        binding.imgBack.setOnClickListener(v -> finish());

        binding.btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryName = binding.editCategoryName.getText().toString().trim();
                if (!categoryName.isEmpty()) {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        viewModel.addCategory(new Category(categoryName));
                    });
                    Toast.makeText(getBaseContext(), R.string.added_successfully, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}