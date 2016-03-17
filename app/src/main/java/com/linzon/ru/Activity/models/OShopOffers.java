package com.linzon.ru.Activity.models;

import java.io.Serializable;

/**
 * Created by erick on 16.10.15.
 */
public class OShopOffers implements Serializable{
    private OOffer[] offer;

    public OOffer[] getOffer() {
        return offer;
    }

    public void setOffer(OOffer[] offer) {
        this.offer = offer;
    }
}
