package com.linzon.ru.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.linzon.ru.MainActivity;
import com.linzon.ru.R;
import com.linzon.ru.adapters.CategoryAdapter;
import com.linzon.ru.database.DBAsync;
import com.linzon.ru.database.DBHelper;
import com.linzon.ru.models.OOffer;

import java.util.ArrayList;

/**
 * Created by erick on 14.10.15.
 */
public class CategoryOffers extends Fragment {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    View view;
    int selectedCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);
        setRecycler();
        setProgressBar();
        setFab();
        return view;
    }

    public void setRecycler() {
        recyclerView = (RecyclerView) view.findViewById(R.id.categoryRecycler);
        recyclerView.setAdapter(null);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
    }

    private void setProgressBar() {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

    public void setCategory(int categoryId) {
        progressBar.setVisibility(View.VISIBLE);
        this.selectedCategory = categoryId;
        recyclerView.setAdapter(null);
    }

    public void loadCategoryItems() {
        DBAsync.asyncGetOfferList(this.selectedCategory, new DBAsync.CallBackGetCategory() {
            @Override
            public void onSuccess(ArrayList<OOffer> success) {
                recyclerView.setAdapter(new CategoryAdapter(success, CategoryOffers.this.getActivity()));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(String error) {

            }
        });
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
