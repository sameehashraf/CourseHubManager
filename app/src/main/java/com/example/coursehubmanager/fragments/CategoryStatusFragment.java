package com.example.coursehubmanager.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursehubmanager.activities.CourseDetailsActivity;
import com.example.coursehubmanager.helpers.SharedPreferencesHelper;
import com.example.coursehubmanager.adapters.CoursesProgressAdapter;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.dao.CourseDao;
import com.example.coursehubmanager.database.dao.LessonDao;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.databinding.FragmentCategoryStatusBinding;

import java.util.List;

public class CategoryStatusFragment extends Fragment {

    private FragmentCategoryStatusBinding binding;
    private CoursesProgressAdapter adapter;
    private MainViewModel viewModel;

    private static final String ARG_CATEGORY = "category";
    SharedPreferencesHelper prefsHelper;

    public static CategoryStatusFragment newInstance(int position) {
        CategoryStatusFragment fragment = new CategoryStatusFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoryStatusBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        prefsHelper = new SharedPreferencesHelper(requireContext());

        if (getArguments() != null) {
            boolean status = getArguments().getInt(ARG_CATEGORY) == 1;
            Log.d("TAG", "onCreateView:status " + status);
            binding.recCourses.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new CoursesProgressAdapter(viewModel);
            binding.recCourses.setAdapter(adapter);

            int userId = prefsHelper.getInt("userId", -1);
            if (status) {
                viewModel.getCompletedCourses(userId).observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
                    @Override
                    public void onChanged(List<Course> courses) {
                        adapter.setData(courses);
                    }
                });
            }else {
                viewModel.getUserCourses(userId).observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
                    @Override
                    public void onChanged(List<Course> courses) {
                        adapter.setData(courses);
                    }
                });
            }

            adapter.setOnItemClickListener((position, isIcon) -> {
                if (!isIcon) {
                    Intent intent = new Intent(requireContext(), CourseDetailsActivity.class);
                    intent.putExtra("courseId", position); // Pass data to the new activity
                    startActivity(intent);
                }
            });

        }
        return root;
    }

}