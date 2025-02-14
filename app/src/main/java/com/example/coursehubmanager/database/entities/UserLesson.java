package com.example.coursehubmanager.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "userLesson",
        primaryKeys = {"lessonId", "userId"},
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Course.class, parentColumns = "courseId", childColumns = "courseId", onDelete = ForeignKey.CASCADE)
        })
public class UserLesson {

    @ColumnInfo(name = "lessonId")
    private int lessonId;

    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "courseId")
    private int courseId;
//
//    @ColumnInfo(name = "isWatched")
//    private boolean isWatched;

    public UserLesson(int userId, int courseId, int lessonId/*, boolean isWatched*/) {
        this.userId = userId;
        this.courseId = courseId;
        this.lessonId = lessonId;
//        this.isWatched = isWatched;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
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

//    public boolean isWatched() {
//        return isWatched;
//    }
//
//    public void setIsWatched(boolean isWatched) {
//        isWatched = isWatched;
//    }


}