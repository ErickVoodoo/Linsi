package com.linzon.ru;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linzon.ru.fragments.About;
import com.linzon.ru.fragments.CategoryOffers;
import com.linzon.ru.fragments.HowToRoad;
import com.linzon.ru.fragments.Popular;
import com.linzon.ru.fragments.PostSend;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView navHeaderUsername;
    public FloatingActionButton fab;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    private final static String CATEGORY_TAG = "CATEGORY_FRAGMENT";
    private final static String POPULAR_TAG = "POPULAR_FRAGMENT";
    private final static String OFFER_TAG = "OFFER_FRAGMENT";
    private final static String ABOUT_TAG = "ABOUT_FRAGMENT";
    private final static String CONTACTS_TAG = "CONTACTS_FRAGMENT";
    private final static String HOWTOROAD_TAG = "HOWTOROAD_FRAGMENT";

    private NavigationView navigationView;

    private Popular popularFragment;
    private CategoryOffers categoryOffersFragment;
    private About aboutFragment;
    private PostSend postSendFragment;
    private HowToRoad howToRoadFragment;
    private Fragment selectedFragment = null;

    private ProgressBar progressBarMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setProgressRing();
        initToolbar();
        initNavigationView();
        initDrawer();
        initFab();
        initTextViews();
        showPopular();
        toolbar.setTitle(this.getResources().getString(R.string.drawer_popular));
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

    private void showCategory(int id) {
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
        if(categoryOffersFragment.selectedCategory != id)
            categoryOffersFragment.setCategory(id);
    }

    private void showAbout() {
        if (!(selectedFragment instanceof About)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if (null == aboutFragment) {
                aboutFragment = new About();
                fragmentTransaction.add(R.id.mainPager, aboutFragment, ABOUT_TAG);
            }
            fragmentTransaction.show(aboutFragment);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
            selectedFragment = aboutFragment;
        }
    }

    private void showPostSend() {
        if (!(selectedFragment instanceof PostSend)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if (null == postSendFragment) {
                postSendFragment = new PostSend();
                fragmentTransaction.add(R.id.mainPager, postSendFragment, CONTACTS_TAG);
            }
            fragmentTransaction.show(postSendFragment);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
            selectedFragment = postSendFragment;
        }
    }

    private void showHowToRoad() {
        if (!(selectedFragment instanceof HowToRoad)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if (null == howToRoadFragment) {
                howToRoadFragment = new HowToRoad();
                fragmentTransaction.add(R.id.mainPager, howToRoadFragment, HOWTOROAD_TAG);
            }
            fragmentTransaction.show(howToRoadFragment);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
            selectedFragment = howToRoadFragment;
        }
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

        aboutFragment = (About) getFragmentManager().findFragmentByTag(ABOUT_TAG);
        if (null != aboutFragment) {
            fragmentTransaction.hide(aboutFragment);
        }

        postSendFragment = (PostSend) getFragmentManager().findFragmentByTag(CONTACTS_TAG);
        if (null != postSendFragment) {
            fragmentTransaction.hide(postSendFragment);
        }

        howToRoadFragment = (HowToRoad) getFragmentManager().findFragmentByTag(HOWTOROAD_TAG);
        if (null != howToRoadFragment) {
            fragmentTransaction.hide(howToRoadFragment);
        }

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
    }

    private void initDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if(selectedFragment instanceof CategoryOffers) {
                    ((CategoryOffers) selectedFragment).loadCategoryItems();
                }
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initTextViews() {
        navHeaderUsername = (TextView)((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.navHeaderUsername);
        navHeaderUsername.setText("Привет, ");
    }


    @Override
    public void onBackPressed() {
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
        toolbar.setTitle(item.getTitle());
        item.setChecked(true);
        invalidateOptionsMenu();
        showFab();
        switch (item.getItemId()) {
            case R.id.nav_popular: {
                showPopular();
                break;
            }
            case R.id.nav_1: {
                showCategory(1);
                break;
            }
            case R.id.nav_10: {
                showCategory(10);
                break;
            }
            case R.id.nav_13: {
                showCategory(13);
                break;
            }
            case R.id.nav_14: {
                showCategory(14);
                break;
            }
            case R.id.nav_15: {
                showCategory(15);
                break;
            }
            case R.id.nav_16: {
                showCategory(16);
                break;
            }
            case R.id.nav_2: {
                showCategory(2);
                break;
            }
            case R.id.nav_7: {
                showCategory(7);
                break;
            }
            case R.id.nav_postsend: {
                showPostSend();
                break;
            }
            case R.id.nav_howtoroad: {
                showHowToRoad();
                break;
            }
            case R.id.nav_about: {
                showAbout();
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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

    public void showProgressBar() {
        progressBarMain.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBarMain.setVisibility(View.GONE);
    }
}
