package com.example.coursehubmanager.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.coursehubmanager.helpers.OnItemClickListener;
import com.example.coursehubmanager.R;
import com.example.coursehubmanager.adapters.EditCoursesAdapter;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.databinding.ActivityViewCoursesBinding;

import java.util.List;
import java.util.concurrent.Executors;

public class ViewCoursesActivity extends AppCompatActivity {
    ActivityViewCoursesBinding binding;
    MainViewModel viewModel;
    AppDatabase db;
    EditCoursesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewCoursesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = AppDatabase.getInstance(this);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.imgBack.setOnClickListener(v -> finish());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EditCoursesAdapter();
        binding.recyclerView.setAdapter(adapter);

        viewModel.getCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                adapter.setData(courses);
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int id, boolean isIcon) {
                if (isIcon) {
                    // DELETE
                    showMyDialog(id);
                } else {
                    //  EDIT
                    Intent intent = new Intent(getBaseContext(), EditCourseActivity.class);
                    intent.putExtra("courseId", id); // Pass data to the new activity
                    startActivity(intent);
                }
            }
        });
    }

    private void showMyDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.warningMessage);
        builder.setMessage(R.string.are_you_sure_you_want_to_delete_this_course);
        builder.setPositiveButton(R.string.yes, (dialog, which) -> {
            Executors.newSingleThreadExecutor().execute(() -> {
                viewModel.deleteCourse(id);
            });
            showToast(getString(R.string.operetaion_completed));
        });
        builder.setNegativeButton(R.string.no, (dialog, which) -> {
            showToast(getString(R.string.operetaion_canceled));
        });
        builder.setNeutralButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.create().show();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}