package com.example.coursehubmanager.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursehubmanager.activities.admin.AddCategoryActivity;
import com.example.coursehubmanager.activities.admin.AddCourseActivity;
import com.example.coursehubmanager.activities.admin.AddLessonActivity;
import com.example.coursehubmanager.activities.admin.ViewCoursesActivity;
import com.example.coursehubmanager.activities.admin.EditCategoryActivity;
import com.example.coursehubmanager.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgBack.setOnClickListener(v -> finish());
        binding.txtAddCourse.setOnClickListener(v -> startActivity
                (new Intent(getBaseContext(), AddCourseActivity.class))
        );

        binding.txtAddCategory.setOnClickListener(v -> startActivity
                (new Intent(getBaseContext(), AddCategoryActivity.class))
        );

        binding.txtAddLesson.setOnClickListener(v -> startActivity
                (new Intent(getBaseContext(), AddLessonActivity.class))
        );

        binding.txtEditCourse.setOnClickListener(v -> startActivity
                (new Intent(getBaseContext(), ViewCoursesActivity.class))
        );

        binding.txtEditCategory.setOnClickListener(v -> startActivity
                (new Intent(getBaseContext(), EditCategoryActivity.class))
        );

    }
}