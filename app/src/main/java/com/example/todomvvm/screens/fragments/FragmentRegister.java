package com.example.todomvvm.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.todomvvm.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentRegister extends Fragment {
    Button button_Register;
    EditText editFirstName, editLastName,editEmail, editPassword;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.register_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        editFirstName= view.findViewById(R.id.editFirstName);
        editLastName= view.findViewById(R.id.editLastName);
        editEmail= view.findViewById(R.id.editEmail);
        editPassword= view.findViewById(R.id.editPassword);
        button_Register= view.findViewById(R.id.button_Register);
        button_Register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
    }
}