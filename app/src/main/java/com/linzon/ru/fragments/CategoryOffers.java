package com.linzon.ru.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linzon.ru.MainActivity;
import com.linzon.ru.R;

/**
 * Created by erick on 14.10.15.
 */
public class CategoryOffers extends Fragment {
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        /*recyclerView = (RecyclerView) view.findViewById(R.id.searchRecycler);
        recyclerView.setAdapter(null);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);


        setFab();*/
        return view;
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
