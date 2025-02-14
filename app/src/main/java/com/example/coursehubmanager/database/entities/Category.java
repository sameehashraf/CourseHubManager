package com.example.coursehubmanager.database.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int categoryId;

    @ColumnInfo(name = "categoryName")
    private String categoryName;


    // Getters and Setters


    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    // Parcelable constructor
    protected Category(Parcel in) {
        categoryId = in.readInt();
        categoryName = in.readString();
    }


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // واجهة Parcelable
    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(categoryId);
        parcel.writeString(categoryName);
    }
}