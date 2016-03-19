package com.linzon.ru.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linzon.ru.MainActivity;
import com.linzon.ru.api.ApiConnector;
import com.linzon.ru.common.Constants;
import com.linzon.ru.database.DBHelper;
import com.linzon.ru.R;

import java.util.ArrayList;

/**
 * Created by erick on 14.10.15.
 */
public class Popular extends Fragment {
    private RecyclerView recyclerView;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_popular, container, false);
        serRecycler();

        //setFab();
        if(DBHelper.getInstance().selectRows(DBHelper.OFFERS,null , null, null, null).getCount() == 0) {
            ApiConnector.asyncGetOfferList(Constants.STATIC_APP, new ApiConnector.CallbackGetOfferList() {
                @Override
                public void onSuccess(String success) {
                    Log.e("WOWO", "ITS WORK");
                }

                @Override
                public void onError(String error) {

                }
            });
        }

        return  view;
    }

    private void serRecycler() {
        recyclerView = (RecyclerView) view.findViewById(R.id.popularRecycler);

        recyclerView.setAdapter(null);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        /*String users = DBHelper.getStringUsers(null);
        if(users != null) {

        } else {
            recyclerView.setAdapter(null);
        }
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));*/
    }

    /*private void setFab() {
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
    }*/
}
