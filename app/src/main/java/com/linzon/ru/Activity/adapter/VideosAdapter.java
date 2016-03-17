package com.linzon.ru.Activity.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linzon.ru.Activity.common.Values;
import com.linzon.ru.Activity.models.PostModel.Attachments.VideoModel;
import com.linzon.ru.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by erick on 20.10.15.
 */
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {
    ArrayList<VideoModel> arrayList;
    private Activity activity;
    int width;

    @Override
    public VideosAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.attachments_video, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.image.getLayoutParams();
        params.width = width;
        holder.image.setLayoutParams(params);
        ViewGroup.LayoutParams description = holder.description.getLayoutParams();
        description.width = width;

        holder.description.setText(arrayList.get(position).getTitle());
        holder.description.setLayoutParams(description);

        Picasso.with(this.activity)
                .load(android.R.drawable.ic_media_play)
                .into(holder.play);

        Picasso.with(this.activity)
                .load(arrayList.get(position).getImage())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public ImageView play;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.attachmentVideo);
            play = (ImageView) itemView.findViewById(R.id.attachmentVideoPlay);
            description = (TextView) itemView.findViewById(R.id.attachmentVideoTitle);
        }
    }

    public VideosAdapter(ArrayList<VideoModel> arrayList, Activity activity) {
        this.width =  Values.GET_SCREEN_WIDTH(activity.getApplicationContext()) - Values.dpToPx(activity.getApplicationContext(), 12);
        this.arrayList = arrayList;
        this.activity = activity;
    }
}
