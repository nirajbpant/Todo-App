package com.example.todomvvm.data.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionPreferenceManager {
    private final String KEY_EMAIL = "KEY_EMAIL";
    private final String KEY_FIRSTNAME = "KEY_FIRST";
    private final String KEY_LASTNAME = "KEY_LAST";
    private final SharedPreferences sharedPreferences;

    SessionPreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
    }

    public void setDetails(String email, String firstName, String lastName) {
        sharedPreferences.edit()
                .putString(KEY_EMAIL, email)
                .putString(KEY_FIRSTNAME, firstName)
                .putString(KEY_LASTNAME, lastName)
                .apply();

    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public String getFirstName() {
        return sharedPreferences.getString(KEY_FIRSTNAME, null);
    }

    public String getLastName() {
        return sharedPreferences.getString(KEY_LASTNAME, null);
    }

    public boolean hasSession() {
        return getEmail() != null;
    }

    public void clearSession() {
        sharedPreferences.edit().clear().apply();
    }
}
