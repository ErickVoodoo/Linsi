package com.linzon.ru.Activity.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.linzon.ru.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by erick on 26.11.15.
 */
public class SendNotification extends Fragment {

    EditText email;
    EditText information;
    Button send;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.send_notification, container, false);
        setEditTexts();
        setButtons();
        return view;
    }

    private void setButtons() {
        send = (Button) view.findViewById(R.id.buttonSendNotificaiton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmailValid(email.getText().toString())) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
                    intent.putExtra(Intent.EXTRA_TEXT, "Привет друг! Загрузить это приложение для своего телефона!\n" + information.getText().toString());
                    intent.setData(Uri.parse("mailto:" + email.getText().toString())); // or just "mailto:" for blank
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                    startActivity(intent);
                } else {
                    Snackbar.make(SendNotification.this.getActivity().findViewById(android.R.id.content), "Введите корректный email", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setEditTexts() {
        email = (EditText) view.findViewById(R.id.edittextEmailNotification);
        information = (EditText) view.findViewById(R.id.editextInformationNotification);
    }


    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
