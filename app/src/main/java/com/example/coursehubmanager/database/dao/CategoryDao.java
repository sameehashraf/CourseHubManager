package com.example.coursehubmanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursehubmanager.database.entities.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    // إضافة تصنيف جديد
    @Insert
    void insertCategory(Category category);

    // جلب جميع التصنيفات
    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getAllCategories();


    @Query("SELECT * FROM categories WHERE categoryId =:categoryId")
    Category getCategoryById(int categoryId);

    //
//    // تحديث تصنيف
    @Update
    void updateCategory(Category category);

    // حذف تصنيف
    @Delete
    void deleteCategory(Category category);

    @Insert
    void insertCategories(List<Category> categories);
}
