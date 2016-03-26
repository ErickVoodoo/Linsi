package com.linzon.ru;

import android.app.Application;
import android.content.Intent;

import com.linzon.ru.common.SharedProperty;
import com.linzon.ru.database.DBHelper;
import com.linzon.ru.models.POffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class App extends Application {

    private ArrayList<POffer> currentOffer;

    @Override
    public void onCreate() {
        super.onCreate();
        DBHelper.init(getApplicationContext());
        SharedProperty.init(getApplicationContext());
        //DBHelper.getInstance().dropDatabase();
        /*Intent service = new Intent(this, NotificationService.class);
        service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startService(service);*/
    }

    public ArrayList<POffer> getPriceOffers() {
        Collections.sort(currentOffer, new Comparator<POffer>() {
            @Override
            public int compare(POffer lhs, POffer rhs) {
                return rhs.getRate().compareTo(lhs.getRate());
            }
        });
        return currentOffer;
    }

    /*public POffer getPriceOfferById(int id) {
        return currentOffer.fil;
    }*/

    public void setPriceOffers(ArrayList<POffer> currentOffer) {
        this.currentOffer = currentOffer;
    }
}
