package com.example.todomvvm.screens.login.viewmodel;

import android.app.Application;

import com.example.todomvvm.data.session.SessionRepository;

import androidx.lifecycle.AndroidViewModel;

public class LoginRegisterViewModel extends AndroidViewModel {
    SessionRepository sessionRepository;

    public LoginRegisterViewModel(Application application) {
        super(application);
        sessionRepository = SessionRepository.getInstance(application);
    }


    public boolean registerUser(String email, String FirstName, String LastName, String password) {
        return sessionRepository.register(email, FirstName, LastName, password);
    }

    public boolean loginUser(String email, String password) {
        return sessionRepository.logIn(email, password);
    }

}
