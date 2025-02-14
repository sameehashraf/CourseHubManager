package com.example.coursehubmanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.coursehubmanager.database.entities.UserLesson;

import java.util.List;

@Dao
public interface UserLessonDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUserLesson(UserLesson userLesson);

    @Query("SELECT * FROM userLesson WHERE userId = :userId")
    LiveData<List<UserLesson>> getAllUserLessons(int userId);

}
