package com.linzon.ru.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.linzon.ru.R;
import com.linzon.ru.common.SharedProperty;

public class UserF extends Fragment {
    View view;

    EditText userName;
    EditText userPhone;
    EditText userEmail;

    Button userSaveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);

        initEditText();
        initButton();
        return view;
    }

    private void initEditText() {
        userEmail = (EditText) view.findViewById(R.id.userEmail);
        userName = (EditText) view.findViewById(R.id.userName);
        userPhone = (EditText) view.findViewById(R.id.userPhone);
    }

    private void initButton() {
        userSaveButton = (Button) view.findViewById(R.id.userSaveButton);
        userSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedProperty.getInstance().setValue(SharedProperty.USER_NAME, userName.getText().toString());
                SharedProperty.getInstance().setValue(SharedProperty.USER_EMAIL, userEmail.getText().toString());
                SharedProperty.getInstance().setValue(SharedProperty.USER_PHONE, userPhone.getText().toString());
                Snackbar.make(UserF.this.getActivity().findViewById(android.R.id.content), "Данные сохранены", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        userName.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_NAME));
        userPhone.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_PHONE));
        userEmail.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_EMAIL));
    }
}
