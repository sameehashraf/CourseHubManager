package com.example.coursehubmanager.activities.admin;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursehubmanager.R;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.entities.Category;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.databinding.ActivityEditCategoryBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class EditCategoryActivity extends AppCompatActivity {
    ActivityEditCategoryBinding binding;
    MainViewModel viewModel;
    AppDatabase db;

    int categoryId = -1;
    String categoryName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        db = AppDatabase.getInstance(this);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.imgBack.setOnClickListener(v -> finish());
        binding.editChooseCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
        binding.btnEditCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryName = binding.editCategoryName.getText().toString().trim();
                if (!categoryName.isEmpty()) {
                    Executors.newSingleThreadExecutor().execute(() -> {
                    Category category = new Category(categoryName);
                    category.setCategoryId(categoryId);
                    viewModel.updateCategory(category);
                    });
                    showToast(getString(R.string.edited_successfully));

                }else {
                    showToast(getString(R.string.something_went_wrong));
                }
            }
        });

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showPopupMenu(View view) {
        List<Category> categoryList = new ArrayList<>();

        viewModel.getCategories().observe(this, categories -> {
            if (categories != null && !categories.isEmpty()) {
                categoryList.addAll(categories);
            }
        });

        PopupMenu popupMenu = new PopupMenu(this, view);

        for (Category category : categoryList) {
            popupMenu.getMenu().add(category.getCategoryName());
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                for (Category category : categoryList) {
                    if (category.getCategoryName() == item.getTitle()) {
                        categoryId = category.getCategoryId();
                        categoryName = category.getCategoryName();
                        binding.editChooseCategory.setText(categoryName);
                    }
                }
                return true;
            }
        });

        // عرض القائمة
        popupMenu.show();
    }

}