package com.linzon.ru.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.linzon.ru.MainActivity;
import com.linzon.ru.R;
import com.linzon.ru.common.Constants;
import com.linzon.ru.database.DBAsync;
import com.linzon.ru.models.OOffer;
import com.squareup.picasso.Picasso;

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
    Spinner offerPWRLeft;
    Spinner offerPWRRight;
    Spinner offerBCLeft;
    Spinner offerBCRight;
    Spinner offerAXRight;
    Spinner offerAXLeft;
    Spinner offerCYLRight;
    Spinner offerCYLLeft;
    Spinner offerCOLORRight;
    Spinner offerCOLORLeft;

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
        offerPWRLeft = (Spinner) view.findViewById(R.id.offerPWRLeft);
        offerPWRRight = (Spinner) view.findViewById(R.id.offerPWRRight);
        offerBCLeft = (Spinner) view.findViewById(R.id.offerBCLeft);
        offerBCRight = (Spinner) view.findViewById(R.id.offerBCRight);
        offerAXLeft = (Spinner) view.findViewById(R.id.offerAXLeft);
        offerAXRight = (Spinner) view.findViewById(R.id.offerAXRight);
        offerCYLLeft = (Spinner) view.findViewById(R.id.offerCYLLeft);
        offerCYLRight = (Spinner) view.findViewById(R.id.offerCYLRight);
        offerCOLORLeft = (Spinner) view.findViewById(R.id.offerCOLORLeft);
        offerCOLORRight = (Spinner) view.findViewById(R.id.offerCOLORRight);

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

                ArrayAdapter<String> adapterPWR = new ArrayAdapter<String>(OfferInfo.this.getActivity(), android.R.layout.simple_spinner_item, success.getParam_PWR());
                adapterPWR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                offerPWRLeft.setAdapter(adapterPWR);
                offerPWRLeft.setSelection(success.getParam_PWR().length / 2);
                offerPWRRight.setAdapter(adapterPWR);
                offerPWRRight.setSelection(success.getParam_PWR().length / 2);

                ArrayAdapter<String> adapterBC = new ArrayAdapter<String>(OfferInfo.this.getActivity(), android.R.layout.simple_spinner_item, success.getParam_BC());
                adapterBC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                offerBCLeft.setAdapter(adapterBC);
                offerBCLeft.setSelection(success.getParam_BC().length / 2);
                offerBCRight.setAdapter(adapterBC);
                offerBCRight.setSelection(success.getParam_BC().length / 2);

                ArrayAdapter<String> adapterAX = new ArrayAdapter<String>(OfferInfo.this.getActivity(), android.R.layout.simple_spinner_item, success.getParam_AX());
                adapterAX.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                offerAXLeft.setAdapter(adapterAX);
                offerAXLeft.setSelection(success.getParam_AX().length / 2);
                offerAXRight.setAdapter(adapterAX);
                offerAXRight.setSelection(success.getParam_AX().length / 2);

                ArrayAdapter<String> adapterCYL = new ArrayAdapter<String>(OfferInfo.this.getActivity(), android.R.layout.simple_spinner_item, success.getParam_CYL());
                adapterCYL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                offerCYLLeft.setAdapter(adapterCYL);
                offerCYLLeft.setSelection(success.getParam_CYL().length / 2);
                offerCYLRight.setAdapter(adapterCYL);
                offerCYLRight.setSelection(success.getParam_CYL().length / 2);

                ArrayAdapter<String> adapterCOLOR = new ArrayAdapter<String>(OfferInfo.this.getActivity(), android.R.layout.simple_spinner_item, success.getParam_COLOR());
                adapterCOLOR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                offerCOLORLeft.setAdapter(adapterCOLOR);
                offerCOLORLeft.setSelection(success.getParam_COLOR().length / 2);
                offerCOLORRight.setAdapter(adapterCOLOR);
                offerCOLORRight.setSelection(success.getParam_COLOR().length / 2);
            }

            @Override
            public void onError(String error) {
                ((MainActivity) OfferInfo.this.getActivity()).hideProgressBar();
            }
        });
    }
}
