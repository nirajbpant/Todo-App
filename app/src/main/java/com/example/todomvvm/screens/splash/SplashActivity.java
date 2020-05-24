package com.example.todomvvm.screens.splash;

import android.content.Intent;
import android.os.Bundle;

import com.example.todomvvm.R;
import com.example.todomvvm.screens.login.LoginActivity;
import com.example.todomvvm.screens.splash.splashviewmodel.SplashViewModel;
import com.example.todomvvm.screens.tasks.TaskListActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class SplashActivity extends AppCompatActivity {
    SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        screenSelector();
    }

    public void screenSelector() {
        if (splashViewModel.isLoggedIn()) {
            Intent intent = new Intent(this, TaskListActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }


}
