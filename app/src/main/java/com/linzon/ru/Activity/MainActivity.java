package com.linzon.ru.Activity;

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

import com.linzon.ru.Activity.adapter.SearchAdapter;
import com.linzon.ru.Activity.api.Vk;
import com.linzon.ru.Activity.common.SharedProperty;
import com.linzon.ru.Activity.database.DBHelper;
import com.linzon.ru.Activity.fragments.Search;
import com.linzon.ru.Activity.fragments.SendNotification;
import com.linzon.ru.Activity.fragments.Users;
import com.linzon.ru.Activity.models.SearchModel;
import com.linzon.ru.Activity.service.MusicPlayer;
import com.linzon.ru.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener, View.OnClickListener {

    private TextView navHeaderUsername;
    public FloatingActionButton fab;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    private final static String USER_TAG = "USER_FRAGMENT";
    private final static String SEARCH_TAG = "SEARCH_FRAGMENT";
    private final static String SEND_TAG = "SEND_FRAGMENT";

    private NavigationView navigationView;
    private Search searchFragment;
    private Users usersFragment;
    private SendNotification sendFragment;
    private Fragment selectedFragment = null;

    private ProgressBar progressBarMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper.init(getApplicationContext());

//        ContentValues contentValues = DBHelper.setUserContentValues("111", 0);
//        long i = DBHelper.getInstance().insertRows(contentValues);
//
//        Log.e("INSERTED", String.valueOf(i));
        setProgressRing();
        setPlayerData();
        initToolbar();
        initNavigationView();
        initDrawer();
        initFab();
        initTextViews();
        showUsers();
    }

    private void setProgressRing() {
        progressBarMain = (ProgressBar) findViewById(R.id.progressBarMain);
    }

    private void showUsers() {
        if (!(selectedFragment instanceof Users)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if (null == usersFragment) {
                usersFragment = new Users();
                fragmentTransaction.add(R.id.mainPager, usersFragment, USER_TAG);
            }
            fragmentTransaction.show(usersFragment);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
            selectedFragment = usersFragment;
        }
    }

    private void showSearch() {
        if (!(selectedFragment instanceof Search)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if (null == searchFragment) {
                searchFragment = new Search();
                fragmentTransaction.add(R.id.mainPager, searchFragment, SEARCH_TAG);
            }
            fragmentTransaction.show(searchFragment);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
            selectedFragment = searchFragment;
        }
    }

    private void showSendNotification() {
        if (!(selectedFragment instanceof SendNotification)) {
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
        }
    }

    private void hideFragments() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        usersFragment = (Users) getFragmentManager().findFragmentByTag(USER_TAG);
        if (null != usersFragment) {
            fragmentTransaction.hide(usersFragment);
        }

        searchFragment = (Search) getFragmentManager().findFragmentByTag(SEARCH_TAG);
        if (null != searchFragment) {
            fragmentTransaction.hide(searchFragment);
        }

        sendFragment = (SendNotification) getFragmentManager().findFragmentByTag(SEND_TAG);
        if (null != sendFragment) {
            fragmentTransaction.hide(sendFragment);
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
        navigationView.getMenu().findItem(R.id.nav_users).setChecked(true);
        toolbar.setTitle("Пользователи");
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
                progressBarMain.setVisibility(View.VISIBLE);
                if (selectedFragment instanceof Search) {
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
                if (selectedFragment instanceof Users) {
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
        });
    }

    private void initTextViews() {
        navHeaderUsername = (TextView)  ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.navHeaderUsername);
        String vcal = SharedProperty.getInstance().getCurrentName();
        navHeaderUsername.setText("Привет, " + vcal);
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

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        if (!(selectedFragment instanceof Search)) {
            MenuItem item = menu.findItem(R.id.action_search);
            item.setVisible(false);
        }
        searchView.setOnQueryTextListener(this);

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
            case R.id.nav_users: {
                uncheckItems();
                toolbar.setTitle("Пользователи");
                fab.setImageResource(R.drawable.ic_loop_white_24dp);
                item.setChecked(true);
                showUsers();
                showFab();
                invalidateOptionsMenu();
                break;
            }
            case R.id.nav_search: {
                uncheckItems();
                toolbar.setTitle("Поиск");
                fab.setImageResource(R.drawable.ic_search_white_24dp);
                item.setChecked(true);
                showSearch();
                showFab();
                invalidateOptionsMenu();
                break;
            }
            case R.id.nav_share: {
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

    private int countCheckedItems() {
        Menu items = navigationView.getMenu();
        int count = 0;

        for (int i = 0; i < items.size(); i++) {
            MenuItem item = items.getItem(i);
            if (item.isChecked()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        progressBarMain.setVisibility(View.VISIBLE);
        Vk.asyncSearchUsers(query, new Vk.CallbackArraySearchModel() {
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
        return false;
    }

    String search_status = "";

    @Override
    public boolean onQueryTextChange(String newText) {
        search_status = newText;
        Log.e("newTEXT", newText);
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
                || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            if(selectedFragment instanceof Users) {
                showUsers();
            } else if(selectedFragment instanceof Search) {
                showSearch();
            }
        }
    }

    TextView artist;
    TextView title;
    ImageButton previous;
    ImageButton startpause;
    ImageButton next;
    LinearLayout mainPlayer;

    private void setPlayerData() {
        mainPlayer = (LinearLayout) findViewById(R.id.mainPlayer);

        artist = (TextView) findViewById(R.id.playerArtistTextview);
        title = (TextView) findViewById(R.id.playerTitleTextview);

        previous = (ImageButton) findViewById(R.id.playerPreviousImageButton);
        previous.setOnClickListener(this);

        startpause = (ImageButton) findViewById(R.id.playerPauseStartImageButton);
        startpause.setOnClickListener(this);

        next = (ImageButton) findViewById(R.id.playerNextImageButton);
        next.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(MusicPlayer.getInstance().audioModels != null) {
            if(MusicPlayer.getInstance().selectedAudio != null) {
                artist.setText(MusicPlayer.getInstance().selectedAudio.getArtist());
                title.setText(MusicPlayer.getInstance().selectedAudio.getTitle());
            }
            if(MusicPlayer.getInstance().isPlaying()) {
                startpause.setImageResource(android.R.drawable.ic_media_pause);
            } else {
                startpause.setImageResource(android.R.drawable.ic_media_play);
            }
            mainPlayer.setVisibility(View.VISIBLE);
        } else {
            mainPlayer.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playerPreviousImageButton: {
                MusicPlayer.getInstance().previousSound();
                startpause.setImageResource(android.R.drawable.ic_media_pause);
                break;
            }
            case R.id.playerPauseStartImageButton: {
                if(MusicPlayer.getInstance().selectedAudio != null) {
                    if(MusicPlayer.getInstance().isPlaying()) {
                        MusicPlayer.getInstance().pause();
                        startpause.setImageResource(android.R.drawable.ic_media_play);
                    } else {
                        MusicPlayer.getInstance().start();
                        startpause.setImageResource(android.R.drawable.ic_media_pause);
                    }
                }
                break;
            }
            case R.id.playerNextImageButton: {
                MusicPlayer.getInstance().nextSound();
                startpause.setImageResource(android.R.drawable.ic_media_pause);
                break;
            }
        }
        if(MusicPlayer.getInstance().selectedAudio != null) {
            artist.setText(MusicPlayer.getInstance().selectedAudio.getArtist());
            title.setText(MusicPlayer.getInstance().selectedAudio.getTitle());
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
}
