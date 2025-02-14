package com.example.coursehubmanager.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "bookmarkedCourses",
        primaryKeys = {"userId", "courseId"},
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Course.class, parentColumns = "courseId", childColumns = "courseId", onDelete = ForeignKey.CASCADE)
        })
public class BookmarkedCourses {
    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "courseId")
    private int courseId;

    @ColumnInfo(name = "isBookmarked")
    private boolean isBookmarked;

    public BookmarkedCourses(int userId, int courseId,boolean isBookmarked) {
        this.userId = userId;
        this.courseId = courseId;
        this.isBookmarked = isBookmarked;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

}