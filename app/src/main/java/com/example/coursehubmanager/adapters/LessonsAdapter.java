package com.example.coursehubmanager.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.entities.Lesson;
import com.example.coursehubmanager.helpers.OnItemClickListener;
import com.example.coursehubmanager.databinding.RecLessonsItemsBinding;
import com.example.coursehubmanager.helpers.OnLessonClickListener;

import java.util.ArrayList;
import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.ViewHolder> {

    private List<Lesson> items = new ArrayList<>();
    private OnLessonClickListener listener;

    // Setter for the listener
    public void setOnLessonClickListener(OnLessonClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecLessonsItemsBinding binding = RecLessonsItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lesson lesson = items.get(position);
        holder.binding.txtCourseName.setText(lesson.getLessonName());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onLessonClick(lesson);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final RecLessonsItemsBinding binding;
        public ViewHolder(RecLessonsItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Lesson> newData) {
        this.items = newData;
        notifyDataSetChanged();
    }
}
