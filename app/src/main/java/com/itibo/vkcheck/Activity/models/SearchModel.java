package com.itibo.vkcheck.Activity.models;

import java.io.Serializable;

/**
 * Created by erick on 14.10.15.
 */
public class SearchModel implements Serializable{
    public int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhoto_100() {
        return photo_100;
    }

    public void setPhoto_100(String photo_100) {
        this.photo_100 = photo_100;
    }

    public String first_name;
    public String last_name;
    public String photo_100;
    public String photo_400_orig;
    public boolean is_added;

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int online;

    public LastSeenModel getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(LastSeenModel last_seen) {
        this.last_seen = last_seen;
    }

    public LastSeenModel last_seen;

    public String getPhoto_200_orig() {
        return photo_200_orig;
    }

    public void setPhoto_200_orig(String photo_200_orig) {
        this.photo_200_orig = photo_200_orig;
    }

    public String getPhoto_200() {
        return photo_200;
    }

    public void setPhoto_200(String photo_200) {
        this.photo_200 = photo_200;
    }

    public String photo_200;
    public String photo_200_orig;

    public String getPhoto_400_orig() {
        return photo_400_orig;
    }

    public void setPhoto_400_orig(String photo_400_orig) {
        this.photo_400_orig = photo_400_orig;
    }
}
