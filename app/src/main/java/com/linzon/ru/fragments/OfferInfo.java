package com.linzon.ru.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.linzon.ru.MainActivity;
import com.linzon.ru.R;
import com.linzon.ru.adapters.CategoryAdapter;
import com.linzon.ru.common.Constants;
import com.linzon.ru.common.RViewScroll;
import com.linzon.ru.database.DBAsync;
import com.linzon.ru.models.OOffer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OfferInfo extends Fragment {
    View view;
    int selectedOffer;

    ImageView offerPicture;
    TextView offerName;
    TextView offerVendor;
    TextView offerDescription;
    TextView offerPrice;

    Spinner offerCountLeft;
    Spinner offerCountRight;

    ScrollView offerScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_offer_info, container, false);
        setTextView();
        setImageView();
        setSpinners();
        setScrollView();
        return view;
    }

    public void setOffer(int categoryId) {
        ((MainActivity) OfferInfo.this.getActivity()).showProgressBar();
        this.selectedOffer = categoryId;
        loadOfferInfo();
    }

    public void setImageView() {
        offerPicture = (ImageView) view.findViewById(R.id.offerPicture);
    }

    public void setTextView() {
        offerName = (TextView) view.findViewById(R.id.offerName);
        offerVendor = (TextView) view.findViewById(R.id.offerVendor);
        offerDescription = (TextView) view.findViewById(R.id.offerDesciption);
        offerPrice = (TextView) view.findViewById(R.id.offerPrice);
    }

    public void setSpinners() {
        offerCountLeft = (Spinner) view.findViewById(R.id.offerCountLeft);
        offerCountRight = (Spinner) view.findViewById(R.id.offerCountRight);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(OfferInfo.this.getActivity(), android.R.layout.simple_spinner_item, Constants.linsCount);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        offerCountLeft.setAdapter(adapter);
        offerCountLeft.setSelection(1);

        offerCountRight.setAdapter(adapter);
        offerCountRight.setSelection(1);
    }

    public void setScrollView() {
        offerScrollView = (ScrollView) view.findViewById(R.id.offerScrollView);
    }

    public void loadOfferInfo() {
        offerScrollView.setVisibility(View.GONE);
        DBAsync.asyncGetOfferInfo(this.selectedOffer, new DBAsync.CallbackGetOffer() {
            @Override
            public void onSuccess(OOffer success) {
                ((MainActivity) OfferInfo.this.getActivity()).hideProgressBar();

                if(success.getVendor() == null)
                    offerVendor.setVisibility(View.GONE);
                else
                    offerVendor.setVisibility(View.VISIBLE);

                if(success.getPrice() == null)
                    offerPrice.setVisibility(View.GONE);
                else
                    offerPrice.setVisibility(View.VISIBLE);

                offerName.setText(success.getName());
                offerDescription.setText(success.getDescription());
                offerVendor.setText(OfferInfo.this.getActivity().getResources().getString(R.string.static_vendor) + " " + success.getVendor());
                offerPrice.setText(OfferInfo.this.getActivity().getResources().getString(R.string.static_price) + " " + success.getPrice() + " " + OfferInfo.this.getActivity().getResources().getString(R.string.static_exchange));
                Picasso.with(OfferInfo.this.getActivity())
                        .load(Constants.STATIC_SERVER + success.getPicture())
                        .into(offerPicture);
                offerScrollView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String error) {
                ((MainActivity) OfferInfo.this.getActivity()).hideProgressBar();
            }
        });
    }
}
