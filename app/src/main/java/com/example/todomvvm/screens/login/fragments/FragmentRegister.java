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
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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

        button_Register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean checkRegister = loginRegisterViewModel.registerUser(editEmail.getText().toString()
                        , editFirstName.getText().toString(),
                        editLastName.getText().toString(), editPassword.getText().toString());
                if (editFirstName.getText().toString().equals("")) {
                    editFirstName.setError("Enter your first name");
                }
                else if (editLastName.getText().toString().equals(""))
                {
                    editLastName.setError("Enter your last name");
                }

                else if(editEmail.getText().toString().isEmpty()) {
                    editEmail.setError("Enter email address");
                }else if (!editEmail.getText().toString().trim().matches(emailPattern)) {
                    editEmail.setError("Invalid email address");
                }


                if(editPassword.getText().toString().equals("")) {
                    editPassword.setError("Enter valid password");
                }

                else if (checkRegister) {
                    Intent intent = new Intent(getContext(), SplashActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else {
                    Toast.makeText(getContext(),
                            "User Already Exists",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}