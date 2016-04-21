package com.linzon.ru;

import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.linzon.ru.common.Constants;
import com.linzon.ru.common.TimeCommon;
import com.linzon.ru.common.Values;
import com.linzon.ru.database.DBAsync;
import com.linzon.ru.database.DBHelper;
import com.linzon.ru.models.CustomOfferData;
import com.linzon.ru.models.OOffer;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

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
    private boolean isDescriptionOpened = false;

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

    private void setOffer() {
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

    private void setProgressBar() {
        progressBar = (ProgressBar) findViewById(R.id.progressBarOffer);
    }

    private void setImageView() {
        offerPicture = (ImageView) findViewById(R.id.offerPicture);
    }

    private void setButton() {
        offerButtonShowDescription = (Button) findViewById(R.id.offerButtonShowDescription);
        offerButtonShowDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDescriptionOpened) {
                    isDescriptionOpened = true;
                    offerButtonShowDescription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_expand_less_black_24dp, 0, 0, 0);
                    // offerDescription.setMaxLines(100);
                    ObjectAnimator animation = ObjectAnimator.ofInt(
                            offerDescription,
                            "maxLines",
                            25);
                    animation.setDuration(350);
                    animation.start();
                } else {
                    isDescriptionOpened = false;
                    ObjectAnimator animation = ObjectAnimator.ofInt(
                            offerDescription,
                            "maxLines",
                            3);
                    animation.setDuration(350);
                    animation.start();
                    offerButtonShowDescription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_expand_more_black_24dp, 0, 0, 0);
                }
            }
        });
        addToBasket = (Button) findViewById(R.id.offerAddToChart);
        addToBasket.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (null != selectedOfferObject) {
                   if (checkBoxRightEye.isChecked() && (offerAXRight.getSelectedItemPosition() == 0 ||
                           offerBCRight.getSelectedItemPosition() == 0 ||
                           offerPWRRight.getSelectedItemPosition() == 0 ||
                           offerCYLRight.getSelectedItemPosition() == 0 ||
                           offerCOLORRight.getSelectedItemPosition() == 0)) {
                       Values.showTopSnackBar(Offer.this, "Выберите все параметры", null, null, Snackbar.LENGTH_SHORT);
                       return;
                   }
                   if (checkBoxLeftEye.isChecked() && (offerAXLeft.getSelectedItemPosition() == 0 ||
                           offerBCLeft.getSelectedItemPosition() == 0 ||
                           offerPWRLeft.getSelectedItemPosition() == 0 ||
                           offerCYLLeft.getSelectedItemPosition() == 0 ||
                           offerCOLORLeft.getSelectedItemPosition() == 0)) {
                       Values.showTopSnackBar(Offer.this, "Выберите все параметры", null, null, Snackbar.LENGTH_SHORT);
                       return;
                   }
                   if (count != 0) {
                       if (checkBoxLeftEye.isChecked()) {
                           ContentValues order = DBHelper.setBasketContentValues(
                                   selectedOfferObject.getId(),
                                   null,
                                   selectedOfferObject.getName(),
                                   offerCountLeft.getSelectedItem().toString(),
                                   selectedOfferObject.getPrice(),
                                   !Arrays.asList(Constants.NotALins).contains(selectedCategory) ? Values.createCustomJsonData(
                                           "левый",
                                           offerBCLeft.getSelectedItem(),
                                           offerPWRLeft.getSelectedItem(),
                                           offerAXLeft.getSelectedItem(),
                                           offerCYLLeft.getSelectedItem(),
                                           offerCOLORLeft.getSelectedItem()
                                   ) : null,
                                   Constants.STATUS_OPEN,
                                   String.valueOf(TimeCommon.getUnixTime()),
                                   "");
                           DBHelper.getInstance().insertToBasket(order, offerCountLeft.getSelectedItem().toString());
                       }
                       if (checkBoxRightEye.isChecked()) {
                           ContentValues order = DBHelper.setBasketContentValues(
                                   selectedOfferObject.getId(),
                                   null,
                                   selectedOfferObject.getName(),
                                   offerCountRight.getSelectedItem().toString(),
                                   selectedOfferObject.getPrice(),
                                   !Arrays.asList(Constants.NotALins).contains(selectedCategory) ? Values.createCustomJsonData(
                                           "правый",
                                           offerBCRight.getSelectedItem(),
                                           offerPWRRight.getSelectedItem(),
                                           offerAXRight.getSelectedItem(),
                                           offerCYLRight.getSelectedItem(),
                                           offerCOLORRight.getSelectedItem()
                                   ) : null,
                                   Constants.STATUS_OPEN,
                                   String.valueOf(TimeCommon.getUnixTime()),
                                   "");
                           DBHelper.getInstance().insertToBasket(order, offerCountRight.getSelectedItem().toString());
                       }

                       Values.showTopSnackBar(Offer.this, "Добавлено в корзину", "В корзину", new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               Intent intent = new Intent(Offer.this, Basket.class);
                               Offer.this.startActivity(intent);
                           }
                       }, Snackbar.LENGTH_LONG);
                   } else {
                       Values.showTopSnackBar(Offer.this, "Нельзя добавить пустой заказ в корзину", null, null, Snackbar.LENGTH_SHORT);
                   }
               }
           }
       }

        );
    }

    private void setTextView() {
        offerName = (TextView) findViewById(R.id.offerName);
        offerVendor = (TextView) findViewById(R.id.offerVendor);
        offerDescription = (TextView) findViewById(R.id.offerDescription);
        offerPrice = (TextView) findViewById(R.id.offerPrice);
    }

    private void setSpinners() {
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Offer.this, android.R.layout.simple_spinner_item, Constants.linsCount);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        offerCountLeft.setAdapter(adapter);
        offerCountLeft.setSelection(0);

        offerCountRight.setAdapter(adapter);
        offerCountRight.setSelection(0);
    }

    private void setScrollView() {
        offerScrollView = (ScrollView) findViewById(R.id.offerScrollView);
    }

    private void loadOfferInfo() {
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

                offerDescription.post(new Runnable() {
                    @Override
                    public void run() {
                        if (offerDescription.getLineCount() < 3) {
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
                        Double[] ss = Values.stringToDouble(success.getParam_PWR());
                        Arrays.sort(ss);
                        ArrayAdapter<String> adapterPWR = new ArrayAdapter<String>(Offer.this, android.R.layout.simple_spinner_item, Values.combine(new String[]{"Выбрать"}, Values.doubleToString(ss)));
                        adapterPWR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        offerPWRLeft.setAdapter(adapterPWR);
                        offerPWRRight.setAdapter(adapterPWR);
                    } else {
                        findViewById(R.id.mainOfferLayoutPWR).setVisibility(View.GONE);
                        findViewById(R.id.viewLinePWR).setVisibility(View.GONE);
                    }

                    if (success.getParam_BC() != null && success.getParam_BC().length != 0 && !success.getParam_BC()[0].equals("")) {
                        ArrayAdapter<String> adapterBC = new ArrayAdapter<String>(Offer.this, android.R.layout.simple_spinner_item, Values.combine(new String[]{"Выбрать"}, success.getParam_BC()));
                        adapterBC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        offerBCLeft.setAdapter(adapterBC);
                        offerBCRight.setAdapter(adapterBC);
                    } else {
                        findViewById(R.id.mainOfferLayoutBC).setVisibility(View.GONE);
                        findViewById(R.id.viewLineBC).setVisibility(View.GONE);
                    }

                    if (success.getParam_AX() != null && success.getParam_AX().length != 0 && !success.getParam_AX()[0].equals("")) {
                        ArrayAdapter<String> adapterAX = new ArrayAdapter<String>(Offer.this, android.R.layout.simple_spinner_item, Values.combine(new String[]{"Выбрать"}, success.getParam_AX()));
                        adapterAX.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        offerAXLeft.setAdapter(adapterAX);
                        offerAXRight.setAdapter(adapterAX);
                    } else {
                        findViewById(R.id.mainOfferLayoutAX).setVisibility(View.GONE);
                        findViewById(R.id.viewLineAX).setVisibility(View.GONE);
                    }

                    if (success.getParam_CYL() != null && success.getParam_CYL().length != 0 && !success.getParam_CYL()[0].equals("")) {
                        ArrayAdapter<String> adapterCYL = new ArrayAdapter<String>(Offer.this, android.R.layout.simple_spinner_item, Values.combine(new String[]{"Выбрать"}, success.getParam_CYL()));
                        adapterCYL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        offerCYLLeft.setAdapter(adapterCYL);
                        offerCYLRight.setAdapter(adapterCYL);
                    } else {
                        findViewById(R.id.mainOfferLayoutCYL).setVisibility(View.GONE);
                        findViewById(R.id.viewLineCYL).setVisibility(View.GONE);
                    }

                    if (success.getParam_COLOR() != null && success.getParam_COLOR().length != 0 && !success.getParam_COLOR()[0].equals("")) {
                        ArrayAdapter<String> adapterCOLOR = new ArrayAdapter<String>(Offer.this, android.R.layout.simple_spinner_item, Values.combine(new String[]{"Выбрать"}, success.getParam_COLOR()));
                        adapterCOLOR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        offerCOLORLeft.setAdapter(adapterCOLOR);
                        offerCOLORRight.setAdapter(adapterCOLOR);
                    } else {
                        findViewById(R.id.mainOfferLayoutCOLOR).setVisibility(View.GONE);
                        findViewById(R.id.viewLineCOLOR).setVisibility(View.GONE);
                    }
                }

                Offer.this.hideProgressBar();
                offerScrollView.setVisibility(View.VISIBLE);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        offerCountRight.setOnItemSelectedListener(Offer.this);
                        offerCountLeft.setOnItemSelectedListener(Offer.this);
                        offerPWRLeft.setOnItemSelectedListener(Offer.this);
                        offerPWRRight.setOnItemSelectedListener(Offer.this);
                        offerBCLeft.setOnItemSelectedListener(Offer.this);
                        offerBCRight.setOnItemSelectedListener(Offer.this);
                        offerAXLeft.setOnItemSelectedListener(Offer.this);
                        offerAXRight.setOnItemSelectedListener(Offer.this);
                        offerCYLLeft.setOnItemSelectedListener(Offer.this);
                        offerCYLRight.setOnItemSelectedListener(Offer.this);
                        offerCOLORLeft.setOnItemSelectedListener(Offer.this);
                        offerCOLORRight.setOnItemSelectedListener(Offer.this);
                    }
                }, 2000);
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

    private void hideNotNecessaryView() {
        findViewById(R.id.mainOfferLayoutEye).setVisibility(View.GONE);
        findViewById(R.id.viewLineEye).setVisibility(View.GONE);
        findViewById(R.id.offerParamLayout).setVisibility(View.GONE);
        offerCountRight.setVisibility(View.GONE);
        offerCountLeft.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 2f));
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
        switch (parent.getId()) {
            case R.id.offerAXLeft:
            case R.id.offerBCLeft:
            case R.id.offerCOLORLeft:
            case R.id.offerCYLLeft:
            case R.id.offerCountLeft:
            case R.id.offerPWRLeft: {
                checkBoxLeftEye.setChecked(true);
                break;
            }
            case R.id.offerAXRight:
            case R.id.offerBCRight:
            case R.id.offerCOLORRight:
            case R.id.offerCYLRight:
            case R.id.offerCountRight:
            case R.id.offerPWRRight: {
                checkBoxRightEye.setChecked(true);
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}