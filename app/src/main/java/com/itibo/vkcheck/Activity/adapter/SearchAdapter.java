package com.itibo.vkcheck.Activity.adapter;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itibo.vkcheck.Activity.common.TimeCommon;
import com.itibo.vkcheck.Activity.common.Values;
import com.itibo.vkcheck.Activity.models.SearchModel;
import com.itibo.vkcheck.Activity.UserProfile;
import com.itibo.vkcheck.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    ArrayList<SearchModel> arrayList;
    private Activity activity;
    private int layoutId;

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(this.activity)
                .load(arrayList.get(position).getPhoto_100() == null ? (arrayList.get(position).getPhoto_200() == null ? arrayList.get(position).getPhoto_200_orig() : arrayList.get(position).getPhoto_200()): arrayList.get(position).getPhoto_100())
                .into(holder.image);
        holder.username.setText(arrayList.get(position).getFirst_name() + " " + arrayList.get(position).getLast_name());
        holder.uid.setText("vk.com/id" + arrayList.get(position).getUid());
        if(arrayList.get(position).getLast_seen().getTime() > 0 && arrayList.get(position).getOnline() == 0) {
            holder.lastseen.setText(TimeCommon.getTimeFromUnix(arrayList.get(position).getLast_seen().getTime()));
        } else if(arrayList.get(position).getOnline() == 1){
            holder.lastseen.setText("Онлайн");
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView username;
        public TextView uid;
        public TextView lastseen;

        public ViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.userNameText);
            image = (ImageView) itemView.findViewById(R.id.itemPhoto);
            uid = (TextView) itemView.findViewById(R.id.uidText);
            lastseen = (TextView) itemView.findViewById(R.id.lastseenText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchAdapter.this.activity, UserProfile.class);
                    intent.putExtra("uid", uid.getText().toString().substring(9));
                    SearchAdapter.this.activity.startActivity(intent);
                }
            });
            if(R.layout.fragment_user_item == layoutId){
                ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                layoutParams.height = Values.GET_SCREEN_WIDTH(activity.getApplicationContext())/2;
                itemView.setLayoutParams(layoutParams);
            }
        }
    }

    public SearchAdapter(ArrayList<SearchModel> arrayList, Activity activity, int layoutId){
        this.arrayList = arrayList;
        this.activity = activity;
        this.layoutId = layoutId;
    }
}