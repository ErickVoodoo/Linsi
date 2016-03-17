package com.linzon.ru;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linzon.ru.common.SharedProperty;
import com.linzon.ru.database.DBHelper;
import com.linzon.ru.fragments.Popular;
import com.linzon.ru.fragments.CategoryOffers;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView navHeaderUsername;
    public FloatingActionButton fab;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    private final static String CATEGORY_TAG = "CATEGORY_FRAGMENT";
    private final static String POPULAR_TAG = "POPULAR_FRAGMENT";

    private NavigationView navigationView;
    private Popular popularFragment;
    private CategoryOffers categoryOffersFragment;
    private Fragment selectedFragment = null;

    private ProgressBar progressBarMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ContentValues contentValues = DBHelper.setUserContentValues("111", 0);
//        long i = DBHelper.getInstance().insertRows(contentValues);
//
//        Log.e("INSERTED", String.valueOf(i));
        setProgressRing();
        initToolbar();
        initNavigationView();
        initDrawer();
        initFab();
        initTextViews();
        showPopular();
    }

    private void setProgressRing() {
        progressBarMain = (ProgressBar) findViewById(R.id.progressBarMain);
    }

    private void showPopular() {
        if (!(selectedFragment instanceof Popular)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if (null == popularFragment) {
                popularFragment = new Popular();
                fragmentTransaction.add(R.id.mainPager, popularFragment, POPULAR_TAG);
            }
            fragmentTransaction.show(popularFragment);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
            selectedFragment = popularFragment;
        }
    }

    private void showCategory() {
        if (!(selectedFragment instanceof CategoryOffers)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if (null == categoryOffersFragment) {
                categoryOffersFragment = new CategoryOffers();
                fragmentTransaction.add(R.id.mainPager, categoryOffersFragment, CATEGORY_TAG);
            }
            fragmentTransaction.show(categoryOffersFragment);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
            selectedFragment = categoryOffersFragment;
        }
    }

    private void showSendNotification() {
        /*if (!(selectedFragment instanceof SendNotification)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if (null == sendFragment) {
                sendFragment = new SendNotification();
                fragmentTransaction.add(R.id.mainPager, sendFragment, SEND_TAG);
            }
            fragmentTransaction.show(sendFragment);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
            selectedFragment = sendFragment;
        }*/
    }

    private void hideFragments() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        popularFragment = (Popular) getFragmentManager().findFragmentByTag(POPULAR_TAG);
        if (null != popularFragment) {
            fragmentTransaction.hide(popularFragment);
        }

        categoryOffersFragment = (CategoryOffers) getFragmentManager().findFragmentByTag(CATEGORY_TAG);
        if (null != categoryOffersFragment) {
            fragmentTransaction.hide(categoryOffersFragment);
        }

        /*sendFragment = (SendNotification) getFragmentManager().findFragmentByTag(SEND_TAG);
        if (null != sendFragment) {
            fragmentTransaction.hide(sendFragment);
        }*/

        fragmentTransaction.commit();
        getFragmentManager().executePendingTransactions();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_popular).setChecked(true);
        toolbar.setTitle("Популярное");
    }

    private void initDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarMain.setVisibility(View.VISIBLE);
                if (selectedFragment instanceof CategoryOffers) {
                    Vk.asyncSearchUsers(search_status, new Vk.CallbackArraySearchModel() {
                        @Override
                        public void onSuccess(ArrayList<SearchModel> model) {
                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.searchRecycler);
                            recyclerView.setAdapter(new SearchAdapter(model, MainActivity.this, R.layout.fragment_search_item));
                            progressBarMain.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                }
                if (selectedFragment instanceof Popular) {
                    String users = DBHelper.getStringUsers(null);
                    if (users != null) {
                        Vk.asyncGetUsersFull(users, new Vk.CallbackArraySearchModel() {
                            @Override
                            public void onSuccess(ArrayList<SearchModel> model) {
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.userRecycler);
                                recyclerView.setAdapter(new SearchAdapter(model, MainActivity.this, R.layout.fragment_user_item));
                                progressBarMain.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                    }
                    fab.animate().cancel();
                }
            }
        });*/
    }

    private void initTextViews() {
        navHeaderUsername = (TextView)((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.navHeaderUsername);
        //String vcal = SharedProperty.getInstance().getCurrentName();
        navHeaderUsername.setText("Привет, ");
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_popular: {
                uncheckItems();
                toolbar.setTitle("Пользователи");
                fab.setImageResource(R.drawable.ic_loop_white_24dp);
                item.setChecked(true);
                showPopular();
                showFab();
                invalidateOptionsMenu();
                break;
            }
            case R.id.nav_send: {
                uncheckItems();
                toolbar.setTitle("Поиск");
                fab.setImageResource(R.drawable.ic_search_white_24dp);
                item.setChecked(true);
                showCategory();
                showFab();
                invalidateOptionsMenu();
                break;
            }
            case R.id.nav_about: {
                uncheckItems();
                toolbar.setTitle("Поделиться");
                item.setChecked(true);
                showSendNotification();
                hideFab();
                invalidateOptionsMenu();
                break;
            }
            case R.id.nav_logout: {
                SharedProperty.getInstance().setCurrentName("");
                Intent intent = new Intent(MainActivity.this, StartScreen.class);
                startActivity(intent);
                this.finish();
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void uncheckItems() {
        Menu items = navigationView.getMenu();

        for (int i = 0; i < items.size(); i++) {
            MenuItem item = items.getItem(i);
            item.setChecked(false);
        }
    }

    /*private int countCheckedItems() {
        Menu items = navigationView.getMenu();
        int count = 0;

        for (int i = 0; i < items.size(); i++) {
            MenuItem item = items.getItem(i);
            if (item.isChecked()) {
                count++;
            }
        }
        return count;
    }*/

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
                || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            showPopular();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showFab() {
        fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    public void hideFab() {
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        fab.animate().translationY(fab.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }
}
