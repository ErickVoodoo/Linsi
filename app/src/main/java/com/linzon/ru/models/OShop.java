package com.linzon.ru.models;

import java.io.Serializable;

/**
 * Created by erick on 16.10.15.
 */
public class OShop implements Serializable{
    private OShopOffers offers;

    private String company;

    private String name;

    private OCategories categories;

    private String url;

    public OShopOffers getOffers() {
        return offers;
    }

    public void setOffers(OShopOffers offers) {
        this.offers = offers;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OCategories getCategories() {
        return categories;
    }

    public void setCategories(OCategories categories) {
        this.categories = categories;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
