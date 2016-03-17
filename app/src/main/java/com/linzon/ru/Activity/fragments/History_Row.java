package com.linzon.ru.Activity.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.linzon.ru.Activity.History;
import com.linzon.ru.Activity.adapter.HistoryAdapter;
import com.linzon.ru.Activity.api.Hostinger;
import com.linzon.ru.Activity.models.HostingerModel;
import com.linzon.ru.Activity.models.PostModel.HistoryModel;
import com.linzon.ru.Activity.models.SearchModel;
import com.linzon.ru.R;

import java.util.ArrayList;

/**
 * Created by erick on 15.12.15.
 */
public class History_Row extends Fragment {
    View rootView;
    SearchModel searchModel;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView historyError;
    Button fixHistoryButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.history_raw, container, false);

        setButton();
        setTextView();
        setProgressBar();
        setRecycler();
        return rootView;
    }

    public void setIntent(SearchModel model) {
        this.searchModel = model;
    }

    private void setRecycler() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.historyRecycler);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(History_Row.this.getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);


        Hostinger.asyncGetUser(String.valueOf(searchModel.getUid()), new Hostinger.CallbackResponse() {
            @Override
            public void onSuccess(HostingerModel model) {
                if (model != null && model.getCode() != null && model.getCode().equals("201")) {
                    Hostinger.asyncGetHistory(String.valueOf(searchModel.getUid()), new Hostinger.CallbackHistory() {
                        @Override
                        public void onSuccess(ArrayList<HistoryModel> model) {
                            HistoryAdapter historyAdapter = new HistoryAdapter(model, History_Row.this.getActivity());
                            if (historyAdapter.getItemCount() == 0) {
                                historyError.setText(getResources().getString(R.string.historyEmpty));
                            }
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setAdapter(historyAdapter);
                            ((History) getActivity()).setHistoryForDiagramm(model);
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                } else {
                    if(model != null) {
                        historyError.setText(getResources().getString(R.string.errorHistoryEmpty));
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        fixHistoryButton.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(History_Row.this.getActivity(),"Произошла ошибка на сервере, попробуйте через несколько минут", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void setButton() {
        fixHistoryButton = (Button) rootView.findViewById(R.id.fixHistoryButton);
        fixHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hostinger.asyncAddUser(String.valueOf(searchModel.getUid()), new Hostinger.CallbackResponse() {
                    @Override
                    public void onSuccess(HostingerModel model) {
                        History_Row.this.getActivity().finish();
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        });
    }

    private void setTextView() {
        historyError = (TextView) rootView.findViewById(R.id.historyError);

    }

    private void setProgressBar() {
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
    }
}
