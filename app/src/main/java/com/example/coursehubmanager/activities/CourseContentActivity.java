package com.example.coursehubmanager.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.coursehubmanager.adapters.EditCoursesAdapter;
import com.example.coursehubmanager.adapters.LessonsAdapter;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.dao.LessonDao;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.entities.Lesson;
import com.example.coursehubmanager.database.entities.UserCourse;
import com.example.coursehubmanager.database.entities.UserLesson;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.databinding.ActivityCourseContentBinding;
import com.example.coursehubmanager.helpers.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

public class CourseContentActivity extends AppCompatActivity {
    ActivityCourseContentBinding binding;
    private LessonsAdapter adapter;
    private AppDatabase db;
    SharedPreferencesHelper prefsHelper;
    private MainViewModel viewModel;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = AppDatabase.getInstance(this);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        prefsHelper = new SharedPreferencesHelper(this);

        courseId = getIntent().getIntExtra("courseId", -1);

        Log.d("TAG", "onCreate:id " + courseId);
        binding.imgBack.setOnClickListener(v -> finish());

        binding.recLessons.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LessonsAdapter();
        binding.recLessons.setAdapter(adapter);

        viewModel.getLessonsForCourse(courseId).observe(this, new Observer<List<Lesson>>() {
            @Override
            public void onChanged(List<Lesson> lessons) {
                Log.d("TAG", "onChanged:lessons.size() " + lessons.size());
                adapter.setData(lessons);
            }
        });

        adapter.setOnLessonClickListener(lesson -> {
            Log.d("TAG", "setWatched:getLessonName "+lesson.getLessonName());
            Log.d("TAG", "setWatched:getLessonId "+lesson.getLessonId());
            setWatched(lesson);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(lesson.getYoutubeLink()));
            intent.setPackage("com.google.android.youtube");
            startActivity(intent);
        });

    }

    public void setWatched(Lesson lesson) {
        int userId = prefsHelper.getInt("userId", -1);
        UserLesson userLesson = new UserLesson(userId, courseId, lesson.getLessonId());
        viewModel.addUserLesson(userLesson);
        int totalLessons = viewModel.getLessonCountForCourse(lesson.getCourseId());
        double oldProgress = viewModel.getProgress(courseId, userId);
        double lessonPercentageOfCourse = (double) 100 / totalLessons;
        lessonPercentageOfCourse += oldProgress;

        viewModel.updateCoursePercentage(userId, lesson.getCourseId(), lessonPercentageOfCourse);
    }
}