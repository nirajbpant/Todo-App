package com.example.todomvvm.screens.login.viewmodel;

import android.app.Application;

import com.example.todomvvm.data.session.SessionRepository;

import androidx.lifecycle.AndroidViewModel;

public class LogoutViewModel extends AndroidViewModel {
    SessionRepository sessionRepository;
    public LogoutViewModel(Application application) {
        super(application);
        sessionRepository = SessionRepository.getInstance(application);
    }
    public boolean LogOutUser(){
        return sessionRepository.logout();
    }
}
