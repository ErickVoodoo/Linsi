package com.linzon.ru.common;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.linzon.ru.MainActivity;

public class RViewScroll extends RecyclerView.OnScrollListener {
    private Activity activity;
    private static final int HIDE_THRESHOLD = 200;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    private RViewScroll() {

    }

    public RViewScroll(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
            ((MainActivity) this.activity).hideFab();
            controlsVisible = false;
            scrolledDistance = 0;
        } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
            ((MainActivity) this.activity).showFab();
            controlsVisible = true;
            scrolledDistance = 0;
        }

        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            scrolledDistance += dy;
        }
    }
}
