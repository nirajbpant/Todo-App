package com.example.todomvvm.data.user;


import com.example.todomvvm.data.user.entity.UserEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Query("select * from users where email= :email and password= :password")
    UserEntity getUser(String email, String password);

    @Insert
    void insertUser(UserEntity user);
}
