package com.example.coursehubmanager.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.coursehubmanager.database.dao.BookmarkedCoursesDao;
import com.example.coursehubmanager.database.dao.UserCourseDao;
import com.example.coursehubmanager.database.dao.UserLessonDao;
import com.example.coursehubmanager.database.entities.BookmarkedCourses;
import com.example.coursehubmanager.database.entities.UserCourse;
import com.example.coursehubmanager.database.entities.UserLesson;
import com.example.coursehubmanager.helpers.MyApplication;
import com.example.coursehubmanager.database.dao.CategoryDao;
import com.example.coursehubmanager.database.dao.CourseDao;
import com.example.coursehubmanager.database.dao.LessonDao;
import com.example.coursehubmanager.database.dao.UserDao;
import com.example.coursehubmanager.database.entities.Category;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.entities.Lesson;
import com.example.coursehubmanager.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Course.class, Lesson.class, Category.class,
        UserCourse.class, UserLesson.class, BookmarkedCourses.class}
        , version = 1)

public abstract class AppDatabase extends RoomDatabase {


    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();

    public abstract CourseDao courseDao();

    public abstract LessonDao lessonDao();

    public abstract CategoryDao categoryDao();

    public abstract UserCourseDao userCourseDao();

    public abstract UserLessonDao userLessonDao();

    public abstract BookmarkedCoursesDao bookmarkedCoursesDao();


    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {

                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .addCallback(databaseCallback) // إضافة الكال باك
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static final RoomDatabase.Callback databaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // تشغيل كود لإضافة بيانات وهمية
            Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase database = getInstance(MyApplication.getInstance().getApplicationContext());
                populateFakeData(database);
            });
        }

    };

    private static void populateFakeData(AppDatabase database) {
        // المستخدمين
        List<User> users = new ArrayList<>();
        users.add(new User("Sameeh", "Sameeh@gmail.com", "123123123"));
        users.add(new User("Mohammed", "mohammed@gmail.com", "123123123"));
        users.add(new User("Admin", "Admin@Admin.com", "AdminAdmin"));
        users.add(new User("Sara", "sara@example.com", "sara123123"));
        users.add(new User("Ali", "ali@example.com", "ali123123"));
        users.add(new User("Laila", "laila@example.com", "laila123"));
        users.add(new User("Omar", "omar@example.com", "omar123123"));
        database.userDao().insertUsers(users);

        // التصنيفات
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Programming"));
        categories.add(new Category("Design"));
        categories.add(new Category("Business"));
        categories.add(new Category("Photography"));
        categories.add(new Category("Content Creation"));
        categories.add(new Category("Translating"));
        categories.add(new Category("Science"));
        categories.add(new Category("Marketing"));
        categories.add(new Category("Finance"));
        categories.add(new Category("Music"));
        database.categoryDao().insertCategories(categories);


        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "Java Programming", "Learn Java basics.", "Dr. Mahmoud", 1/*, false*/, 49.99, 30, 130, ""/*, 0*/));
        courses.add(new Course(2, "Android Development", "Build Android apps.", "Dr. Yousef", 1/*, false*/, 59.99, 40, 135, ""/*, 0*/));
        courses.add(new Course(3, "UX Design", "Learn UX principles.", "Dr. Mohammed", 2/*, false*/, 29.99, 20, 120, ""/*, 0*/));
        courses.add(new Course(4, "Python Programming", "Learn Python from scratch.", "Dr. Ali", 1/*, false*/, 39.99, 25, 110, ""/*, 0*/));
        courses.add(new Course(5, "Data Science", "Introduction to Data Science.", "Dr. Sarah", 1/*, false*/, 79.99, 50, 150, ""/*, 0*/));
        courses.add(new Course(6, "Graphic Design", "Master the basics of design.", "Dr. Noor", 2/*, false*/, 45.99, 15, 100, ""/*, 0*/));
        courses.add(new Course(7, "Web Development", "Build responsive websites.", "Dr. Khaled", 1/*, true*/, 55.99, 35, 125, ""/*, 100*/));
        courses.add(new Course(8, "Marketing 101", "Learn the fundamentals of marketing.", "Dr. Faris", 3/*, true*/, 39.99, 20, 100, ""/*, 100*/));
        courses.add(new Course(9, "Photography for Beginners", "Learn basic photography techniques.", "Dr. Yasmine", 4/*, true*/, 29.99, 10, 85, ""/*, 100*/));
        courses.add(new Course(10, "Financial Accounting", "Learn the basics of accounting.", "Dr. Zain", 5/*, false*/, 59.99, 45, 140, ""/*, 0*/));
        database.courseDao().insertCourses(courses);



        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson(1, "Introduction to Java", "Java Basics", "https://youtube.com/java_intro"/*, false, 100, 20*/));
        lessons.add(new Lesson(1, "OOP Concepts", "Object-Oriented Programming", "https://youtube.com/oop"/*, false, 100, 80*/));
        lessons.add(new Lesson(2, "Android Basics", "Android Components", "https://youtube.com/android_basics"/*, false, 100, 100*/));
        lessons.add(new Lesson(3, "Python Basics", "Introduction to Python", "https://youtube.com/python_basics"/*, false, 100, 40*/));
        lessons.add(new Lesson(4, "Data Preprocessing", "Data Cleaning and Preprocessing", "https://youtube.com/data_preprocessing"/*, false, 100, 60*/));
        lessons.add(new Lesson(5, "Design Principles", "Basic Design Concepts", "https://youtube.com/design_principles"/*, false, 100, 30*/));
        lessons.add(new Lesson(6, "HTML and CSS", "Create static web pages", "https://youtube.com/html_css"/*, false, 100, 50*/));
        lessons.add(new Lesson(7, "Digital Marketing", "Introduction to digital marketing", "https://youtube.com/digital_marketing"/*, false, 100, 90*/));
        lessons.add(new Lesson(8, "Photography Basics", "Camera settings and shooting techniques", "https://youtube.com/photography_basics"/*, false, 100, 25*/));
        lessons.add(new Lesson(9, "Accounting Principles", "Basic financial concepts", "https://youtube.com/accounting_basics"/*, false, 100, 100*/));
        database.lessonDao().insertLessons(lessons);

    }


}
