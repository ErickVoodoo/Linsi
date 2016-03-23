package com.linzon.ru;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.linzon.ru.api.ApiConnector;
import com.linzon.ru.common.Constants;
import com.linzon.ru.common.SharedProperty;
import com.linzon.ru.common.Values;

public class StartScreen extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        setButton();
        setProgressBar();
    }

    private void setButton() {
        button = (Button) findViewById(R.id.startScreenRetryDownload);
        button.setOnClickListener(this);
    }

    private void setProgressBar() {
        progressBar = (ProgressBar) findViewById(R.id.progressBarStartScreen);
    }

    private void uploadInformation() {
        progressBar.setVisibility(View.VISIBLE);
        ApiConnector.asyncGetOfferList(Constants.STATIC_APP, new ApiConnector.CallbackGetOfferList() {
            @Override
            public void onSuccess(String success) {
                progressBar.setVisibility(View.GONE);
                SharedProperty.getInstance().setValue(SharedProperty.APP_VERSION, "0");

                Intent intent = new Intent(StartScreen.this, MainActivity.class);
                startActivity(intent);
                StartScreen.this.finish();
            }

            @Override
            public void onError(String error) {
                Log.e("NETWORK", "ERROR");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharedProperty.getInstance().getValue(SharedProperty.APP_VERSION).equals("null")) {
            uploadInformation();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startScreenRetryDownload: {
                if(Values.isOnline(getApplicationContext())) {
                    uploadInformation();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), getApplicationContext().getResources().getString(R.string.networkException), Snackbar.LENGTH_LONG).show();
                }
                break;
            }
        }
    }
}
