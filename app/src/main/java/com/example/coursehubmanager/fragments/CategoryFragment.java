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
import com.example.coursehubmanager.adapters.CoursesAdapter;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.dao.CourseDao;
import com.example.coursehubmanager.database.entities.Category;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.databinding.FragmentCategoryBinding;

import java.util.List;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    private CoursesAdapter adapter;
    private AppDatabase db;
    private MainViewModel viewModel;
    private static final String ARG_CATEGORY = "category";
    SharedPreferencesHelper prefsHelper;

    public static CategoryFragment newInstance(Category category) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = AppDatabase.getInstance(requireContext());

        prefsHelper = new SharedPreferencesHelper(requireContext());
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        RecyclerView recyclerView = binding.recCourses;

        if (getArguments() != null) {
            Category category = getArguments().getParcelable(ARG_CATEGORY);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recCourses.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new CoursesAdapter(viewModel);
            binding.recCourses.setAdapter(adapter);

            viewModel.getCoursesByCategoryId(category.getCategoryId()).observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
                @Override
                public void onChanged(List<Course> courses) {
                    adapter.setData(courses);
                }
            });

            adapter.setOnItemClickListener((position, isIcon) -> {
                if (!isIcon) {
                    Intent intent = new Intent(requireContext(), CourseDetailsActivity.class);
                    intent.putExtra("courseId", position); // Pass data to the new activity
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        }

        return root;
    }

}