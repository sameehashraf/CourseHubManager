package com.example.coursehubmanager.activities.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursehubmanager.R;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.entities.Lesson;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.databinding.ActivityAddLessonBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class AddLessonActivity extends AppCompatActivity {
    ActivityAddLessonBinding binding;

    AppDatabase db;

    String editLessonName;
    String editLessonDescription;
    String editLessonLink;
    String editLessonDuration;
    String editCourseName;
    int courseId;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddLessonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = AppDatabase.getInstance(this);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        binding.imgBack.setOnClickListener(v -> finish());
        binding.editCourseId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        binding.btnAddLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editLessonName = binding.editLessonName.getText().toString().trim();
                editLessonDescription = binding.editLessonDescription.getText().toString().trim();
                editLessonLink = binding.editLessonLink.getText().toString().trim();
//                editLessonDuration = binding.editLessonDuration.getText().toString().trim();

                if (isValid(editLessonName, editLessonDescription, editLessonLink/*, editLessonDuration*/)) {
                    Lesson lesson = new Lesson(courseId, editLessonName, editLessonDescription, editLessonLink);
                    Executors.newSingleThreadExecutor().execute(() -> {
                        viewModel.addLesson(lesson);
                    });
                    Toast.makeText(AddLessonActivity.this, R.string.added_successfully, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }

    private boolean isValid(String editLessonName,
                            String editLessonDescription,
                            String editLessonLink
//            ,
//                            String editLessonDuration
    ) {
        Log.d("TAG", "isValid: " + editLessonName);
        Log.d("TAG", "isValid: " + editLessonDescription);
        Log.d("TAG", "isValid: " + editLessonLink);
//        Log.d("TAG", "isValid: " + editLessonDuration);
        if (editLessonName.isEmpty()) {
            showError(getString(R.string.data_cant_be_empty));
            return false;
        }
        if (editLessonDescription.isEmpty()) {
            showError(getString(R.string.data_cant_be_empty));
            return false;
        }
        if (editLessonLink.isEmpty()) {
            showError(getString(R.string.data_cant_be_empty));
            return false;
        }
        if (editLessonDuration.isEmpty()) {
            showError(getString(R.string.data_cant_be_empty));
            return false;
        }
        if (courseId == -1) {
            showError(getString(R.string.select_Course));
            return false;
        }

        return true;
    }

    private void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }


    private void showPopupMenu(View view) {
        List<Course> courseList = new ArrayList<>();
        PopupMenu popupMenu = new PopupMenu(this, view);

        viewModel.getCourses().observe(this, courses -> {
            if (courses != null && !courses.isEmpty()) {
                courseList.addAll(courses);

            }
        });

        for (Course course : courseList) {
            popupMenu.getMenu().add(course.getCourseName());
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                for (Course course : courseList) {
                    if (course.getCourseName() == item.getTitle()) {
                        courseId = course.getCourseId();
                        editCourseName = course.getCourseName();
                        binding.editCourseId.setText(editCourseName);
                    }
                }
                return true;
            }
        });

        // عرض القائمة
        popupMenu.show();
    }


}