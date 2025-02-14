package com.example.coursehubmanager.adapters;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coursehubmanager.helpers.OnItemClickListener;
import com.example.coursehubmanager.R;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.databinding.RecCourseItemsDeleteBinding;

import java.util.ArrayList;
import java.util.List;

public class EditCoursesAdapter extends RecyclerView.Adapter<EditCoursesAdapter.ViewHolder> {

    private List<Course> courses = new ArrayList<>();
    private OnItemClickListener listener;


    // Setter for the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecCourseItemsDeleteBinding binding = RecCourseItemsDeleteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Course course = courses.get(position);
        holder.binding.txtCourseName.setText(course.getCourseName());

        byte[] decodedString = Base64.decode(course.getCourseImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        Glide.with(holder.binding.getRoot())
                .load(decodedByte)
                    .placeholder(R.drawable.course)
                .into(holder.binding.imgCourse);

        holder.binding.imgEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position, false);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position, false);
            }
        });

        holder.binding.imgDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position, true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final RecCourseItemsDeleteBinding binding;

        public ViewHolder(RecCourseItemsDeleteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Course> newData/*, int prog*/) {
        this.courses = newData;
        notifyDataSetChanged();
    }
}
