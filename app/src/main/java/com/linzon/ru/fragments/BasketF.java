package com.linzon.ru.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.linzon.ru.Basket;
import com.linzon.ru.R;
import com.linzon.ru.adapters.BasketAdapter;
import com.linzon.ru.common.Constants;
import com.linzon.ru.common.SharedProperty;
import com.linzon.ru.common.Values;
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
                if(DBHelper.getInstance().getTotalPrice(Constants.STATUS_OPEN) < 1000) {
                    Snackbar.make(BasketF.this.getActivity().findViewById(android.R.id.content), "Нельзя заказать товаров на сумму меньше 1000", Snackbar.LENGTH_SHORT).show();
                } else {
                    if(SharedProperty.getInstance().getValue(SharedProperty.USER_NAME) != null &&
                            SharedProperty.getInstance().getValue(SharedProperty.USER_PHONE) != null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BasketF.this.getActivity());
                        builder.setMessage("Отправить заявку в магазин?")
                                .setPositiveButton("Заказать", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(Values.isOnline(BasketF.this.getActivity())) {
                                            DBHelper.createFinalJsonData();
                                            /*DBHelper.sendToArchive();
                                            Intent intent = new Intent();
                                            intent.setAction(Constants.BROADCAST_ADD_TO_ARCHIVE);
                                            LocalBroadcastManager.getInstance(BasketF.this.getActivity()).sendBroadcast(intent);
                                        */} else {
                                            Snackbar.make(BasketF.this.getActivity().findViewById(android.R.id.content), BasketF.this.getActivity().getResources().getString(R.string.networkException), Snackbar.LENGTH_SHORT).show();
                                        }
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
                        final Dialog dialog = new Dialog(BasketF.this.getActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setTitle("Заполните контактные данные");
                        dialog.setCancelable(true);
                        dialog.setContentView(R.layout.fragment_user);
                        Button dialogButton = (Button) dialog.findViewById(R.id.userSaveButton);
                        final EditText username = (EditText) dialog.findViewById(R.id.userName);
                        final EditText email = (EditText) dialog.findViewById(R.id.userEmail);
                        final EditText phone = (EditText) dialog.findViewById(R.id.userPhone);

                        username.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_NAME));
                        phone.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_PHONE));
                        email.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_EMAIL));

                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(username.getText().toString().length() == 0 || email.getText().toString().length() == 0 || phone.getText().toString().length() == 0) {
                                    Snackbar.make(BasketF.this.getActivity().findViewById(android.R.id.content), "Заполните все поля", Snackbar.LENGTH_SHORT).show();
                                    return;
                                }
                                SharedProperty.getInstance().setValue(SharedProperty.USER_NAME, username.getText().toString());
                                SharedProperty.getInstance().setValue(SharedProperty.USER_EMAIL, email.getText().toString());
                                SharedProperty.getInstance().setValue(SharedProperty.USER_PHONE, phone.getText().toString());
                                Snackbar.make(BasketF.this.getActivity().findViewById(android.R.id.content), "Данные сохранены", Snackbar.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                }
            }
        });
    }

    private void initTextView() {
        basketTotalCount = (TextView) view.findViewById(R.id.basketTotalCount);
        updateTotalPrice();
    }

    private void updateTotalPrice() {
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
                case Constants.BROADCAST_ADD_TO_ARCHIVE: {
                    recyclerView.setAdapter(null);
                    basketTotalCount.setText(BasketF.this.getActivity().getResources().getString(R.string.static_total) + " 0 " + BasketF.this.getActivity().getResources().getString(R.string.static_exchange));
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
        intentFilter.addAction(Constants.BROADCAST_ADD_TO_ARCHIVE);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
    }
}
