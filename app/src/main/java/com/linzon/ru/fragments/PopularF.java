package com.linzon.ru.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linzon.ru.MainActivity;
import com.linzon.ru.R;
import com.linzon.ru.common.CustomGridLayoutManager;
import com.linzon.ru.common.RViewScroll;

public class PopularF extends Fragment {
    private RecyclerView recyclerView;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_popular, container, false);
        serRecycler();
        setFab();
        showPopular();
        return  view;
    }

    private void serRecycler() {
        recyclerView = (RecyclerView) view.findViewById(R.id.popularRecycler);

        recyclerView.setAdapter(null);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setLayoutManager(new CustomGridLayoutManager(getActivity(), 2));
    }

    private void showPopular() {
        ((MainActivity) this.getActivity()).showProgressBar();

        ((MainActivity) this.getActivity()).hideProgressBar();
    }

    private void setFab() {
        recyclerView.addOnScrollListener(new RViewScroll(this.getActivity()));
    }
}
