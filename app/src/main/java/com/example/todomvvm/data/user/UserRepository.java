package com.example.todomvvm.data.user;

import com.example.todomvvm.data.user.entity.UserEntity;

import androidx.room.Query;

public class UserRepository {
    UserDao dao;
    public UserRepository(UserDatabase userDatabase){
        dao= userDatabase.userDao();
    }
    public void addUser(final String email, final String firstName, final String lastName, final String password){
        UserDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertUser(new UserEntity(email, firstName, lastName, password));
            }
        });
    }
    public CheckInWrapper checkUser(String email, String password){
        UserEntity user= dao.getUser(email, password);
        if(user== null){
           return CheckInWrapper.notFound();
        }else{
            return CheckInWrapper.sucess(user.getEmail(),user.getFirstName(),user.getLastName());
        }

    }
}
