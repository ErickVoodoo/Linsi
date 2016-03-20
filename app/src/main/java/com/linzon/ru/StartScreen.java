package com.linzon.ru;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.linzon.ru.common.SharedProperty;

public class StartScreen extends AppCompatActivity implements View.OnClickListener{
    private Button button;
    private EditText editText;
    private SharedProperty sharedProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        button = (Button) findViewById(R.id.startScreenButton);
        editText = (EditText) findViewById(R.id.startScreenSelectedName);

        button.setOnClickListener(this);

        Intent intent = new Intent(StartScreen.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedProperty.init(this);

        sharedProperty = SharedProperty.getInstance();

        if(sharedProperty.isCurrentName()) {
            Intent intent = new Intent(StartScreen.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startScreenButton: {
                if(editText.getText().toString().length() > 0) {
                    //sharedProperty.setCurrentName(editText.getText().toString());
                    Intent intent = new Intent(StartScreen.this, MainActivity.class);
                    startActivity(intent);
                    this.finish();
                    return;
                }
                Snackbar.make(findViewById(android.R.id.content), "Fill your name", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            }
        }
    }
}
