package com.example.coursehubmanager.database.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.coursehubmanager.database.entities.BookmarkedCourses;
import com.example.coursehubmanager.database.entities.User;
import com.example.coursehubmanager.database.entities.UserCourse;
import com.example.coursehubmanager.database.entities.UserLesson;
import com.example.coursehubmanager.helpers.MyApplication;
import com.example.coursehubmanager.helpers.SharedPreferencesHelper;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.entities.Category;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.entities.Lesson;

import java.util.List;
import java.util.Objects;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Category>> categories;
    private LiveData<List<Course>> courses;
    private LiveData<List<Course>> bookmarkedCourses;
    public LiveData<User> user = new MutableLiveData<>(new User());
    //    private Course course;
    private AppDatabase db;
    public MutableLiveData<Integer> userId = new MutableLiveData<>(-1);

    public MainViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getInstance(MyApplication.getInstance().getApplicationContext());
        categories = db.categoryDao().getAllCategories();
        courses = db.courseDao().getAllCourses();

    }


    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public LiveData<List<User>> getUsers() {
        return db.userDao().getAllUsers();
    }

    public LiveData<List<Course>> getCourses() {
        return courses;
    }

    public LiveData<List<Course>> getCoursesByCategoryId(int id) {
        return db.courseDao().getCoursesByCategory(id);
    }

    public LiveData<List<Course>> getCompletedCourses(int userId) {
        return db.userCourseDao().getCompletedCourses(userId);
    }

    public Course getCourseById(int courseId) {
        return db.courseDao().getCourseById(courseId);
    }

    public Category getCategoryById(int categoryId) {
        return db.categoryDao().getCategoryById(categoryId);
    }

    public void addOrDeleteBookmarks(int courseId, int userId, boolean isBookmarked) {
        db.bookmarkedCoursesDao().updateBookmarkStatus(userId, courseId, isBookmarked);
    }

    public void insertBookmarkedCourse(BookmarkedCourses bookmarkedCourses) {
        db.bookmarkedCoursesDao().insertCourse(bookmarkedCourses);
    }

    public boolean isCourseBookmarked(int courseId, int userId) {
        return db.bookmarkedCoursesDao().isCourseBookmarked(userId, courseId);
    }

    public User getUserById(int email) {
        return db.userDao().getUserById(email);
    }

    public LiveData<List<Course>> getUserCourses(int courseId) {
        return db.userCourseDao().getAllUserCourses(courseId);
    }

    public Double getProgress(int courseId,int userId) {
        return db.userCourseDao().getProgress(courseId,userId);
    }

    public LiveData<List<Course>> getAllBookmarkedCourses(int userId) {
        bookmarkedCourses = db.bookmarkedCoursesDao().getAllBookmarkedCourses(userId);
        return bookmarkedCourses;
    }

    public LiveData<List<Lesson>> getLessonsForCourse(int courseId) {
        return db.lessonDao().getLessonsByCourseId(courseId);
    }

    public int getLessonCountForCourse(int courseId) {
        return db.lessonDao().getLessonCountForCourse(courseId);
    }
    public void updateCoursePercentage(int userId, int courseId, double percentage) {
        db.userCourseDao().updateProgress(userId, courseId, percentage);
    }


    public void updateUser(User user) {
        db.userDao().updateUser(user);
    }

    public void addCategory(Category category) {
        db.categoryDao().insertCategory(category);
    }

    public void updateCategory(Category category) {
        db.categoryDao().updateCategory(category);
    }

    public void addLesson(Lesson lesson) {
        db.lessonDao().insertLesson(lesson);
    }

    public void addCourse(Course course) {
        db.courseDao().insert(course);
    }

    public void addUserCourse(UserCourse userCourse) {
        db.userCourseDao().insertUserCourse(userCourse);
    }

    public void addUserLesson(UserLesson userLesson) {
        db.userLessonDao().insertUserLesson(userLesson);
    }

    public void deleteCourse(int courseId) {
        db.courseDao().deleteCourse(courseId);
    }

}
