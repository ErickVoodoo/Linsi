package com.itibo.vkcheck.Activity.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.itibo.vkcheck.Activity.MainActivity;
import com.itibo.vkcheck.Activity.adapter.SearchAdapter;
import com.itibo.vkcheck.Activity.api.Vk;
import com.itibo.vkcheck.Activity.database.DBHelper;
import com.itibo.vkcheck.Activity.models.SearchModel;
import com.itibo.vkcheck.R;

import java.util.ArrayList;

/**
 * Created by erick on 14.10.15.
 */
public class Users extends Fragment {
    private RecyclerView recyclerView;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_user, container, false);
        Log.e("onCreateView()", "Users");
        serRecycler();
        return  view;
    }

    private void serRecycler() {
        recyclerView = (RecyclerView) view.findViewById(R.id.userRecycler);

        recyclerView.setAdapter(null);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        setFab();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume()", "Users");
        String users = DBHelper.getStringUsers(null);
        if(users != null) {
            Vk.asyncGetUsersFull(users, new Vk.CallbackArraySearchModel() {
                @Override
                public void onSuccess(ArrayList<SearchModel> model) {
                    SearchAdapter searchAdapter = new SearchAdapter(model, getActivity(), R.layout.fragment_user_item);
                    recyclerView.setAdapter(searchAdapter);
                }

                @Override
                public void onError(String error) {

                }
            });
        } else {
            recyclerView.setAdapter(null);
        }
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    private void setFab() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                             private static final int HIDE_THRESHOLD = 20;
                                             private int scrolledDistance = 0;
                                             private boolean controlsVisible = true;

                                             @Override
                                             public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                                 super.onScrolled(recyclerView, dx, dy);
                                                 if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                                                     ((MainActivity) getActivity()).hideFab();
                                                     controlsVisible = false;
                                                     scrolledDistance = 0;
                                                 } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                                                     ((MainActivity) getActivity()).showFab();
                                                     controlsVisible = true;
                                                     scrolledDistance = 0;
                                                 }

                                                 if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
                                                     scrolledDistance += dy;
                                                 }
                                             }
                                         }
        );
    }
}
