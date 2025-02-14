package com.example.coursehubmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursehubmanager.R;
import com.example.coursehubmanager.database.entities.BookmarkedCourses;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.entities.UserCourse;
import com.example.coursehubmanager.database.entities.UserLesson;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.databinding.ActivityCourseDetailsBinding;
import com.example.coursehubmanager.helpers.SharedPreferencesHelper;

public class CourseDetailsActivity extends AppCompatActivity {
    ActivityCourseDetailsBinding binding;
    MainViewModel viewModel;
    Course course;

    SharedPreferencesHelper prefsHelper;
    int userId;
    int courseId;
    boolean isBookmarked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCourseDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        prefsHelper = new SharedPreferencesHelper(this);
        userId = prefsHelper.getInt("userId", -1);
        courseId = getIntent().getIntExtra("courseId", -1);
        Log.d("TAG", "onCreate:getCourseById id " + courseId);
        course = viewModel.getCourseById(courseId);
        if (course != null) {
            binding.txtTitle.setText(course.getCourseName());
            binding.txtMentor.setText(course.getMentorName());
            binding.txtPrice.setText("$" + course.getPrice());
            binding.txtStudents.setText(String.valueOf(course.getStudentCount()));
            binding.txthours.setText(String.valueOf(course.getHours()));
            binding.txtAbout.setText(course.getDescription());
        }
        binding.imgBack.setOnClickListener(v -> finish());

        binding.btnShowCourseContent.setOnClickListener(v -> {
            Intent intent = new Intent(this, CourseContentActivity.class);
            intent.putExtra("courseId", courseId); // Pass data to the new activity
            startActivity(intent);
        });

        isBookmarked = viewModel.isCourseBookmarked(courseId, userId);

        binding.btnJoinCourse.setOnClickListener(v -> {
            UserCourse userCourse = new UserCourse(userId, courseId, 0);
            viewModel.addUserCourse(userCourse);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        binding.imgBookmark.setImageResource(isBookmarked ? R.drawable.bookmark_filled : R.drawable.bookmark_outlined);
        binding.imgBookmark.setOnClickListener(v -> {
            isBookmarked = !isBookmarked;
            viewModel.insertBookmarkedCourse(new BookmarkedCourses(userId, courseId, true));
            viewModel.addOrDeleteBookmarks(course.getCourseId(), userId, isBookmarked);
            binding.imgBookmark.setImageResource(isBookmarked
                    ? R.drawable.bookmark_filled : R.drawable.bookmark_outlined
            );
        });
    }
}