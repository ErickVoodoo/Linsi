package com.linzon.ru;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linzon.ru.api.ApiConnector;
import com.linzon.ru.common.Constants;
import com.linzon.ru.common.SharedProperty;
import com.linzon.ru.common.Values;
import com.linzon.ru.models.POffer;

import java.util.ArrayList;

public class StartScreen extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private ProgressBar progressBar;
    private TextView startScreenStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        setButton();
        setTextView();
        setProgressBar();
    }

    private void setButton() {
        button = (Button) findViewById(R.id.startScreenRetryDownload);
        button.setOnClickListener(this);
    }

    private void setTextView() {
        startScreenStatus = (TextView) findViewById(R.id.startScreenStatus);
    }

    private void setProgressBar() {
        progressBar = (ProgressBar) findViewById(R.id.progressBarStartScreen);
    }

    private void uploadInformation() {
        progressBar.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);
        if (Values.isOnline(this)) {
            ApiConnector.asyncSimpleGetRequest(Constants.STATIC_VERSION, new ApiConnector.CallbackString() {
                @Override
                public void onSuccess(final String successVersion) {
                    ApiConnector.asyncGetPrice(Constants.STATIC_PRICE, new ApiConnector.CallbackGetPriceList() {
                        @Override
                        public void onSuccess(ArrayList<POffer> successPrice) {
                            ((App) StartScreen.this.getApplication()).setPriceOffers(successPrice);

                            if(SharedProperty.getInstance().getValue(SharedProperty.APP_VERSION) == null ||
                                    !SharedProperty.getInstance().getValue(SharedProperty.APP_VERSION).equals(successVersion)) {
                                if(SharedProperty.getInstance().getValue(SharedProperty.APP_VERSION) != null)
                                    startScreenStatus.setText(StartScreen.this.getResources().getString(R.string.notificationNewData));
                                ApiConnector.asyncGetOfferList(Constants.STATIC_APP, new ApiConnector.CallbackGetOfferList() {
                                    @Override
                                    public void onSuccess(String successOffers) {
                                        progressBar.setVisibility(View.GONE);
                                        SharedProperty.getInstance().setValue(SharedProperty.APP_VERSION, successVersion);

                                        Intent intent = new Intent(StartScreen.this, MainActivity.class);
                                        startActivity(intent);
                                        StartScreen.this.finish();
                                    }

                                    @Override
                                    public void onError(String error) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                button.setVisibility(View.VISIBLE);
                                                progressBar.setVisibility(View.GONE);
                                                startScreenStatus.setText(StartScreen.this.getResources().getString(R.string.networkExceptionSmall));
                                            }
                                        });
                                    }
                                });
                            } else {
                                Intent intent = new Intent(StartScreen.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                StartScreen.this.finish();
                            }
                        }

                        @Override
                        public void onError(String error) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    button.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    startScreenStatus.setText(StartScreen.this.getResources().getString(R.string.networkExceptionSmall));
                                }
                            });
                        }
                    });
                }

                @Override
                public void onError(String error) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            button.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            startScreenStatus.setText(StartScreen.this.getResources().getString(R.string.networkExceptionSmall));
                        }
                    });
                }
            });
        } else {
            button.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            startScreenStatus.setText(StartScreen.this.getResources().getString(R.string.networkExceptionSmall));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        uploadInformation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startScreenRetryDownload: {
                if (Values.isOnline(getApplicationContext())) {
                    uploadInformation();
                    startScreenStatus.setText(StartScreen.this.getResources().getString(R.string.startScreenLoadInformation));
                } else {
                    Values.showTopSnackBar(StartScreen.this, getApplicationContext().getResources().getString(R.string.networkException), null, null, Snackbar.LENGTH_SHORT);
                }
                break;
            }
        }
    }
}
