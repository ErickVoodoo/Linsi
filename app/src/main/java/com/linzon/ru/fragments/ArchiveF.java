package com.linzon.ru.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linzon.ru.R;
import com.linzon.ru.adapters.ArchiveAdapter;
import com.linzon.ru.adapters.BasketAdapter;
import com.linzon.ru.common.Constants;
import com.linzon.ru.database.DBAsync;
import com.linzon.ru.models.BasketItem;

import java.util.ArrayList;

/**
 * Created by Admin on 26.03.2016.
 */
public class ArchiveF extends Fragment {
    View view;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        view = inflater.inflate(R.layout.fragment_archive, container, false);

        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.archiveRecyclerView);
        recyclerView.setAdapter(null);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        updateArchive();
    }

    private void updateArchive() {
        DBAsync.asyncGetBasketList(Constants.STATUS_ARCHIVED, new DBAsync.CallbackGetBasket() {
            @Override
            public void onSuccess(ArrayList<BasketItem> success) {
                recyclerView.setAdapter(new ArchiveAdapter(ArchiveF.this.getActivity(), success));
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constants.BROADCAST_ADD_TO_ARCHIVE: {
                    updateArchive();
                    break;
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_ADD_TO_ARCHIVE);
        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this.getContext()).unregisterReceiver(broadcastReceiver);
    }
}
