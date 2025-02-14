package com.example.coursehubmanager.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.coursehubmanager.activities.BookmarksActivity;
import com.example.coursehubmanager.R;
import com.example.coursehubmanager.adapters.CategoryPagerAdapter;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.dao.CategoryDao;
import com.example.coursehubmanager.database.entities.Category;
import com.example.coursehubmanager.database.entities.User;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.databinding.FragmentHomeBinding;
import com.example.coursehubmanager.helpers.SharedPreferencesHelper;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private CategoryPagerAdapter adapter;
    //    private List<String> categories;
    private List<Category> categories;
    private CategoryDao dao;
    private AppDatabase db;
    private MainViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = AppDatabase.getInstance(requireContext());
        dao = db.categoryDao();
        SharedPreferencesHelper prefsHelper = new SharedPreferencesHelper(requireContext());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        tabLayout = binding.tabLayout;
        viewPager = binding.viewPager;
        int userId = prefsHelper.getInt("userId", -1);

        User user = viewModel.getUserById(userId);
        binding.txtTitle.setText(getString(R.string.hello) + " " + user.getUserName());
        adapter = new CategoryPagerAdapter(requireActivity());
        viewPager.setAdapter(adapter);

        binding.imgBookmark.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), BookmarksActivity.class));
        });

        viewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            if (categories != null && !categories.isEmpty()) {
                adapter.setData(categories);

                new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
                    tab.setCustomView(R.layout.tab_item_chip);
                    TextView tabText = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tab_item_chip);
                    tabText.setText(categories.get(position).getCategoryName());
                    tab.setCustomView(tabText);

                }).attach();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}