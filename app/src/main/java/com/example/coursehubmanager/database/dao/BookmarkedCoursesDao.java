package com.example.coursehubmanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.coursehubmanager.database.entities.BookmarkedCourses;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.entities.UserCourse;

import java.util.List;

@Dao
public interface BookmarkedCoursesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCourse(BookmarkedCourses bookmarkedCourses);

    @Transaction
    @Query("SELECT * FROM courses " +
            "INNER JOIN bookmarkedCourses ON courses.courseId = bookmarkedCourses.courseId " +
            "WHERE bookmarkedCourses.userId = :userId AND bookmarkedCourses.isBookmarked = 1")
    LiveData<List<Course>> getAllBookmarkedCourses(int userId);

    @Query("UPDATE bookmarkedCourses SET isBookmarked = :isBookmarked WHERE userId = :userId AND courseId = :courseId")
    void updateBookmarkStatus(int userId, int courseId, boolean isBookmarked);

    // Query: Check if a course is bookmarked by a user
    @Query("SELECT isBookmarked FROM bookmarkedCourses WHERE userId = :userId AND courseId = :courseId")
    boolean isCourseBookmarked(int userId, int courseId);

}
