package com.example.coursehubmanager.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses",
        foreignKeys = @ForeignKey(entity = Category.class,
                parentColumns = "categoryId",
                childColumns = "categoryId",
                onDelete = ForeignKey.CASCADE))

public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseId;

    @ColumnInfo(name = "courseName")
    private String courseName;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "mentorName")
    private String mentorName;

    @ColumnInfo(name = "categoryId")
    private int categoryId;

//    @ColumnInfo(name = "isCompleted")
//    private boolean isCompleted;

    @ColumnInfo(name = "price")
    private double price; // سعر الكورس

    @ColumnInfo(name = "studentCount")
    private int studentCount; // عدد الطلاب

    @ColumnInfo(name = "hours")
    private int hours; // عدد الساعات

    @ColumnInfo(name = "courseImage")
    private String courseImage; // رابط الصورة

//    @ColumnInfo(name = "percentage")
//    private float percentage; // مواضيع الكورس


    // Getters and Setters

    public Course(){}
    public Course(int courseId,String courseName, String description, String mentorName,
                  int categoryId
//            , boolean isCompleted
            , double price, int studentCount,
                  int hours, String courseImage
//            , float percentage
    ) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.description = description;
        this.mentorName = mentorName;
        this.categoryId = categoryId;
//        this.isCompleted = isCompleted;
        this.price = price;
        this.studentCount = studentCount;
        this.hours = hours;
        this.courseImage = courseImage;
//        this.percentage = percentage;
    }
    public Course(String courseName, String description, String mentorName,
                  int categoryId
//            , boolean isCompleted
            , double price, int studentCount,
                  int hours, String courseImage
//            , float percentage
    ) {
        this.courseName = courseName;
        this.description = description;
        this.mentorName = mentorName;
        this.categoryId = categoryId;
//        this.isCompleted = isCompleted;
        this.price = price;
        this.studentCount = studentCount;
        this.hours = hours;
        this.courseImage = courseImage;
//        this.percentage = percentage;
    }



    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

//    public boolean isCompleted() {
//        return isCompleted;
//    }
//
//    public void setCompleted(boolean isCompleted) {
//        this.isCompleted = isCompleted;
//    }
//
//    public float getPercentage() {
//        return percentage;
//    }
//
//    public void setPercentage(float percentage) {
//        this.percentage = percentage;
//    }
}