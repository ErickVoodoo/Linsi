package com.linzon.ru.fragments;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.linzon.ru.R;
import com.linzon.ru.adapters.BasketAdapter;
import com.linzon.ru.api.ApiConnector;
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
    boolean isFormError = false;

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
        updateBasketList();
    }

    private void updateBasketList() {
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
                    Values.showTopSnackBar(BasketF.this.getActivity(), "Нельзя заказать товаров на сумму меньше 1000", null, null, Snackbar.LENGTH_SHORT);
                } else {
                    if(SharedProperty.getInstance().getValue(SharedProperty.USER_NAME) != null &&
                            SharedProperty.getInstance().getValue(SharedProperty.USER_PHONE) != null) {

                        final Dialog send = new Dialog(BasketF.this.getActivity());
                        final Window window = send.getWindow();
                        send.setCancelable(true);
                        send.setContentView(R.layout.send_basket);
                        send.setTitle(R.string.static_send_title);

                        final EditText comment = (EditText) send.findViewById(R.id.sendComment);
                        comment.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_COMMENT));
                        final Button cancel = (Button) send.findViewById(R.id.sendCansel);
                        final Button approve = (Button) send.findViewById(R.id.sendApprove);

                        comment.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                SharedProperty.getInstance().setValue(SharedProperty.USER_COMMENT, s.toString());
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                send.dismiss();
                            }
                        });

                        approve.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(Values.isOnline(BasketF.this.getActivity())) {
                                    approve.setEnabled(false);
                                    ApiConnector.asyncSendToServer(new ApiConnector.CallbackString() {
                                        @Override
                                        public void onSuccess(String success) {
                                            Log.e("SUCCESS", success);
                                            SharedProperty.getInstance().setValue(SharedProperty.USER_COMMENT, "");
                                            DBHelper.sendToArchive(success);
                                            Intent intent = new Intent();
                                            intent.setAction(Constants.BROADCAST_ADD_TO_ARCHIVE);
                                            LocalBroadcastManager.getInstance(BasketF.this.getActivity()).sendBroadcast(intent);

                                            send.dismiss();

                                            final Dialog dialog = new Dialog(BasketF.this.getActivity());
                                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialog.setCancelable(false);
                                            dialog.setContentView(R.layout.seccessful_send);
                                            dialog.show();

                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dialog.dismiss();
                                                }
                                            }, 3000);
                                        }

                                        @Override
                                        public void onError(String error) {
                                            Values.showTopSnackBar(BasketF.this.getActivity(),error, null, null, Snackbar.LENGTH_SHORT);
                                            send.dismiss();
                                        }
                                    });
                                } else {
                                    Values.showTopSnackBar(BasketF.this.getActivity(), BasketF.this.getActivity().getResources().getString(R.string.networkException), null, null, Snackbar.LENGTH_SHORT);
                                }
                            }
                        });

                        send.show();
                    } else {
                        final Dialog dialog = new Dialog(BasketF.this.getActivity());
                        dialog.setTitle("Заполните контактные данные");
                        dialog.setCancelable(true);
                        dialog.setContentView(R.layout.fragment_user);
                        Button dialogButton = (Button) dialog.findViewById(R.id.userSaveButton);
                        final EditText username = (EditText) dialog.findViewById(R.id.userName);
                        final EditText email = (EditText) dialog.findViewById(R.id.userEmail);
                        final EditText phone = (EditText) dialog.findViewById(R.id.userPhone);
                        final EditText city = (EditText) dialog.findViewById(R.id.userCity);
                        final EditText street = (EditText) dialog.findViewById(R.id.userStreet);

                        username.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_NAME));
                        phone.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_PHONE));
                        email.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_EMAIL));
                        city.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_CITY));
                        street.setText(SharedProperty.getInstance().getValue(SharedProperty.USER_STREET));

                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextInputLayout userUserInput = (TextInputLayout) dialog.findViewById(R.id.userNameInput);
                                TextInputLayout userProneInput = (TextInputLayout) dialog.findViewById(R.id.userPhoneInput);
                                if (username.getText().toString().length() == 0) {
                                    userUserInput.setErrorEnabled(true);
                                    userUserInput.setError(BasketF.this.getActivity().getResources().getString(R.string.errorNotFilled));
                                    isFormError = true;
                                } else {
                                    userUserInput.setErrorEnabled(false);
                                }

                                if (phone.getText().toString().length() == 0) {
                                    userProneInput.setErrorEnabled(true);
                                    userProneInput.setError(BasketF.this.getActivity().getResources().getString(R.string.errorNotFilled));
                                    isFormError = true;
                                } else {
                                    userProneInput.setErrorEnabled(false);
                                }

                                if (isFormError) {
                                    isFormError = false;
                                    return;
                                }
                                SharedProperty.getInstance().setValue(SharedProperty.USER_NAME, username.getText().toString());
                                SharedProperty.getInstance().setValue(SharedProperty.USER_EMAIL, email.getText().toString());
                                SharedProperty.getInstance().setValue(SharedProperty.USER_PHONE, phone.getText().toString());
                                SharedProperty.getInstance().setValue(SharedProperty.USER_CITY, city.getText().toString());
                                SharedProperty.getInstance().setValue(SharedProperty.USER_STREET, street.getText().toString());
                                Values.showTopSnackBar(BasketF.this.getActivity(), "Данные сохранены", null, null, Snackbar.LENGTH_SHORT);
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
        basketTotalCount.setText(
                BasketF.this.getActivity().getResources().getString(R.string.static_total) + " " +
                        DBHelper.getInstance().getTotalPrice(Constants.STATUS_OPEN) + " " +
                        BasketF.this.getActivity().getResources().getString(R.string.static_exchange) + " (доставка +" + Constants.DELIVER_PRICE + "р.)");
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
                case Constants.BROADCAST_ADD_TO_BASKET_FROM_ARCHIVE: {
                    updateBasketList();
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
        intentFilter.addAction(Constants.BROADCAST_ADD_TO_ARCHIVE);
        intentFilter.addAction(Constants.BROADCAST_ADD_TO_BASKET_FROM_ARCHIVE);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
    }
}
