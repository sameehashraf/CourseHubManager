package com.example.coursehubmanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User login(String email, String password);

    @Query("SELECT * FROM users WHERE userId = :userId")
    User getUserById(int userId);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Insert
    void insertUsers(List<User> users);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();

}
