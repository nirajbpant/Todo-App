package com.example.todomvvm.data.user;

import android.content.Context;

import com.example.todomvvm.data.user.entity.UserEntity;

public class UserRepository {
    private static UserRepository instance;
    UserDao dao;

    public UserRepository(Context context) {
        UserDatabase database = UserDatabase.getInstance(context);
        dao = database.userDao();
    }

    public static UserRepository getInstance(Context context) {
        if (instance == null) {
            instance = new UserRepository(context);
        }
        return instance;
    }

    public void addUser(final String email, final String firstName, final String lastName, final String password) {
        UserDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertUser(new UserEntity(email, firstName, lastName, password));
            }
        });
    }

    public CheckInWrapper checkUser(String email, String password) {
        UserEntity user = dao.getUser(email, password);
        if (user == null) {
            return CheckInWrapper.notFound();
        } else {
            return CheckInWrapper.sucess(user.getEmail(), user.getFirstName(), user.getLastName());
        }

    }

    public boolean userExists(String email) {
        UserEntity user = dao.hasUser(email);
        return user != null;
    }
}
