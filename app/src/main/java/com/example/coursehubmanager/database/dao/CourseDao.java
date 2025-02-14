package com.example.coursehubmanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursehubmanager.database.entities.Course;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert
    void insert(Course course);

    @Query("SELECT * FROM courses")
    LiveData<List<Course>> getAllCourses();

    // تحديث كورس
    @Update
    void updateCourse(Course course);

    // حذف كورس
    @Query("DELETE FROM courses WHERE courseId = :courseId")
    void deleteCourse(int courseId);

    // جلب الكورسات حسب التصنيف
    @Query("SELECT * FROM courses WHERE categoryId = :categoryId")
    LiveData<List<Course>> getCoursesByCategory(int categoryId);

    // جلب الكورسات حسب التصنيف
    @Query("SELECT * FROM courses WHERE courseId = :courseId")
    Course getCourseById(int courseId);

    @Insert
    void insertCourses(List<Course> courses);
}