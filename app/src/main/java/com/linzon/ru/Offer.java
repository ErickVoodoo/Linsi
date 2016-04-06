package com.linzon.ru;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.linzon.ru.common.Constants;
import com.linzon.ru.database.DBAsync;
import com.linzon.ru.database.DBHelper;
import com.linzon.ru.models.CustomOfferData;
import com.linzon.ru.models.OOffer;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Date;

public class Offer extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    int selectedOffer;
    String selectedCategory;

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

    ProgressBar progressBar;

    Toolbar toolbar;
    Button addToBasket;
    Button offerButtonShowDescription;

    CheckBox checkBoxLeftEye;
    CheckBox checkBoxRightEye;

    private OOffer selectedOfferObject;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_info);

        Intent intent = this.getIntent();
        selectedOffer = Integer.parseInt(intent.getStringExtra("OFFER_ID"));
        selectedCategory = intent.getStringExtra("CATEGORY_ID");

        initToolbar();
        setCheckBox();
        setTextView();
        setProgressBar();
        setImageView();
        setSpinners();
        setScrollView();
        setOffer();
        setButton();
    }

    public void setOffer() {
        showProgressBar();
        loadOfferInfo();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.offerToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Offer.this.finish();
            }
        });
    }

    private void setCheckBox() {
        checkBoxLeftEye = (CheckBox) findViewById(R.id.checkBoxLeft);
        checkBoxRightEye = (CheckBox) findViewById(R.id.checkBoxRight);

        checkBoxLeftEye.setOnCheckedChangeListener(this);
        checkBoxRightEye.setOnCheckedChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle(Constants.CATEGORIES.get(selectedCategory));
    }

    public void setProgressBar() {
        progressBar = (ProgressBar) findViewById(R.id.progressBarOffer);
    }

    public void setImageView() {
        offerPicture = (ImageView) findViewById(R.id.offerPicture);
    }

    public void setButton() {
        offerButtonShowDescription = (Button) findViewById(R.id.offerButtonShowDescription);
        offerButtonShowDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerButtonShowDescription.setVisibility(View.GONE);
                offerDescription.setMaxLines(100);
            }
        });
        addToBasket = (Button) findViewById(R.id.offerAddToChart);
        addToBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != selectedOfferObject) {
                    if (count != 0) {
                        if (checkBoxLeftEye.isChecked()) {
                            ContentValues order = DBHelper.setBasketContentValues(
                                    selectedOfferObject.getId(),
                                    selectedOfferObject.getName(),
                                    offerCountLeft.getSelectedItem().toString(),
                                    selectedOfferObject.getPrice(),
                                    !Arrays.asList(Constants.NotALins).contains(selectedCategory) ? createCustomJsonData(
                                            "левый",
                                            offerBCLeft.getSelectedItem(),
                                            offerPWRLeft.getSelectedItem(),
                                            offerAXLeft.getSelectedItem(),
                                            offerCYLLeft.getSelectedItem(),
                                            offerCOLORLeft.getSelectedItem()
                                    ) : null,
                                    Constants.STATUS_OPEN,
                                    new Date().toString(),
                                    "");
                            DBHelper.getInstance().insertToBasket(order, offerCountLeft.getSelectedItem().toString());
                            //DBHelper.getInstance().insertRows(DBHelper.BASKET, order);
                        }
                        if (checkBoxRightEye.isChecked()) {
                            ContentValues order = DBHelper.setBasketContentValues(
                                    selectedOfferObject.getId(),
                                    selectedOfferObject.getName(),
                                    offerCountRight.getSelectedItem().toString(),
                                    selectedOfferObject.getPrice(),
                                    !Arrays.asList(Constants.NotALins).contains(selectedCategory) ? createCustomJsonData(
                                            "правый",
                                            offerBCRight.getSelectedItem(),
                                            offerPWRRight.getSelectedItem(),
                                            offerAXRight.getSelectedItem(),
                                            offerCYLRight.getSelectedItem(),
                                            offerCOLORRight.getSelectedItem()
                                    ) : null,
                                    Constants.STATUS_OPEN,
                                    new Date().toString(),
                                    "");
                            DBHelper.getInstance().insertToBasket(order, offerCountRight.getSelectedItem().toString());
                            //DBHelper.getInstance().insertRows(DBHelper.BASKET, order);
                        }

                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Добавлено в корзину", Snackbar.LENGTH_LONG).setAction("В корзину", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Offer.this, Basket.class);
                                Offer.this.startActivity(intent);
                            }
                        });

                        View view = snack.getView();
                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                        view.setLayoutParams(params);
                        snack.show();
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "Нельзя добавить пустой заказ", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void setTextView() {
        offerName = (TextView) findViewById(R.id.offerName);
        offerVendor = (TextView) findViewById(R.id.offerVendor);
        offerDescription = (TextView) findViewById(R.id.offerDescription);
        offerPrice = (TextView) findViewById(R.id.offerPrice);
    }

    public void setSpinners() {
        offerCountRight = (Spinner) findViewById(R.id.offerCountRight);
        offerCountLeft = (Spinner) findViewById(R.id.offerCountLeft);
        offerPWRLeft = (Spinner) findViewById(R.id.offerPWRLeft);
        offerPWRRight = (Spinner) findViewById(R.id.offerPWRRight);
        offerBCLeft = (Spinner) findViewById(R.id.offerBCLeft);
        offerBCRight = (Spinner) findViewById(R.id.offerBCRight);
        offerAXLeft = (Spinner) findViewById(R.id.offerAXLeft);
        offerAXRight = (Spinner) findViewById(R.id.offerAXRight);
        offerCYLLeft = (Spinner) findViewById(R.id.offerCYLLeft);
        offerCYLRight = (Spinner) findViewById(R.id.offerCYLRight);
        offerCOLORLeft = (Spinner) findViewById(R.id.offerCOLORLeft);
        offerCOLORRight = (Spinner) findViewById(R.id.offerCOLORRight);

        offerCountRight.setOnItemSelectedListener(this);
        offerCountLeft.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Offer.this, android.R.layout.simple_spinner_item, Constants.linsCount);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        offerCountLeft.setAdapter(adapter);
        offerCountLeft.setSelection(0);

        offerCountRight.setAdapter(adapter);
        offerCountRight.setSelection(0);
    }

    public void setScrollView() {
        offerScrollView = (ScrollView) findViewById(R.id.offerScrollView);
    }

    public void loadOfferInfo() {
        offerScrollView.setVisibility(View.GONE);
        DBAsync.asyncGetOfferInfo(this.selectedOffer, new DBAsync.CallbackGetOffer() {
            @Override
            public void onSuccess(OOffer success) {
                selectedOfferObject = success;
                if (success.getVendor() == null)
                    offerVendor.setVisibility(View.GONE);
                else
                    offerVendor.setText(Offer.this.getResources().getString(R.string.static_vendor) + " " + success.getVendor());

                if (success.getPrice() == null)
                    offerPrice.setVisibility(View.GONE);
                else
                    recalculatePrice();

                offerName.setText(success.getName());
                offerDescription.setText(success.getDescription());

                Log.e("COUNT", String.valueOf(offerDescription.getLineCount()));

                offerDescription.post(new Runnable() {
                    @Override
                    public void run() {
                        if(offerDescription.getLineCount() < 3) {
                            offerButtonShowDescription.setVisibility(View.GONE);
                        } else {
                            offerDescription.setMaxLines(3);
                            offerDescription.setEllipsize(TextUtils.TruncateAt.END);
                        }
                    }
                });

                Picasso.with(Offer.this)
                        .load(Constants.STATIC_SERVER + success.getPicture())
                        .into(offerPicture);

                if (Arrays.asList(Constants.NotALins).contains(selectedCategory)) {
                    hideNotNecessaryView();
                } else {
                    DBAsync.asyncGetOfferInfo(Offer.this.selectedOffer, new DBAsync.CallbackGetOffer() {
                        @Override
                        public void onSuccess(OOffer success) {
                            if (Arrays.asList(Constants.NotALins).contains(success.getCategoryId())) {
                                hideNotNecessaryView();
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });

                    if (success.getParam_PWR() != null && success.getParam_PWR().length != 0 && !success.getParam_PWR()[0].equals("")) {
                        Double[] ss = stringToDouble(success.getParam_PWR());
                        Arrays.sort(ss);

                        ArrayAdapter<String> adapterPWR = new ArrayAdapter<String>(Offer.this, android.R.layout.simple_spinner_item, doubleToString(ss));
                        adapterPWR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        offerPWRLeft.setAdapter(adapterPWR);
                        offerPWRLeft.setSelection(success.getParam_PWR().length / 2);
                        offerPWRRight.setAdapter(adapterPWR);
                        offerPWRRight.setSelection(success.getParam_PWR().length / 2);
                    } else {
                        findViewById(R.id.mainOfferLayoutPWR).setVisibility(View.GONE);
                        findViewById(R.id.viewLinePWR).setVisibility(View.GONE);
                    }

                    if (success.getParam_BC() != null && success.getParam_BC().length != 0 && !success.getParam_BC()[0].equals("")) {
                        ArrayAdapter<String> adapterBC = new ArrayAdapter<String>(Offer.this, android.R.layout.simple_spinner_item, success.getParam_BC());
                        adapterBC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        offerBCLeft.setAdapter(adapterBC);
                        offerBCLeft.setSelection(success.getParam_BC().length / 2);
                        offerBCRight.setAdapter(adapterBC);
                        offerBCRight.setSelection(success.getParam_BC().length / 2);
                    } else {
                        findViewById(R.id.mainOfferLayoutBC).setVisibility(View.GONE);
                        findViewById(R.id.viewLineBC).setVisibility(View.GONE);
                    }

                    if (success.getParam_AX() != null && success.getParam_AX().length != 0 && !success.getParam_AX()[0].equals("")) {
                        ArrayAdapter<String> adapterAX = new ArrayAdapter<String>(Offer.this, android.R.layout.simple_spinner_item, success.getParam_AX());
                        adapterAX.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        offerAXLeft.setAdapter(adapterAX);
                        offerAXLeft.setSelection(success.getParam_AX().length / 2);
                        offerAXRight.setAdapter(adapterAX);
                        offerAXRight.setSelection(success.getParam_AX().length / 2);
                    } else {
                        findViewById(R.id.mainOfferLayoutAX).setVisibility(View.GONE);
                        findViewById(R.id.viewLineAX).setVisibility(View.GONE);
                    }

                    if (success.getParam_CYL() != null && success.getParam_CYL().length != 0 && !success.getParam_CYL()[0].equals("")) {
                        ArrayAdapter<String> adapterCYL = new ArrayAdapter<String>(Offer.this, android.R.layout.simple_spinner_item, success.getParam_CYL());
                        adapterCYL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        offerCYLLeft.setAdapter(adapterCYL);
                        offerCYLLeft.setSelection(success.getParam_CYL().length / 2);
                        offerCYLRight.setAdapter(adapterCYL);
                        offerCYLRight.setSelection(success.getParam_CYL().length / 2);
                    } else {
                        findViewById(R.id.mainOfferLayoutCYL).setVisibility(View.GONE);
                        findViewById(R.id.viewLineCYL).setVisibility(View.GONE);
                    }

                    if (success.getParam_COLOR() != null && success.getParam_COLOR().length != 0 && !success.getParam_COLOR()[0].equals("")) {
                        ArrayAdapter<String> adapterCOLOR = new ArrayAdapter<String>(Offer.this, android.R.layout.simple_spinner_item, success.getParam_COLOR());
                        adapterCOLOR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        offerCOLORLeft.setAdapter(adapterCOLOR);
                        offerCOLORLeft.setSelection(0);
                        offerCOLORRight.setAdapter(adapterCOLOR);
                        offerCOLORRight.setSelection(0);
                    } else {
                        findViewById(R.id.mainOfferLayoutCOLOR).setVisibility(View.GONE);
                        findViewById(R.id.viewLineCOLOR).setVisibility(View.GONE);
                    }
                }

                Offer.this.hideProgressBar();
                offerScrollView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String error) {
                Offer.this.hideProgressBar();
            }
        });
    }

    private void recalculatePrice() {
        count = (checkBoxLeftEye.isChecked() ? Integer.parseInt(offerCountLeft.getSelectedItem().toString()) : 0) +
                (checkBoxRightEye.isChecked() ? Integer.parseInt(offerCountRight.getSelectedItem().toString()) : 0);
        offerPrice.setText(Offer.this.getResources().getString(R.string.static_price) + " " + Integer.parseInt(selectedOfferObject.getPrice()) * count + " " + Offer.this.getResources().getString(R.string.static_exchange));
    }

    public void hideNotNecessaryView() {
        findViewById(R.id.mainOfferLayoutEye).setVisibility(View.GONE);
        findViewById(R.id.viewLineEye).setVisibility(View.GONE);
        findViewById(R.id.offerParamLayout).setVisibility(View.GONE);
        offerCountRight.setVisibility(View.GONE);
        offerCountLeft.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 2f));
    }

    public Double[] stringToDouble(String[] array) {
        Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Double.parseDouble(array[i].substring(1, array[i].length()));
        }

        return result;
    }

    public String[] doubleToString(Double[] array) {
        String[] s = new String[array.length];

        for (int i = 0; i < s.length; i++)
            s[i] = array[i] > 0 ? "+" + String.valueOf(array[i]) : String.valueOf(array[i]);

        return s;
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.offer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_basket: {
                Intent intent = new Intent(Offer.this, Basket.class);
                Offer.this.startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        recalculatePrice();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        recalculatePrice();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private String createCustomJsonData(Object TYPE, Object BC, Object PWR, Object AX, Object CYL, Object COLOR) {
        CustomOfferData data = new CustomOfferData(
                TYPE != null ? TYPE.toString() : null,
                BC != null ? BC.toString() : null,
                PWR != null ? PWR.toString() : null,
                AX != null ? AX.toString() : null,
                CYL != null ? CYL.toString() : null,
                COLOR != null ? COLOR.toString() : null
        );
        return data.toString();
    }
}