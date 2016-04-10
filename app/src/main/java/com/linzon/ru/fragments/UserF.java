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
import com.linzon.ru.common.Values;

public class UserF extends Fragment {
    View view;

    EditText userName;
    EditText userPhone;
    EditText userEmail;
    EditText userCity;
    EditText userStreet;

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
        userCity = (EditText) view.findViewById(R.id.userCity);
        userStreet = (EditText) view.findViewById(R.id.userStreet);
    }

    private void initButton() {
        userSaveButton = (Button) view.findViewById(R.id.userSaveButton);
        userSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().toString().length() == 0 ||
                        userPhone.getText().toString().length() == 0) {
                    Values.showTopSnackBar(UserF.this.getActivity(), UserF.this.getActivity().getResources().getString(R.string.errorNotFilled), null, null, Snackbar.LENGTH_SHORT);
                    return;
                }
                SharedProperty.getInstance().setValue(SharedProperty.USER_NAME, userName.getText().toString());
                SharedProperty.getInstance().setValue(SharedProperty.USER_EMAIL, userEmail.getText().toString());
                SharedProperty.getInstance().setValue(SharedProperty.USER_PHONE, userPhone.getText().toString());
                SharedProperty.getInstance().setValue(SharedProperty.USER_CITY, userCity.getText().toString());
                SharedProperty.getInstance().setValue(SharedProperty.USER_STREET, userStreet.getText().toString());
                Values.showTopSnackBar(UserF.this.getActivity(), "Данные сохранены", null, null, Snackbar.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        userName.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_NAME));
        userPhone.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_PHONE));
        userEmail.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_EMAIL));
        userCity.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_CITY));
        userStreet.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_STREET));
    }
}
