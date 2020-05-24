package com.example.todomvvm.screens.splash.splashviewmodel;

import android.app.Application;

import com.example.todomvvm.data.session.SessionRepository;

import androidx.lifecycle.AndroidViewModel;

public class SplashViewModel extends AndroidViewModel {
    SessionRepository sessionRepository;

    public SplashViewModel(Application application) {
        super(application);
        sessionRepository = SessionRepository.getInstance(application);
    }

    public boolean isLoggedIn() {
        return sessionRepository.isLoggedIn();
    }
}
