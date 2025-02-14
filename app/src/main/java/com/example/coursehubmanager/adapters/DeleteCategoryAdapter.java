package com.example.coursehubmanager.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursehubmanager.helpers.OnCategoryClickListener;
import com.example.coursehubmanager.database.entities.Category;
import com.example.coursehubmanager.databinding.TabItemChipDeleteBinding;

import java.util.ArrayList;
import java.util.List;

public class DeleteCategoryAdapter extends RecyclerView.Adapter<DeleteCategoryAdapter.ViewHolder> {

    private List<Category> items= new ArrayList<>();
    private OnCategoryClickListener<Category> listener;

    public void setOnItemClickListener(OnCategoryClickListener<Category> listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TabItemChipDeleteBinding binding = TabItemChipDeleteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.getRoot().setText(items.get(position).getCategoryName());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(items.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TabItemChipDeleteBinding binding;

        public ViewHolder(TabItemChipDeleteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Category> newData/*, int prog*/) {
        this.items = newData;
        notifyDataSetChanged();
    }
}
