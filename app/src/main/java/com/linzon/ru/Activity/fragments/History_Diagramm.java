package com.linzon.ru.Activity.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.linzon.ru.Activity.History;
import com.linzon.ru.Activity.adapter.UserListAdapter;
import com.linzon.ru.Activity.api.Hostinger;
import com.linzon.ru.Activity.api.Vk;
import com.linzon.ru.Activity.common.TimeCommon;
import com.linzon.ru.Activity.common.Values;
import com.linzon.ru.Activity.database.DBHelper;
import com.linzon.ru.Activity.models.MainOffer;
import com.linzon.ru.Activity.models.PostModel.HistoryModel;
import com.linzon.ru.Activity.models.SearchModel;
import com.linzon.ru.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by erick on 15.12.15.
 */
public class History_Diagramm extends Fragment{
    SearchModel model;
    View rootView;

    RecyclerView historyDiagrammFriendsRecycler;

    ArrayList<MainOffer> users;

    String usersString = "";

    TextView errorMessage;

    public void setIntent(SearchModel model) {
        this.model = model;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.history_diagramm, container, false);
        users = new ArrayList<MainOffer>();

        errorMessage = (TextView) rootView.findViewById(R.id.errorMessage);
        setRecycler();

        return rootView;
    }

    private void setRecycler() {
        historyDiagrammFriendsRecycler = (RecyclerView) rootView.findViewById(R.id.historyDiagrammFriendsRecycler);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(History_Diagramm.this.getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        historyDiagrammFriendsRecycler.setHasFixedSize(true);
        historyDiagrammFriendsRecycler.setLayoutManager(mLinearLayoutManager);


        Cursor cursor = DBHelper.getInstance().selectRows(DBHelper.USERS, null, "uid != " + this.model.getUid(), null, null);

        if (cursor.moveToFirst()) {
            do {
                usersString += String.valueOf(cursor.getInt(cursor.getColumnIndex("uid"))) + ",";
            }
            while (cursor.moveToNext());
        }

        Vk.asyncGetUsersShort(usersString.substring(0, usersString.length() - 1), new Vk.CallbackArraySearchModel() {
            @Override
            public void onSuccess(ArrayList<SearchModel> model) {
                UserListAdapter userListAdapter = new UserListAdapter(model, History_Diagramm.this.getActivity(), new UserListAdapter.onClickCallback() {
                    @Override
                    public void onClick(String uid) {
                        Hostinger.asyncGetHistory(uid, new Hostinger.CallbackHistory() {
                            @Override
                            public void onSuccess(ArrayList<HistoryModel> model) {
                                currentUser = returnArrayDiagrammTime(((History) getActivity()).currentUserHistory);
                                ArrayList<Diagramm> selectedUser = returnArrayDiagrammTime(model);

                                if(currentUser != null) {
                                    ArrayList<BarEntry> entries = new ArrayList<>();
                                    ArrayList<String> labels = new ArrayList<String>();

                                    int count = 0;
                                    for(int i = 0;i < 24; i++) {
                                        count = 0;
                                        for(int j = 0;j < currentUser.size(); j++) {
                                            int[] cur_start = TimeCommon.getArrayHourMinute(Integer.parseInt(currentUser.get(j).getFROM()));
                                            int[] cur_end = TimeCommon.getArrayHourMinute(Integer.parseInt(currentUser.get(j).getTO()));
                                            if(i == cur_start[0]) {
                                                for(int k = 0;k < selectedUser.size(); k++) {
                                                    int[] sel_start = TimeCommon.getArrayHourMinute(Integer.parseInt(selectedUser.get(k).getFROM()));
                                                    int[] sel_end = TimeCommon.getArrayHourMinute(Integer.parseInt(selectedUser.get(k).getTO()));

                                                    if(cur_start[0] == sel_start[0]) {
                                                        int start_max = Math.max(Integer.parseInt(selectedUser.get(k).getFROM()), Integer.parseInt(currentUser.get(j).getFROM()));
                                                        int end_min = Math.min(Integer.parseInt(selectedUser.get(k).getTO()) > i ? i * 60 : Integer.parseInt(selectedUser.get(k).getTO()), Integer.parseInt(currentUser.get(j).getTO()) > i ? i*60 :  Integer.parseInt(currentUser.get(j).getTO()));
                                                        count = end_min - start_max;
                                                    }
                                                }
                                            }
                                        }
                                        Log.e("COUNT", String.valueOf(count));
                                        entries.add(new BarEntry(count, i));
                                    }

                                    /*entries.add(new BarEntry(4, 0));
                                    entries.add(new BarEntry(8, 1));
                                    entries.add(new BarEntry(6, 2));
                                    entries.add(new BarEntry(12, 3));
                                    entries.add(new BarEntry(18, 4));
                                    entries.add(new BarEntry(9, 5));*/

                                    BarDataSet dataset = new BarDataSet(entries, "совпадение времени онлайн в минутах");

                                    for(int i = 0;i < 24; i++) {
                                        labels.add(String.valueOf(i));
                                    }

                                    BarChart barChart = (BarChart) rootView.findViewById(R.id.barChart);
                                    BarData data = new BarData(labels, dataset);

                                    barChart.setData(data);//SET DATA

                                    barChart.invalidate();
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                    }
                });

                if (userListAdapter.getItemCount() == 0) {
                    errorMessage.setText(getResources().getString(R.string.historyFriendsEmpty));
                } else {
                    errorMessage.setVisibility(View.GONE);
                }
                historyDiagrammFriendsRecycler.setAdapter(userListAdapter);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    ArrayList<Diagramm> currentUser;
    ArrayList<Diagramm> HistoryUserDiagramm;

    public class Diagramm {
        public String FROM;
        public String TO;

        public String getFROM() {
            return FROM;
        }

        public void setFROM(String FROM) {
            this.FROM = FROM;
        }

        public String getTO() {
            return TO;
        }

        public void setTO(String TO) {
            this.TO = TO;
        }
    }

    public void setMainHistory(ArrayList<HistoryModel> model) {
        currentUser = returnArrayDiagrammTime(model);
    }

    public ArrayList<Diagramm> returnArrayDiagrammTime(ArrayList<HistoryModel> model) {
        Collections.reverse(model);
        ArrayList<Diagramm> UserModel = new ArrayList<>();
        Diagramm diagramm = new Diagramm();
        for(int j = 0; j < model.size(); j++) {
            Date date = new Date((long) model.get(j).getTime() * 1000);
            int minute = date.getMinutes();
            int hour = date.getHours();
            int day = date.getDate();

            if(day == Values.getDayNumber()) {
                Log.e(String.valueOf(hour), String.valueOf(minute) + "|" + String.valueOf(model.get(j).getType()));
                if(UserModel.size() == 0 && diagramm.getFROM() == null &&model.get(j).getType() == 0) {
                    diagramm.setFROM("0");
                } else if(model.get(j).getType() == 1) {
                    diagramm.setFROM(String.valueOf(hour * 60 + minute));
                }

                if(model.get(j).getType() == 0 && diagramm.getFROM() != null) {
                    diagramm.setTO(String.valueOf(hour * 60 + minute));
                    UserModel.add(diagramm);
                    diagramm = new Diagramm();
                }

                if(j == (model.size() - 1) && diagramm.getFROM() != null && diagramm.getTO() == null) {
                    diagramm.setTO(String.valueOf(Values.getHourNumber()*60 + Values.getMinuteNumber()));
                    UserModel.add(diagramm);
                }
            }
        }

        int index = 0;
        ArrayList<Diagramm> New_Model = new ArrayList<>();
        for(int i = 1; i < 25; i++) {
            Diagramm temp_model = UserModel.get(index);
            Diagramm temp = new Diagramm();
            temp.setFROM("-1");
            temp.setTO("-1");

            if(Integer.parseInt(temp_model.getFROM()) < i * 60
                    && Integer.parseInt(temp_model.getTO()) < i * 60
                    && Integer.parseInt(temp_model.getFROM()) > (i - 1) * 60
                    && Integer.parseInt(temp_model.getTO()) > (i - 1) * 60) {

                int minute_from = Integer.parseInt(temp_model.getFROM())/60 > 0 ? (Integer.parseInt(temp_model.getFROM()) - ((int)(Integer.parseInt(temp_model.getFROM()) / 60)*60)) : Integer.parseInt(temp_model.getFROM());
                int minute_to = Integer.parseInt(temp_model.getTO())/60 > 0 ? (Integer.parseInt(temp_model.getTO()) - ((int)(Integer.parseInt(temp_model.getTO()) / 60)*60)) : Integer.parseInt(temp_model.getTO());
                temp.setFROM(String.valueOf(minute_from));
                temp.setTO(String.valueOf(minute_to));
                index ++;
            } else if(Integer.parseInt(temp_model.getFROM()) < i * 60 && Integer.parseInt(temp_model.getFROM()) < (i - 1) * 60) {
                for(int k = 0; i <= (Integer.parseInt(temp_model.getTO()) - Integer.parseInt(temp_model.getFROM()))/60; k++) {
                    Diagramm temp_2 = new Diagramm();
                    /*temp_2.setFROM();
                    temp_2.setTO();
                    New_Model.add(temp_2)*/
                }
            }
            New_Model.add(temp);
        }
        return UserModel;
    }
}
