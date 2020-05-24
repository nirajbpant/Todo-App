package com.example.todomvvm.screens.login.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todomvvm.R;
import com.example.todomvvm.screens.login.viewmodel.LoginRegisterViewModel;
import com.example.todomvvm.screens.tasks.TaskListActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class FragmentLogin extends Fragment {

    Button button_Login, button_Register;
    EditText editEmail, editPassword;
    CallbackFragment callbackFragment;
    LoginRegisterViewModel loginRegisterViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        editEmail = view.findViewById(R.id.editEmail);
        editPassword = view.findViewById(R.id.editPassword);
        button_Login = view.findViewById(R.id.button_Login);
        button_Register = view.findViewById(R.id.button_Register);
        loginRegisterViewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel.class);

        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(editEmail.getText()) && !TextUtils.isEmpty(editPassword.getText())) {
                    boolean checkLogin = loginRegisterViewModel.loginUser(editEmail.getText().toString(), editPassword.getText().toString());

                    if (checkLogin) {
                        Intent intent = new Intent(getContext(), TaskListActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getContext(),
                                "Incorrect Login Credentials",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(),
                            "Please fill in LogIn Credentials",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

        button_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callbackFragment != null) {
                    callbackFragment.changeFragment();
                }
            }
        });
    }

    public void setCallbackFragment(CallbackFragment callbackFragment) {
        this.callbackFragment = callbackFragment;
    }
}
