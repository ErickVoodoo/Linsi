package com.linzon.ru.Activity.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linzon.ru.Activity.adapter.BoardAdapter;
import com.linzon.ru.Activity.api.Vk;
import com.linzon.ru.Activity.database.DBHelper;
import com.linzon.ru.Activity.models.Profile.ChangesModel;
import com.linzon.ru.Activity.models.Profile.Subscribers;
import com.linzon.ru.Activity.models.SearchModel;
import com.linzon.ru.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erick on 26.10.15.
 */
public class User_Board extends Fragment {
    View view;
    Intent intent;
    private ProgressBar progressBar;
    private RecyclerView friendsRecycler;
    private RecyclerView onlineFriendsRecycler;
    private RecyclerView subscribersRecycler;
    private RecyclerView onlineSubscribersRecycler;
    private RecyclerView boardChangesFriends;

    private TextView boardFriendsCount;
    private TextView boardOnlineFriendsCount;
    private TextView boardSubscribersCount;
    private TextView boardOnlineSubscribersCount;
    private TextView boardChangeFriendsCountTextView;
    private TextView boardChangeFriendsTextView;

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_board, container, false);

        setProgressBar();
        setTextViews();
        setRecyclers();

        return view;
    }

    private void setTextViews() {
        boardFriendsCount = (TextView) view.findViewById(R.id.boardFriendsCountTextView);
        boardOnlineFriendsCount = (TextView) view.findViewById(R.id.boardOnlineFriendsCountTextView);
        boardSubscribersCount = (TextView) view.findViewById(R.id.boardSubscribersCountTextView);
        boardOnlineSubscribersCount = (TextView) view.findViewById(R.id.boardOnlineSubscribersCountTextView);
        boardChangeFriendsCountTextView = (TextView) view.findViewById(R.id.boardChangeFriendsCountTextView);
        boardChangeFriendsTextView = (TextView) view.findViewById(R.id.boardChangeFriendsTextView);
    }

    private void setRecyclers() {
        friendsRecycler = (RecyclerView) view.findViewById(R.id.boardFriends);
        onlineFriendsRecycler = (RecyclerView) view.findViewById(R.id.boardOnlineFriends);
        subscribersRecycler = (RecyclerView) view.findViewById(R.id.boardSubscribers);
        onlineSubscribersRecycler = (RecyclerView) view.findViewById(R.id.boardOnlineSubscribers);

        boardChangesFriends = (RecyclerView) view.findViewById(R.id.boardChangesFriends);

        LinearLayoutManager friendLM = new LinearLayoutManager(getActivity());
        friendLM.setOrientation(LinearLayoutManager.HORIZONTAL);

        friendsRecycler.setHasFixedSize(true);
        friendsRecycler.setLayoutManager(friendLM);

        LinearLayoutManager onlineFriendLM = new LinearLayoutManager(getActivity());
        onlineFriendLM.setOrientation(LinearLayoutManager.HORIZONTAL);

        onlineFriendsRecycler.setHasFixedSize(true);
        onlineFriendsRecycler.setLayoutManager(onlineFriendLM);

        LinearLayoutManager subscribersLM = new LinearLayoutManager(getActivity());
        subscribersLM.setOrientation(LinearLayoutManager.HORIZONTAL);

        subscribersRecycler.setHasFixedSize(true);
        subscribersRecycler.setLayoutManager(subscribersLM);

        LinearLayoutManager onlineSubscriberLM = new LinearLayoutManager(getActivity());
        onlineSubscriberLM.setOrientation(LinearLayoutManager.HORIZONTAL);

        onlineSubscribersRecycler.setHasFixedSize(true);
        onlineSubscribersRecycler.setLayoutManager(onlineSubscriberLM);

        LinearLayoutManager changesFriendLM = new LinearLayoutManager(getActivity());
        changesFriendLM.setOrientation(LinearLayoutManager.HORIZONTAL);

        boardChangesFriends.setHasFixedSize(true);
        boardChangesFriends.setLayoutManager(changesFriendLM);

        Vk.asyncGetUserFriends(intent.getStringExtra("uid"), new Vk.CallbackArray() {
            @Override
            public void onSuccess(ArrayList<Integer> list) {
                String result = "";
                for (int i = 0; i < (list.size() > 150 ? 150 : list.size()); i++) {
                    result += list.get(i).toString() + ",";
                }
                boardFriendsCount.setText(String.valueOf(list.size()));
                if (result.length() != 0) {
                    Vk.asyncGetUsersShort(result.substring(0, result.length() - 1), new Vk.CallbackArraySearchModel() {
                        @Override
                        public void onSuccess(ArrayList<SearchModel> model) {
                            BoardAdapter boardFriendsAdapter = new BoardAdapter(model, null, getActivity());
                            friendsRecycler.setAdapter(boardFriendsAdapter);


                            ArrayList<SearchModel> onlineFriends = new ArrayList<SearchModel>();
                            for (int onlineFriendIndex = 0; onlineFriendIndex < model.size(); onlineFriendIndex++) {
                                if (model.get(onlineFriendIndex).getOnline() == 1) {
                                    onlineFriends.add(model.get(onlineFriendIndex));
                                }
                            }

                            BoardAdapter boardOnlineFriendsAdapter = new BoardAdapter(onlineFriends, null, getActivity());
                            onlineFriendsRecycler.setAdapter(boardOnlineFriendsAdapter);
                            boardOnlineFriendsCount.setText(String.valueOf(onlineFriends.size()));

                            if (DBHelper.getInstance().checkIfUserExist(intent.getStringExtra("uid"))) {
                                getFriendChanges(model, DBHelper.getUserFriendsFromDatabase(intent.getStringExtra("uid")));
                            }
                            //BoardAdapter boardChangesFriendsAdapter = new BoardAdapter(changesModel, getActivity());

                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                }
            }
        });

        Vk.asyncGetSubscribers(intent.getStringExtra("uid"), new Vk.CallbackSubscribers() {
            @Override
            public void onSuccess(Subscribers model) {
                String result = "";
                for (int i = 0; i < (model.getItems().length > 150 ? 150 : model.getItems().length); i++) {
                    result += model.getItems()[i] + ",";
                }
                boardSubscribersCount.setText(model.getCount());
                if (result.length() != 0) {
                    Vk.asyncGetUsersShort(result.substring(0, result.length() - 1), new Vk.CallbackArraySearchModel() {
                        @Override
                        public void onSuccess(ArrayList<SearchModel> model) {
                            BoardAdapter boardFriendsAdapter = new BoardAdapter(model, null, getActivity());
                            subscribersRecycler.setAdapter(boardFriendsAdapter);

                            ArrayList<SearchModel> onlineSubscribers = new ArrayList<SearchModel>();
                            for (int onlineFriendIndex = 0; onlineFriendIndex < model.size(); onlineFriendIndex++) {
                                if (model.get(onlineFriendIndex).getOnline() == 1) {
                                    onlineSubscribers.add(model.get(onlineFriendIndex));
                                }
                            }

                            BoardAdapter boardOnlineSubscribersAdapter = new BoardAdapter(onlineSubscribers, null, getActivity());
                            onlineSubscribersRecycler.setAdapter(boardOnlineSubscribersAdapter);
                            boardOnlineSubscribersCount.setText(String.valueOf(onlineSubscribers.size()));
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
    }

    private void getFriendChanges(ArrayList<SearchModel> modelFromVk, ArrayList<ChangesModel> modelFromDB) {
        final BoardAdapter boardAdapter = null;
        String result = "";
        ArrayList<Integer> vkArray = new ArrayList<>();
        ArrayList<Integer> dbArray = new ArrayList<>();
        final ArrayList<ChangesModel> changesModels = new ArrayList<>();
        for (int i = 0; i < modelFromVk.size(); i++) {
            vkArray.add(modelFromVk.get(i).getUid());
        }
        if(modelFromDB == null) {
            return;
        }
        for (int i = 0; i < modelFromDB.size(); i++) {
            dbArray.add(modelFromDB.get(i).getFriend_uid());
        }

        List<Integer> added = new ArrayList<>(vkArray);
        added.removeAll(dbArray);

        List<Integer> removed = new ArrayList<>(dbArray);
        removed.removeAll(vkArray);

        if (added.size() != 0)
            for (int i = 0; i < added.size(); i++) {
                result += added.get(i) + ",";
                ChangesModel model = new ChangesModel();
                model.setUser_id(Integer.parseInt(intent.getStringExtra("uid")));
                model.setFriend_uid(added.get(i));
                model.setIs_added(true);
                changesModels.add(model);
            }

        if (removed.size() != 0)
            for (int i = 0; i < removed.size(); i++) {
                result += removed.get(i) + ",";
                ChangesModel model = new ChangesModel();
                model.setUser_id(Integer.parseInt(intent.getStringExtra("uid")));
                model.setFriend_uid(removed.get(i));
                model.setIs_added(false);
                changesModels.add(model);
            }
        if (result.length() != 0) {
            Vk.asyncGetUsersShort(result.substring(0, result.length() - 1), new Vk.CallbackArraySearchModel() {
                @Override
                public void onSuccess(ArrayList<SearchModel> model) {
                    BoardAdapter boardAdapter = new BoardAdapter(model, changesModels, getActivity());
                    if(boardAdapter.getItemCount() != 0) {
                        boardChangeFriendsCountTextView.setVisibility(View.VISIBLE);
                        boardChangesFriends.setVisibility(View.VISIBLE);
                        boardChangeFriendsTextView.setVisibility(View.VISIBLE);
                    }
                    boardChangeFriendsCountTextView.setText(String.valueOf(boardAdapter.getItemCount()));
                    boardChangesFriends.setAdapter(boardAdapter);
                }

                @Override
                public void onError(String error) {

                }
            });
        }
    }

    private void setProgressBar() {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }
}
