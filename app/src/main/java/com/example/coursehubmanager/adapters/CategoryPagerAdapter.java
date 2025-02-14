package com.example.coursehubmanager.adapters;


import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.coursehubmanager.database.entities.Category;
import com.example.coursehubmanager.fragments.CategoryFragment;

import java.util.ArrayList;
import java.util.List;

public class CategoryPagerAdapter extends FragmentStateAdapter {

    private final List<Category> categories= new ArrayList<>();

    public CategoryPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return CategoryFragment.newInstance(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setData(List<Category> newData) {
        categories.clear();
        categories.addAll(newData);
        notifyDataSetChanged();
    }
}