package com.example.coursehubmanager.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.coursehubmanager.R;
import com.example.coursehubmanager.adapters.CategoryPagerAdapter;
import com.example.coursehubmanager.adapters.CategoryStatusAdapter;
import com.example.coursehubmanager.databinding.FragmentMyCoursesBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MyCoursesFragment extends Fragment {

    private FragmentMyCoursesBinding binding;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyCoursesBinding.inflate(inflater, container, false);
        tabLayout = binding.tabLayout2;
        viewPager = binding.viewPager2;

        CategoryStatusAdapter adapter = new CategoryStatusAdapter(requireActivity());
        viewPager.setAdapter(adapter);


        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText(R.string.ongoing);
            } else if (position == 1) {
                tab.setText(R.string.completed);
            }
        }).attach();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}