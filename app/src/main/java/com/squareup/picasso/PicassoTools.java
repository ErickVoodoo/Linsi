package com.squareup.picasso;

import android.util.Log;

public class PicassoTools {

    public static void clearCache (Picasso p) {
        Log.e("CACHE", String.valueOf(p.cache.size()));
        p.cache.clear();
        Log.e("CACHE2", String.valueOf(p.cache.size()));
    }
}