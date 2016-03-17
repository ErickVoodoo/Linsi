package com.linzon.ru.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by erick on 16.10.15.
 */
public class OShopOffers implements Serializable{
    private ArrayList<OOffer> offer;

    public ArrayList<OOffer> getOffer() {
        return offer;
    }

    public void setOffer(ArrayList<OOffer> offer) {
        this.offer = offer;
    }
}
