package com.linzon.ru.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
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

    TextInputLayout userUserInput;
    TextInputLayout userProneInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);

        initEditText();
        initButton();
        initTextInputs();
        return view;
    }

    private void initEditText() {
        userEmail = (EditText) view.findViewById(R.id.userEmail);
        userName = (EditText) view.findViewById(R.id.userName);
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    userUserInput.setErrorEnabled(false);
                    userName.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        userPhone = (EditText) view.findViewById(R.id.userPhone);
        userPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    userProneInput.setErrorEnabled(false);
                    userPhone.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        userCity = (EditText) view.findViewById(R.id.userCity);
        userStreet = (EditText) view.findViewById(R.id.userStreet);
    }

    private boolean isError = false;

    private void initButton() {
        userSaveButton = (Button) view.findViewById(R.id.userSaveButton);
        userSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().length() == 0) {
                    userUserInput.setErrorEnabled(true);
                    userUserInput.setError(UserF.this.getActivity().getResources().getString(R.string.errorNotFilled));
                    isError = true;
                } else {
                    userUserInput.setErrorEnabled(false);
                }

                if (userPhone.getText().toString().length() == 0) {
                    userProneInput.setErrorEnabled(true);
                    userProneInput.setError(UserF.this.getActivity().getResources().getString(R.string.errorNotFilled));
                    isError = true;
                } else {
                    userProneInput.setErrorEnabled(false);
                }

                if(isError) {
                    isError = false;
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

    private void initTextInputs() {
        userUserInput = (TextInputLayout) view.findViewById(R.id.userNameInput);
        userProneInput = (TextInputLayout) view.findViewById(R.id.userPhoneInput);
    }

    @Override
    public void onResume() {
        super.onResume();
        userUserInput.setErrorEnabled(false);
        userProneInput.setErrorEnabled(false);
        userName.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_NAME));
        userPhone.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_PHONE));
        userEmail.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_EMAIL));
        userCity.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_CITY));
        userStreet.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_STREET));
    }
}
