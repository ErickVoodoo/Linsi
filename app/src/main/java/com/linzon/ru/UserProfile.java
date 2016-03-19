package com.linzon.ru;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by erick on 15.10.15.
 */
public class UserProfile extends AppCompatActivity {
    /*private Toolbar toolbar;

    private Intent intent;
    private MainOffer DBuser;
    private CollapsingToolbarLayout toolbarLayout;
    public String music_url = "";
    public boolean is_playing = false;
    private SearchModel user;

    private Fragment selectedFragment;
    private User_Posts postsFragment;
    private User_Board boardFragment;

    private static final String POSTS_TAG = "user_posts";
    private static final String BOARD_TAG = "user_board";

    private FloatingActionButton fab;
    private TopCropImageView mainView;

    private RelativeLayout imageViewContainer;
    private TouchImageView imageViewer;
    public String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);

        intent = this.getIntent();
        Vk.asyncGetUser(intent.getStringExtra("uid"), new Vk.CallbackSearchModel() {
            @Override
            public void onSuccess(SearchModel model) {
                user = model;
                updateValues(model);
                setFab();
            }

            @Override
            public void onError(String error) {

            }
        });

        DBuser = DBHelper.getUserFromDatabase(intent.getStringExtra("uid"));

        setToolbar();
        setCollapsingToolbar();
        setImageViewer();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Values.USER_GET_URL);
        filter.addAction(Values.USER_GET_ISPLAYING);
        LocalBroadcastManager.getInstance(this).registerReceiver(appServiceReciever, filter);
        showPosts();
    }

    private void setImageViewer() {
        imageViewContainer = (RelativeLayout) findViewById(R.id.imageViewContainer);
        imageViewer = (TouchImageView) findViewById(R.id.imageViewer);
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.header_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
    }

    private void setCollapsingToolbar() {
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    }

    private void setImageView() {
        mainView = (TopCropImageView) findViewById(R.id.mainView);
    }

    public int updateValues(SearchModel model) {
        if (model.getPhoto_400_orig() != null || model.getPhoto_200_orig() != null) {
            if (null == mainView) {
                setImageView();
            }
            Picasso.with(this)
                    .load(model.getPhoto_400_orig() == null ? model.getPhoto_200_orig() : model.getPhoto_400_orig())
                    .into(mainView);
        }
        if (model != null) {
            if (null != model.getLast_name() && null != model.getFirst_name()) {
                if (toolbarLayout == null) {
                    setCollapsingToolbar();
                }
                toolbarLayout.setTitle(model.getFirst_name() + " " + model.getLast_name());
            }
        }
        return -1;
    }

    public void setFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if (!DBHelper.getInstance().checkIfUserExist(String.valueOf(user.getUid()))) {
                    DBHelper.getInstance().insertRows(DBHelper.USERS, DBHelper.setUserContentValues(String.valueOf(user.getUid()), 0, user.getOnline(), user.getLast_seen().getTime()));
                    fab.animate().alpha(0).setDuration(200L).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            Hostinger.asyncGetUser(String.valueOf(user.getUid()), new Hostinger.CallbackResponse() {
                                @Override
                                public void onSuccess(HostingerModel model) {
                                    if (model.getCode().equals("404")) {
                                        Vk.asyncGetUserFriends(intent.getStringExtra("uid"), new Vk.CallbackArray() {
                                            @Override
                                            public void onSuccess(ArrayList<Integer> list) {
                                                String execute = "INSERT INTO '" + DBHelper.HISTORY_FRIENDS +"'('user_id', 'friend_uid') VALUES ";
                                                for (int i = 0; i < (list.size() > 200 ? 200 : list.size()); i++) {
                                                    if(list.get(i) != null && user != null)
                                                        execute += "('" + user.getUid() + "','" + list.get(i) +"'),";
                                                    //DBHelper.getInstance().insertRows(DBHelper.HISTORY_FRIENDS, DBHelper.setBoardContentValues(user.getUid(), list.get(i)));
                                                }
                                                Log.e(String.valueOf(list.size()), execute);
                                                DBHelper.getInstance().getWritableDatabase().execSQL(execute.substring(0, execute.length() - 1));
                                            }
                                        });

                                        Vk.asyncGetSubscribers(intent.getStringExtra("uid"), new Vk.CallbackSubscribers() {
                                            @Override
                                            public void onSuccess(Subscribers model) {
                                                String execute = "INSERT INTO '" + DBHelper.HISTORY_SUBSCRIBERS +"'('user_id', 'friend_uid') VALUES ";
                                                for (int i = 0; i < (model.getItems().length > 200 ? 200 : model.getItems().length); i++) {
                                                    if(model.getItems()[i] != null && user != null)
                                                        execute += "('" + user.getUid() + "','" + model.getItems()[i] +"'),";
                                                    //DBHelper.getInstance().insertRows(DBHelper.HISTORY_SUBSCRIBERS, DBHelper.setBoardContentValues(user.getUid(), Integer.parseInt(model.getItems()[i])));
                                                }
                                                Log.e(String.valueOf(model.getItems().length), execute);
                                                DBHelper.getInstance().getWritableDatabase().execSQL(execute.substring(0, execute.length() - 1));
                                            }

                                            @Override
                                            public void onError(String error) {

                                            }
                                        });
                                        Hostinger.asyncAddUser(String.valueOf(user.getUid()), new Hostinger.CallbackResponse() {
                                            @Override
                                            public void onSuccess(HostingerModel model) {

                                            }

                                            @Override
                                            public void onError(String error) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onError(String error) {

                                }
                            });
                            fab.setBackgroundTintList(getResources().getColorStateList(R.color.redColor));
                            fab.setImageResource(R.drawable.ic_clear_white_24dp);
                            fab.animate().alpha(1).setDuration(200L).start();
                        }
                    }).start();
                } else {
                    DBHelper.getInstance().deleteRows(DBHelper.USERS, "uid='" + String.valueOf(user.getUid()) + "'");
                    fab.animate().alpha(0).setDuration(200L).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            Hostinger.asyncGetUser(String.valueOf(user.getUid()), new Hostinger.CallbackResponse() {
                                @Override
                                public void onSuccess(HostingerModel model) {
                                    if (model != null)
                                        if (model.getCode().equals("201")) {
                                            Hostinger.asyncRemoveUser(String.valueOf(user.getUid()), new Hostinger.CallbackResponse() {
                                                @Override
                                                public void onSuccess(HostingerModel model) {
                                                    DBHelper.getInstance().deleteRows(DBHelper.HISTORY_FRIENDS, "user_id='" + user.getUid() + "'");
                                                    DBHelper.getInstance().deleteRows(DBHelper.HISTORY_SUBSCRIBERS, "user_id='" + user.getUid() + "'");
                                                }

                                                @Override
                                                public void onError(String error) {

                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onError(String error) {

                                }
                            });
                            fab.setBackgroundTintList(getResources().getColorStateList(R.color.greenColor));
                            fab.setImageResource(R.drawable.ic_add_white_24dp);
                            fab.animate().alpha(1).setDuration(200L).start();
                        }
                    }).start();
                }
            }
        });
        if (DBHelper.getInstance().checkIfUserExist(String.valueOf(user.getUid()))) {
            fab.setBackgroundTintList(getResources().getColorStateList(R.color.redColor));
            fab.setImageResource(R.drawable.ic_clear_white_24dp);
        } else {
            fab.setBackgroundTintList(getResources().getColorStateList(R.color.greenColor));
            fab.setImageResource(R.drawable.ic_add_white_24dp);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.historyButton) {
            Intent intent = new Intent(this, History.class);
            intent.putExtra("user", (Serializable) user);
            startActivity(intent);
        }

        if (id == R.id.notificationsButton) {
            if (DBHelper.getUserFromDatabase(intent.getStringExtra("uid")).getNotification() == 0) {
                DBHelper.getInstance().updateRows(DBHelper.USERS, DBHelper.setUserContentValues(String.valueOf(user.getUid()), 1, -1, -1), "uid='" + DBuser.getUid() + "'");
                item.setTitle(getResources().getString(R.string.notificationsTurnOff));
                Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.notificationsTurnedOn), Snackbar.LENGTH_LONG).show();
            } else {
                DBHelper.getInstance().updateRows(DBHelper.USERS, DBHelper.setUserContentValues(String.valueOf(user.getUid()), 0, -1, -1), "uid='" + DBuser.getUid() + "'");
                item.setTitle(getResources().getString(R.string.notificationsTurnOn));
                Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.notificationsTurnedOff), Snackbar.LENGTH_LONG).show();
            }
            return true;
        }

        if (id == R.id.userBoard) {
            showBoard();
            invalidateOptionsMenu();
        }

        if (id == R.id.userPosts) {
            showPosts();
            invalidateOptionsMenu();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        if (DBHelper.getInstance().checkIfUserExist(intent.getStringExtra("uid"))) {
            MenuItem item = menu.findItem(R.id.notificationsButton);

            if (DBHelper.getUserFromDatabase(intent.getStringExtra("uid")).getNotification() == 0) {
                item.setTitle(getResources().getString(R.string.notificationsTurnOn));
            } else {
                item.setTitle(getResources().getString(R.string.notificationsTurnOff));
            }
        } else {
            MenuItem item = menu.findItem(R.id.notificationsButton);
            item.setVisible(false);
            MenuItem item2 = menu.findItem(R.id.historyButton);
            item2.setVisible(false);
        }

        if (selectedFragment == null || selectedFragment instanceof User_Posts) {
            MenuItem board = menu.findItem(R.id.userBoard);
            board.setVisible(true);

            MenuItem posts = menu.findItem(R.id.userPosts);
            posts.setVisible(false);
        } else if (selectedFragment instanceof User_Board) {

            MenuItem board = menu.findItem(R.id.userBoard);
            board.setVisible(false);

            MenuItem posts = menu.findItem(R.id.userPosts);
            posts.setVisible(true);
        }
        return true;
    }

    private void showPosts() {
        if (!(selectedFragment instanceof User_Posts)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if (null == postsFragment) {
                postsFragment = new User_Posts();
                postsFragment.setIntent(intent);
                fragmentTransaction.add(R.id.mainPagerUser, postsFragment, POSTS_TAG);
            }
            fragmentTransaction.show(postsFragment);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
            selectedFragment = postsFragment;
        }
    }

    private void showBoard() {
        if (!(selectedFragment instanceof User_Board)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if (null == boardFragment) {
                boardFragment = new User_Board();
                boardFragment.setIntent(intent);
                fragmentTransaction.add(R.id.mainPagerUser, boardFragment, BOARD_TAG);
            }
            fragmentTransaction.show(boardFragment);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
            selectedFragment = boardFragment;
        }
    }

    private void hideFragments() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        postsFragment = (User_Posts) getFragmentManager().findFragmentByTag(POSTS_TAG);
        if (null != postsFragment) {
            fragmentTransaction.hide(postsFragment);
        }

        boardFragment = (User_Board) getFragmentManager().findFragmentByTag(BOARD_TAG);
        if (null != boardFragment) {
            fragmentTransaction.hide(boardFragment);
        }

        fragmentTransaction.commit();
        getFragmentManager().executePendingTransactions();
    }

    private BroadcastReceiver appServiceReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Values.USER_GET_URL: {
                    music_url = intent.getStringExtra("url");
                    break;
                }
                case Values.USER_GET_ISPLAYING: {
                    is_playing = intent.getBooleanExtra("isplaying", false);
                    break;
                }
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        PicassoTools.clearCache(Picasso.with(this));
    }

    public void showImageViewer(String url) {
        Picasso.with(this)
                .load(url)
                .into(imageViewer);
        this.url = url;
        imageViewContainer.setVisibility(View.VISIBLE);
        imageViewContainer.animate().alpha(1).setDuration(250).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageViewContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    public void hideImageViewer() {
        url = "";
        imageViewContainer.animate().alpha(0).setDuration(250).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageViewContainer.setVisibility(View.GONE);
                imageViewer.resetScale();
                Picasso.with(UserProfile.this)
                        .load(R.drawable.default_avatar)
                        .into(imageViewer);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    @Override
    public void onBackPressed()
    {
        if(!url.equals("")) {
            hideImageViewer();
        } else  {
            super.onBackPressed();
        }
    }*/
}