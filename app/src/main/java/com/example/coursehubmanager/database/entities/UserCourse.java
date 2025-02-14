package com.example.coursehubmanager.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "userCourse",
        primaryKeys = {"userId", "courseId"},
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Course.class, parentColumns = "courseId", childColumns = "courseId", onDelete = ForeignKey.CASCADE)
        })
public class UserCourse {
    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "courseId")
    private int courseId;

    @ColumnInfo(name = "progress")
    private double progress;

    public UserCourse(int userId, int courseId, double progress) {
        this.userId = userId;
        this.courseId = courseId;
        this.progress = progress;
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

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

}