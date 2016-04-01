package com.linzon.ru.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 01.04.2016.
 */
public class CustomOfferData {
    public String R_BC;
    public String L_BC;
    public String R_PWR;
    public String L_PWR;
    public String R_AX;
    public String L_AX;
    public String R_CYL;
    public String L_CYL;
    public String R_COLOR;
    public String L_COLOR;

    public CustomOfferData(String R_BC, String L_BC, String R_PWR, String L_PWR, String R_AX, String L_AX, String R_CYL, String L_CYL, String R_COLOR, String L_COLOR) {
        this.R_BC = R_BC;
        this.L_BC = L_BC;
        this.R_PWR = R_PWR;
        this.L_PWR = L_PWR;
        this.R_AX = R_AX;
        this.L_AX = L_AX;
        this.R_CYL = R_CYL;
        this.L_CYL = L_CYL;
        this.R_COLOR = R_COLOR;
        this.L_COLOR = L_COLOR;
    }

    public String getR_BC() {
        return R_BC;
    }

    public void setR_BC(String r_BC) {
        R_BC = r_BC;
    }

    public String getL_BC() {
        return L_BC;
    }

    public void setL_BC(String l_BC) {
        L_BC = l_BC;
    }

    public String getR_PWR() {
        return R_PWR;
    }

    public void setR_PWR(String r_PWR) {
        R_PWR = r_PWR;
    }

    public String getL_PWR() {
        return L_PWR;
    }

    public void setL_PWR(String l_PWR) {
        L_PWR = l_PWR;
    }

    public String getR_AX() {
        return R_AX;
    }

    public void setR_AX(String r_AX) {
        R_AX = r_AX;
    }

    public String getL_AX() {
        return L_AX;
    }

    public void setL_AX(String l_AX) {
        L_AX = l_AX;
    }

    public String getR_CYL() {
        return R_CYL;
    }

    public void setR_CYL(String r_CYL) {
        R_CYL = r_CYL;
    }

    public String getL_CYL() {
        return L_CYL;
    }

    public void setL_CYL(String l_CYL) {
        L_CYL = l_CYL;
    }

    public String getR_COLOR() {
        return R_COLOR;
    }

    public void setR_COLOR(String r_COLOR) {
        R_COLOR = r_COLOR;
    }

    public String getL_COLOR() {
        return L_COLOR;
    }

    public void setL_COLOR(String l_COLOR) {
        L_COLOR = l_COLOR;
    }

    public String toString() {
        JSONObject object = new JSONObject();
        try {
            if(this.L_AX != null) {
                object.put("L_AX", this.L_AX);
            }
            if(this.L_BC != null) {
                object.put("L_BC", this.L_BC);
            }
            if(this.L_COLOR != null) {
                object.put("L_COLOR", this.L_COLOR);
            }
            if(this.L_CYL != null) {
                object.put("L_CYL", this.L_CYL);
            }
            if(this.L_PWR != null) {
                object.put("L_PWR", this.L_PWR);
            }
            if(this.R_AX != null) {
                object.put("R_AX", this.R_AX);
            }
            if(this.R_BC != null) {
                object.put("R_BC", this.R_BC);
            }
            if(this.R_COLOR != null) {
                object.put("R_COLOR", this.R_COLOR);
            }
            if(this.R_CYL != null) {
                object.put("R_CYL", this.R_CYL);
            }
            if(this.R_PWR != null) {
                object.put("R_PWR", this.R_PWR);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
