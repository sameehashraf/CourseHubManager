package com.example.coursehubmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.coursehubmanager.R;
import com.example.coursehubmanager.adapters.EditCoursesAdapter;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.entities.UserCourse;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.helpers.SharedPreferencesHelper;
import com.example.coursehubmanager.adapters.CoursesAdapter;
import com.example.coursehubmanager.databinding.ActivityBookmarksBinding;

import java.util.ArrayList;
import java.util.List;

public class BookmarksActivity extends AppCompatActivity {

    ActivityBookmarksBinding binding;
    CoursesAdapter adapter;
    SharedPreferencesHelper prefsHelper;
    MainViewModel viewModel;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookmarksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        prefsHelper = new SharedPreferencesHelper(this);
        binding.recCourses.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CoursesAdapter(viewModel);
        binding.recCourses.setAdapter(adapter);
        userId = prefsHelper.getInt("userId", -1);

        if (userId != -1) {
            viewModel.getAllBookmarkedCourses(userId).observe(this,
                    courses -> adapter.setData(courses)
            );
        }
        adapter.setOnItemClickListener((position, isIcon) -> {
            if (!isIcon) {
                Intent intent = new Intent(BookmarksActivity.this, CourseDetailsActivity.class);
                intent.putExtra("courseId", position); // Pass data to the new activity
                startActivity(intent);
            }
        });
        binding.imgBack.setOnClickListener(v -> finish());
    }
}