package com.linzon.ru.models;

/**
 * Created by Admin on 17.03.2016.
 */
public class PShop {
    private PShopOffers offers;

    private String company;

    private String name;

    private PCategories categories;

    public PShopOffers getOffers() {
        return offers;
    }

    public void setOffers(PShopOffers offers) {
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

    public PCategories getCategories() {
        return categories;
    }

    public void setCategories(PCategories categories) {
        this.categories = categories;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;
}
