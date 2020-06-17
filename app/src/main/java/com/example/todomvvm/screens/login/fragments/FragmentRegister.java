package com.example.todomvvm.screens.login.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todomvvm.R;
import com.example.todomvvm.screens.login.viewmodel.LoginRegisterViewModel;
import com.example.todomvvm.screens.splash.SplashActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentRegister extends Fragment {
    Button button_Register;
    EditText editFirstName, editLastName, editEmail, editPassword;
    LoginRegisterViewModel loginRegisterViewModel;
    boolean isNameValid, isLastNameValid, isEmailValid, isPasswordValid;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        editFirstName = view.findViewById(R.id.editFirstName);
        editLastName = view.findViewById(R.id.editLastName);
        editEmail = view.findViewById(R.id.editEmail);
        editPassword = view.findViewById(R.id.editPassword);
        button_Register = view.findViewById(R.id.button_Register);
        loginRegisterViewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel.class);

        button_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
                if (!TextUtils.isEmpty(editFirstName.getText()) && !TextUtils.isEmpty(editLastName.getText()) &&
                        !TextUtils.isEmpty(editEmail.getText()) && isEmailValid && !TextUtils.isEmpty(editPassword.getText())) {
                    boolean checkRegister = loginRegisterViewModel.registerUser(editEmail.getText().toString()
                            , editFirstName.getText().toString(),
                            editLastName.getText().toString(), editPassword.getText().toString());


                    if (checkRegister) {
                        Intent intent = new Intent(getContext(), SplashActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getContext(),
                                "Fields cannot be empty",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(),
                            "Please fill in Register Credentials",
                            Toast.LENGTH_SHORT).show();
                }


            }

        });
    }

    private void validation() {
        if (editFirstName.getText().toString().isEmpty()) {
            editFirstName.setError(getResources().getString(R.string.name_error));
            isNameValid = false;
        } else  {
            isNameValid = true;
        }

        if (editLastName.getText().toString().isEmpty()) {
            editLastName.setError(getResources().getString(R.string.last_name_error));
            isLastNameValid = false;
        } else  {
            isLastNameValid = true;
        }

        if (editEmail.getText().toString().isEmpty()) {
            editEmail.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString()).matches()) {
            editEmail.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else {
            isEmailValid = true;
        }

        if (editPassword.getText().toString().isEmpty()) {
            editPassword.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (editPassword.getText().length() < 6) {
            editPassword.setError(getResources().getString(R.string.error_invalid_password));
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
        }

        if (isNameValid && isLastNameValid && isEmailValid  && isPasswordValid) {
            Toast.makeText(getContext(), "Successfully", Toast.LENGTH_SHORT).show();
        }

    }
}