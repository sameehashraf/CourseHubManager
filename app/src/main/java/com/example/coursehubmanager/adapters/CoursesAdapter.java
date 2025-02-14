package com.example.coursehubmanager.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coursehubmanager.R;
import com.example.coursehubmanager.activities.BookmarksActivity;
import com.example.coursehubmanager.database.entities.BookmarkedCourses;
import com.example.coursehubmanager.database.entities.Category;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.helpers.MyApplication;
import com.example.coursehubmanager.helpers.OnItemClickListener;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.databinding.RecCourseItemsBinding;
import com.example.coursehubmanager.helpers.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {

    private List<Course> courses = new ArrayList<>();
    private OnItemClickListener listener;
    String categoryName = "";
    boolean isBookmarked;
    MainViewModel viewModel;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public CoursesAdapter(MainViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public boolean isCourseBookmarked(int userId, int courseId) {
        return viewModel.isCourseBookmarked(courseId, userId);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecCourseItemsBinding binding = RecCourseItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SharedPreferencesHelper prefsHelper = new SharedPreferencesHelper(MyApplication.getInstance().getApplicationContext());
        Course course = courses.get(position);
        int userId = prefsHelper.getInt("userId", -1);
        int courseId = course.getCourseId();
        isBookmarked = isCourseBookmarked(userId, courseId);

        Category category = viewModel.getCategoryById(course.getCategoryId());

        holder.binding.txtCourseName.setText(course.getCourseName());
        holder.binding.txtCategory.setText(category.getCategoryName());
        holder.binding.txtTeacherName.setText(course.getMentorName());

        byte[] decodedString = Base64.decode(course.getCourseImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        Glide.with(holder.binding.getRoot())
                .load(decodedByte)
                .placeholder(R.drawable.course)
                .into(holder.binding.imgCourse);

        holder.binding.imgBookmark.setImageResource(isBookmarked ? R.drawable.bookmark_filled : R.drawable.bookmark_outlined);

        holder.binding.imgBookmark.setOnClickListener(v -> {
            isBookmarked = !isBookmarked;
            viewModel.insertBookmarkedCourse(new BookmarkedCourses(userId, courseId, true));
            viewModel.addOrDeleteBookmarks(course.getCourseId(), userId, isBookmarked);
            holder.binding.imgBookmark.setImageResource(isBookmarked ? R.drawable.bookmark_filled : R.drawable.bookmark_outlined);
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(course.getCourseId(), false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final RecCourseItemsBinding binding;

        public ViewHolder(RecCourseItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Course> newData) {
        this.courses = newData;
        notifyDataSetChanged();
    }
}
