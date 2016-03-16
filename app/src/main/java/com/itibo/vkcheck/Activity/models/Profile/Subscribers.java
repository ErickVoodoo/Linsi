package com.itibo.vkcheck.Activity.models.Profile;

/**
 * Created by erick on 27.10.15.
 */
public class Subscribers {
    private String count;

    private String[] items;

    public String getCount ()
    {
        return count;
    }

    public void setCount (String count)
    {
        this.count = count;
    }

    public String[] getItems ()
    {
        return items;
    }

    public void setItems (String[] items)
    {
        this.items = items;
    }
}
