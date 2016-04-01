package com.linzon.ru.fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.linzon.ru.Basket;
import com.linzon.ru.R;
import com.linzon.ru.adapters.BasketAdapter;
import com.linzon.ru.common.Constants;
import com.linzon.ru.common.SharedProperty;
import com.linzon.ru.database.DBAsync;
import com.linzon.ru.database.DBHelper;
import com.linzon.ru.models.BasketItem;

import java.util.ArrayList;

/**
 * Created by Admin on 26.03.2016.
 */
public class BasketF extends Fragment {
    View view;
    RecyclerView recyclerView;
    Button buyButton;
    TextView basketTotalCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_basket, container, false);

        initRecyclerView();
        initTextView();
        initButtons();
        return view;
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.basketRecyclerView);
        recyclerView.setAdapter(null);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        DBAsync.asyncGetBasketList(Constants.STATUS_OPEN, new DBAsync.CallbackGetBasket() {
            @Override
            public void onSuccess(ArrayList<BasketItem> success) {
                recyclerView.setAdapter(new BasketAdapter(BasketF.this.getActivity(), success));
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initButtons() {
        buyButton = (Button) view.findViewById(R.id.buyButton);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SharedProperty.getInstance().getValue(SharedProperty.USER_NAME) != null &&
                        SharedProperty.getInstance().getValue(SharedProperty.USER_PHONE) != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BasketF.this.getActivity());
                    builder.setMessage("Отправить заявку в магазин?")
                            .setPositiveButton("Заказать", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DBHelper.sendToArchive();
                                    /*
                                    arrayList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, getItemCount());

                                    Intent updatePrice = new Intent();
                                    updatePrice.setAction(Constants.BROADCAST_UPDATE_PRICE);
                                    LocalBroadcastManager.getInstance(activity).sendBroadcast(updatePrice);*/
                                }
                            })
                            .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BasketF.this.getActivity());
                    builder.setMessage("Вы не ввели данные для связи")
                            .setPositiveButton("Заказать", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    /*DBHelper.deleteFromBasket(arrayList.get(position).getId());
                                    arrayList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, getItemCount());

                                    Intent updatePrice = new Intent();
                                    updatePrice.setAction(Constants.BROADCAST_UPDATE_PRICE);
                                    LocalBroadcastManager.getInstance(activity).sendBroadcast(updatePrice);*/
                                }
                            })
                            .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }
            }
        });
    }

    private void initTextView() {
        basketTotalCount = (TextView) view.findViewById(R.id.basketTotalCount);
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        Log.e("updateTotalPrice", "UPDATED");
        basketTotalCount.setText(BasketF.this.getActivity().getResources().getString(R.string.static_total) + " " + DBHelper.getInstance().getTotalPrice(Constants.STATUS_OPEN) + " " + BasketF.this.getActivity().getResources().getString(R.string.static_exchange));
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case Constants.BROADCAST_UPDATE_PRICE: {
                    updateTotalPrice();
                    break;
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_UPDATE_PRICE);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
    }
}
