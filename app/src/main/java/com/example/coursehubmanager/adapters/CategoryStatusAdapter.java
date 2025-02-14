package com.example.coursehubmanager.adapters;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.coursehubmanager.database.entities.Category;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.fragments.CategoryFragment;
import com.example.coursehubmanager.fragments.CategoryStatusFragment;

import java.util.ArrayList;
import java.util.List;

public class CategoryStatusAdapter extends FragmentStateAdapter {

    public CategoryStatusAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return CategoryStatusFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}
