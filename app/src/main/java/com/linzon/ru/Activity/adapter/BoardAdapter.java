package com.linzon.ru.Activity.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.linzon.ru.Activity.common.CircleTransformation;
import com.linzon.ru.Activity.common.Values;
import com.linzon.ru.Activity.models.Profile.ChangesModel;
import com.linzon.ru.Activity.models.SearchModel;
import com.linzon.ru.Activity.UserProfile;
import com.linzon.ru.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by erick on 20.10.15.
 */
public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    ArrayList<SearchModel> usersProfileList;
    ArrayList<ChangesModel> changesList;
    private Activity activity;
    int width;

    @Override
    public BoardAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_user_board_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        ViewGroup.LayoutParams params = holder.image.getLayoutParams();
//        params.width = width;
//        holder.image.setLayoutParams(params);
        if(changesList == null) {
            holder.userIcon.setVisibility(View.GONE);
        } else {
            holder.userIcon.setVisibility(View.VISIBLE);
            holder.userIcon.setImageResource(changesList.get(position).is_added ? android.R.drawable.ic_input_add : android.R.drawable.ic_delete);
        }

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(BoardAdapter.this.activity)
                        .setTitle(usersProfileList.get(position).getFirst_name() + " " + usersProfileList.get(position).getLast_name())
                        .setMessage(null)
                        .setPositiveButton("ПРОФИЛЬ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(BoardAdapter.this.activity, UserProfile.class);
                                intent.putExtra("uid", String.valueOf(usersProfileList.get(position).getUid()));
                                BoardAdapter.this.activity.startActivity(intent);
                                BoardAdapter.this.activity.finish();
                            }
                        })
                        .show();
            }
        });

        Picasso.with(this.activity)
                .load(usersProfileList.get(position).getPhoto_100())
                .transform(new CircleTransformation())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return usersProfileList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public ImageView userIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.userItemPhoto);
            userIcon = (ImageView) itemView.findViewById(R.id.userIcon);
        }
    }

    public BoardAdapter(ArrayList<SearchModel> arrayList, ArrayList<ChangesModel> changesList, Activity activity) {
        this.width = Values.GET_SCREEN_WIDTH(activity.getApplicationContext()) - Values.dpToPx(activity.getApplicationContext(), 8);
        this.usersProfileList = arrayList;
        this.changesList = changesList;
        this.activity = activity;
    }
}
