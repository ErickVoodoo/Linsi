package com.linzon.ru.models;

import java.io.Serializable;

/**
 * Created by erick on 16.10.15.
 */
public class OOffer implements Serializable{
    private String picture;

    private String id;

    private String price;

    private String description;

    private String vendor;

    private String currencyId;

    private String name;

    private String categoryId;

    private String[] param_BC;

    private String[] param_PWR;

    private String[] param_AX;

    private String[] param_CYL;

    private String[] param_COLOR;

    public String[] getParam_BC() {
        return param_BC;
    }

    public void setParam_BC(String[] param_BC) {
        this.param_BC = param_BC;
    }

    public String[] getParam_PWR() {
        return param_PWR;
    }

    public void setParam_PWR(String[] param_PWR) {
        this.param_PWR = param_PWR;
    }

    public String[] getParam_AX() {
        return param_AX;
    }

    public void setParam_AX(String[] param_AX) {
        this.param_AX = param_AX;
    }

    public String[] getParam_CYL() {
        return param_CYL;
    }

    public void setParam_CYL(String[] param_CYL) {
        this.param_CYL = param_CYL;
    }

    public String[] getParam_COLOR() {
        return param_COLOR;
    }

    public void setParam_COLOR(String[] param_COLOR) {
        this.param_COLOR = param_COLOR;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
