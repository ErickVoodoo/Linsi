package com.linzon.ru;

import android.app.Application;
import android.content.Intent;

import com.linzon.ru.common.SharedProperty;
import com.linzon.ru.database.DBHelper;
import com.linzon.ru.models.POffer;
import com.linzon.ru.service.BasketService;

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

        Intent service = new Intent(this, BasketService.class);
        service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startService(service);

        //DBHelper.getInstance().dropDatabase();
        /*Intent service = new Intent(this, BasketService.class);
        service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startService(service);*/
    }

    public ArrayList<POffer> getPriceOffers() {
        Collections.sort(currentOffer, new Comparator<POffer>() {
            @Override
            public int compare(POffer lhs, POffer rhs) {
                try {
                    if(Integer.parseInt(lhs.getRate()) > Integer.parseInt(rhs.getRate())){
                        return -1;
                    }else if(Integer.parseInt(lhs.getRate()) < Integer.parseInt(rhs.getRate())){
                        return  1;
                    }else if(Integer.parseInt(lhs.getRate()) == Integer.parseInt(rhs.getRate())){
                        return 0;
                    }
                    return 0;
                }catch (NumberFormatException e) {
                    System.err.println("Неверный формат строки!");
                }
                return 0;
            }
        });
        return currentOffer;
    }

    public void setPriceOffers(ArrayList<POffer> currentOffer) {
        this.currentOffer = currentOffer;
    }
}
