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

import com.linzon.ru.fragments.UserF;
import com.linzon.ru.fragments.CategoryOffersF;
import com.linzon.ru.fragments.ContactsF;
import com.linzon.ru.fragments.PostSendF;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView navHeaderUsername;
    public FloatingActionButton fab;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    private final static String CATEGORY_TAG = "CATEGORY_FRAGMENT";
    private final static String POPULAR_TAG = "POPULAR_FRAGMENT";
    private final static String OFFER_TAG = "OFFER_FRAGMENT";
    private final static String USER_TAG = "ABOUT_FRAGMENT";
    private final static String CONTACTS_TAG = "CONTACTS_FRAGMENT";
    private final static String POST_TAG = "POST_FRAGMENT";

    private NavigationView navigationView;

    //private PopularF popularFragment;
    private CategoryOffersF categoryOffersFragment;
    private UserF userFFragment;
    private PostSendF postSendFragment;
    private ContactsF contactsFragment;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(null == categoryOffersFragment) {
            showCategory(0);
            toolbar.setTitle(this.getResources().getString(R.string.drawer_popular));
        }
    }

    private void setProgressRing() {
        progressBarMain = (ProgressBar) findViewById(R.id.progressBarMain);
    }

    /*private void showPopular() {
        if (!(selectedFragment instanceof PopularF)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if (null == popularFragment) {
                popularFragment = new PopularF();
                fragmentTransaction.add(R.id.mainPager, popularFragment, POPULAR_TAG);
            }
            fragmentTransaction.show(popularFragment);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
            selectedFragment = popularFragment;
        }
        if(categoryOffersFragment.selectedCategory != 0)
            categoryOffersFragment.setCategory(0);
    }*/

    private void showCategory(int id) {
        if (!(selectedFragment instanceof CategoryOffersF)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if (null == categoryOffersFragment) {
                categoryOffersFragment = new CategoryOffersF();
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

    private void showUser() {
        if (!(selectedFragment instanceof UserF)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if (null == userFFragment) {
                userFFragment = new UserF();
                fragmentTransaction.add(R.id.mainPager, userFFragment, USER_TAG);
            }
            fragmentTransaction.show(userFFragment);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
            selectedFragment = userFFragment;
        }
    }

    private void showPostSend() {
        if (!(selectedFragment instanceof PostSendF)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if (null == postSendFragment) {
                postSendFragment = new PostSendF();
                fragmentTransaction.add(R.id.mainPager, postSendFragment, POST_TAG);
            }
            fragmentTransaction.show(postSendFragment);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
            selectedFragment = postSendFragment;
        }
    }

    private void showContacts() {
        if (!(selectedFragment instanceof ContactsF)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if (null == contactsFragment) {
                contactsFragment = new ContactsF();
                fragmentTransaction.add(R.id.mainPager, contactsFragment, CONTACTS_TAG);
            }
            fragmentTransaction.show(contactsFragment);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
            selectedFragment = contactsFragment;
        }
    }

    private void hideFragments() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        /*popularFragment = (PopularF) getFragmentManager().findFragmentByTag(POPULAR_TAG);
        if (null != popularFragment) {
            fragmentTransaction.hide(popularFragment);
        }*/

        categoryOffersFragment = (CategoryOffersF) getFragmentManager().findFragmentByTag(CATEGORY_TAG);
        if (null != categoryOffersFragment) {
            fragmentTransaction.hide(categoryOffersFragment);
        }

        userFFragment = (UserF) getFragmentManager().findFragmentByTag(USER_TAG);
        if (null != userFFragment) {
            fragmentTransaction.hide(userFFragment);
        }

        postSendFragment = (PostSendF) getFragmentManager().findFragmentByTag(POST_TAG);
        if (null != postSendFragment) {
            fragmentTransaction.hide(postSendFragment);
        }

        contactsFragment = (ContactsF) getFragmentManager().findFragmentByTag(CONTACTS_TAG);
        if (null != contactsFragment) {
            fragmentTransaction.hide(contactsFragment);
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
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Basket.class);
                startActivity(intent);
            }
        });
    }

    private void initTextViews() {
        /*navHeaderUsername = (TextView)((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.navHeaderUsername);
        navHeaderUsername.setText("Привет, ");*/
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
        switch (item.getItemId()) {
            case R.id.action_post: {
                showPostSend();
                toolbar.setTitle(MainActivity.this.getResources().getString(R.string.toolbarPost));
                break;
            }
            case R.id.action_user: {
                showUser();
                toolbar.setTitle(MainActivity.this.getResources().getString(R.string.toolbarUser));
                break;
            }
            case R.id.action_contacts: {
                showContacts();
                toolbar.setTitle(MainActivity.this.getResources().getString(R.string.toolbarContacts));
                break;
            }
            default: {

            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        toolbar.setTitle(item.getTitle());
        uncheckItems();
        item.setChecked(true);
        invalidateOptionsMenu();
        showFab();
        switch (item.getItemId()) {
            case R.id.nav_popular: {
                showCategory(0);
                break;
            }
            case R.id.nav_1: {
                showCategory(1);
                break;
            }
            case R.id.nav_14: {
                showCategory(14);
                break;
            }
            case R.id.nav_2: {
                showCategory(2);
                break;
            }
            case R.id.nav_15: {
                showCategory(15);
                break;
            }
            case R.id.nav_7: {
                showCategory(7);
                break;
            }
            case R.id.nav_16: {
                showCategory(16);
                break;
            }
            case R.id.nav_3: {
                showCategory(3);
                break;
            }
            case R.id.nav_13: {
                showCategory(13);
                break;
            }
            case R.id.nav_9: {
                showCategory(9);
                break;
            }
            case R.id.nav_6: {
                showCategory(6);
                break;
            }
            case R.id.nav_10: {
                showCategory(10);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
                || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            showCategory(0);
        }
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
