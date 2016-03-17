package com.linzon.ru.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linzon.ru.adapters.PostAdapter;
import com.linzon.ru.R;

import java.util.ArrayList;

/**
 * Created by erick on 26.10.15.
 */
public class User_Posts extends Fragment {
    View view;

    /*private Toolbar toolbar;
    private RecyclerView recyclerView;

    private Intent intent;

    private TextView wallError;

    PostAdapter postAdapter;
    ProgressBar progressBar;

    public String music_url = "";
    public boolean is_playing = false;

    public void setIntent(Intent intent) {
        this.intent = intent;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_posts, container, false);

        /*setProgressBar();
        setTextViews();
        setRecycler();*/

        return view;
    }


   /* private void setProgressBar() {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

    private void setRecycler() {
        recyclerView = (RecyclerView) view.findViewById(R.id.wallRecycler);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        loadPosts();
    }

    private void setTextViews() {
        wallError = (TextView) view.findViewById(R.id.wallError);
    }

    private void loadPosts() {
        Vk.asyncUserPosts(intent.getStringExtra("uid"), 0, 10, new Vk.CallbackArrayPostModel() {
            @Override
            public void onSuccess(ArrayList<PostModel> model) {
                if (model.size() != 0) {
                    User_Posts.this.postAdapter = new PostAdapter(model, getActivity());
                    wallError.setVisibility(View.GONE);
                    recyclerView.setAdapter(postAdapter);
                } else {
                    wallError.setVisibility(View.VISIBLE);
                    wallError.setText(getResources().getString(R.string.errorWallClosed));
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(String error) {

            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
                int firstVisiblePosition = layoutManager.findLastVisibleItemPosition();

                if (postAdapter.getItemCount() - 5 < firstVisiblePosition) {
                    if (!loading) {
                        loading = true;
                        Vk.asyncUserPosts(intent.getStringExtra("uid"), offset_count, 1, new Vk.CallbackArrayPostModel() {
                            @Override
                            public void onSuccess(ArrayList<PostModel> model) {
                                if (model != null) {
                                    offset_count += model.size();
                                    User_Posts.this.postAdapter.updatePosts(model);
                                    loading = false;
                                } else {
                                    wallError.setVisibility(View.VISIBLE);
                                    wallError.setText(getResources().getString(R.string.errorWallClosed));
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                    }
                }
            }
        });
    }

    boolean loading = false;
    int offset_count = 10;*/
}
