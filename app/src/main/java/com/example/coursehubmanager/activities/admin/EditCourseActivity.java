package com.example.coursehubmanager.activities.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursehubmanager.R;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.entities.Category;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.databinding.ActivityEditCourseBinding;

import java.util.List;
import java.util.concurrent.Executors;

public class EditCourseActivity extends AppCompatActivity {
    ActivityEditCourseBinding binding;
    MainViewModel viewModel;
    AppDatabase db;

    String editCourseName;
    String editHours;
    String editPrice;
    String editMentorName;
    String editAbout;
    int courseId;
    int categoryId = -1;
    Course course = new Course();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = AppDatabase.getInstance(this);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        courseId = getIntent().getIntExtra("courseId", -1);
        binding.imgBack.setOnClickListener(v -> finish());
        Executors.newSingleThreadExecutor().execute(() -> {
            course = viewModel.getCourseById(courseId);
            categoryId = course.getCategoryId();
            binding.editCourseName.setText(course.getCourseName());
            binding.editMentorName.setText(course.getMentorName());
            binding.editPrice.setText(String.valueOf(course.getPrice()));
            binding.editHours.setText(String.valueOf(course.getHours()));
            binding.editAbout.setText(course.getDescription());

        });
        binding.btnEditCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCourseName = binding.editCourseName.getText().toString().trim();
                editHours = binding.editHours.getText().toString().trim();
                editPrice = binding.editPrice.getText().toString().trim();
                editMentorName = binding.editMentorName.getText().toString().trim();
                editAbout = binding.editAbout.getText().toString().trim();

                Executors.newSingleThreadExecutor().execute(() -> {
                    if (isValid(editCourseName, editHours, editPrice, editMentorName, editAbout)) {
                        course = new Course(editCourseName, editAbout, editMentorName, categoryId,
                                 Integer.parseInt(editPrice), 0,
                                Integer.parseInt(editHours), String.valueOf(R.drawable.course));
                        course.setCourseId(courseId);
                        viewModel.addCourse(course);
                    }
                });
                Toast.makeText(EditCourseActivity.this, R.string.added_successfully, Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }

    private boolean isValid(String editCourseName,
                            String editHours,
                            String editPrice,
                            String editMentorName,
                            String editAbout) {
        if (
                editCourseName.isEmpty() ||
                        editHours.isEmpty() || editPrice.isEmpty() ||
                        editMentorName.isEmpty() || editAbout.isEmpty()
        ) {
            showError(getString(R.string.data_cant_be_empty));
            return false;
        }
        if (categoryId == -1) {
            showError(getString(R.string.select_Category));
            return false;
        }

        return true;
    }

    private void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }


}