package com.linzon.ru.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 01.04.2016.
 */
public class CustomOfferData {
    public String TYPE;
    public String BC;
    public String PWR;
    public String AX;
    public String CYL;
    public String COLOR;

    public CustomOfferData(String TYPE, String BC, String PWR, String AX, String CYL, String COLOR) {
        this.TYPE = TYPE;
        this.BC = BC;
        this.PWR = PWR;
        this.AX = AX;
        this.CYL = CYL;
        this.COLOR = COLOR;
    }

    public String toString() {
        JSONObject object = new JSONObject();
        try {
            if(this.TYPE != null) {
                object.put("TYPE", this.TYPE);
            }
            if(this.BC != null) {
                object.put("BC", this.BC);
            }
            if(this.PWR != null) {
                object.put("PWR", this.PWR);
            }
            if(this.AX != null) {
                object.put("AX", this.AX);
            }
            if(this.CYL != null) {
                object.put("CYL", this.CYL);
            }
            if(this.COLOR != null) {
                object.put("COLOR", this.COLOR);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    public static String toCompactString(String data) {
        JSONObject dataObj = null;
        String dataString = "";
        if(data == null) {
            return "";
        }
        try {
            dataObj = new JSONObject(data);
            /*if(dataObj.has("TYPE")) {
                dataString += dataObj.getString("TYPE") + ", ";
            }*/
            if(dataObj.has("BC")) {
                dataString += dataObj.getString("BC") + " | ";
            }
            if(dataObj.has("PWR")) {
                dataString += dataObj.getString("PWR") + " | ";
            }
            if(dataObj.has("AX")) {
                dataString += dataObj.getString("AX") + " | ";
            }
            if(dataObj.has("CYL")) {
                dataString += dataObj.getString("CYL") + " | ";
            }
            if(dataObj.has("COLOR")) {
                dataString += dataObj.getString("COLOR") + " | ";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataString.length() != 0 ?  dataString.substring(0, dataString.length() - 3) : dataString;
    }
}
