package com.example.coursehubmanager.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "lessons",
        foreignKeys = @ForeignKey(entity = Course.class,
                parentColumns = "courseId",
                childColumns = "courseId",
                onDelete = ForeignKey.CASCADE))

public class Lesson {
    @PrimaryKey(autoGenerate = true)
    private int lessonId;

    @ColumnInfo(name = "courseId")
    private int courseId;

    @ColumnInfo(name = "lessonName")
    private String lessonName;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "youtubeLink")
    private String youtubeLink;

//    @ColumnInfo(name = "isWatched")
//    private boolean isWatched;
//
//    @ColumnInfo(name = "totalDuration")
//    private int totalDuration; // مدة الدرس بالثواني أو الدقائق
//
//    @ColumnInfo(name = "watchedDuration")
//    private int watchedDuration; // مدة المشاهدة
//    // Getters and Setters


    public Lesson(int courseId, String lessonName, String description, String youtubeLink
//                  boolean isWatched, int totalDuration, int watchedDuration
    ) {
        this.courseId = courseId;
        this.lessonName = lessonName;
        this.description = description;
        this.youtubeLink = youtubeLink;
        //just
//        this.isWatched = isWatched;
//        this.totalDuration = totalDuration;
//        this.watchedDuration = watchedDuration;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

//    public boolean isWatched() {
//        return isWatched;
//    }
//
//    public void setWatched(boolean watched) {
//        isWatched = watched;
//    }
//
//    public int getTotalDuration() {
//        return totalDuration;
//    }
//
//    public void setTotalDuration(int totalDuration) {
//        this.totalDuration = totalDuration;
//    }
//
//    public int getWatchedDuration() {
//        return watchedDuration;
//    }
//
//    public void setWatchedDuration(int watchedDuration) {
//        this.watchedDuration = watchedDuration;
//    }
//
//    public double getCompletionPercentage() {
//        return totalDuration > 0 ? ((double) watchedDuration / totalDuration) * 100 : 0;
//    }
}
