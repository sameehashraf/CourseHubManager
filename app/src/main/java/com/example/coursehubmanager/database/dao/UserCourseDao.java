package com.example.coursehubmanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.entities.User;
import com.example.coursehubmanager.database.entities.UserCourse;

import java.util.List;

@Dao
public interface UserCourseDao {
    @Insert
    void insertUserCourse(UserCourse userCourse);

    @Transaction
    @Query("SELECT * FROM courses " +
            "INNER JOIN userCourse ON courses.courseId = userCourse.courseId " +
            "WHERE userCourse.userId = :userId AND userCourse.progress <= 100")
    LiveData<List<Course>> getAllUserCourses(int userId);

    @Query("SELECT progress FROM userCourse WHERE courseId = :courseId AND userId = :userId")
    Double getProgress(int courseId, int userId);

    @Transaction
    @Query("SELECT * FROM courses " +
            "INNER JOIN userCourse ON courses.courseId = userCourse.courseId " +
            "WHERE userCourse.userId = :userId AND userCourse.progress >= 100 ")
    LiveData<List<Course>> getCompletedCourses(int userId);


    // Query: Update progress for a specific user and course
    @Query("UPDATE userCourse SET progress = :progress WHERE userId = :userId AND courseId = :courseId")
    void updateProgress(int userId, int courseId, double progress);

}
