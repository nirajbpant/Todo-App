package com.example.todomvvm.data.session;

import android.content.Context;

import com.example.todomvvm.data.user.CheckInWrapper;
import com.example.todomvvm.data.user.UserRepository;

public class SessionRepository {
    private static SessionRepository instance;
    private final UserRepository userRepository;
    private final SessionPreferenceManager sessionPreferenceManager;

    SessionRepository(Context context) {
        userRepository = UserRepository.getInstance(context);
        sessionPreferenceManager = new SessionPreferenceManager(context);
    }

    public static SessionRepository getInstance(Context context) {
        if (instance == null) {
            instance = new SessionRepository(context);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return sessionPreferenceManager.hasSession();
    }

    public boolean logout() {
        sessionPreferenceManager.clearSession();
        return true;
    }

    public boolean logIn(String email, String password) {
        CheckInWrapper checkInWrapper = userRepository.checkUser(email, password);
        if (checkInWrapper.isExists()) {
            sessionPreferenceManager.setDetails(email, checkInWrapper.getFirstName(), checkInWrapper.getLastName());
        }
        return checkInWrapper.isExists();
    }

    public boolean register(String email, String firstName, String lastName, String password) {
        if (userRepository.userExists(email)) {
            return false;
        }
        userRepository.addUser(email, firstName, lastName, password);
        sessionPreferenceManager.setDetails(email, firstName, lastName);
        return true;

    }
    public void terminateSession(){
        sessionPreferenceManager.clearSession();
    }

    public String getEmail() {
        return sessionPreferenceManager.getEmail();
    }
}
