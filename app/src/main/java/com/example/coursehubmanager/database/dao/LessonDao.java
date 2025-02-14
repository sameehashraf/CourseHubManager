package com.example.coursehubmanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursehubmanager.database.entities.Lesson;

import java.util.List;

@Dao
public interface LessonDao {
    @Insert
    void insertLesson(Lesson lesson);

    @Query("Select * From lessons")
    List<Lesson>getAll();

    @Query("SELECT * FROM lessons WHERE courseId=:courseId")
    LiveData<List<Lesson>> getLessonsByCourseId(int courseId);

    @Update
    void updateLesson(Lesson lesson);

    @Delete
    void deleteLesson(Lesson lesson);

    @Insert
    void insertLessons(List<Lesson> lessons);
    @Query("SELECT COUNT(*) FROM lessons WHERE courseId = :courseId")
    int getLessonCountForCourse(int courseId);



}