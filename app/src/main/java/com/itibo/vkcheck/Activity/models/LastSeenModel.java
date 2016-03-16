package com.itibo.vkcheck.Activity.models;

import java.io.Serializable;

/**
 * Created by erick on 16.10.15.
 */
public class LastSeenModel implements Serializable{
    public int time;
    public int platform;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }
}
