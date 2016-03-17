package com.linzon.ru.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linzon.ru.common.Values;
import com.linzon.ru.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by erick on 20.10.15.
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {
    /*ArrayList<PhotoModel> arrayList;
    private Activity activity;
    int width;
    String last_url = "";*/
    @Override
    public PhotosAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.attachments_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        /*ViewGroup.LayoutParams params = holder.image.getLayoutParams();
        params.width = width;
        holder.image.setLayoutParams(params);

        Picasso.with(this.activity)
                .load(arrayList.get(position).getSrc_big())
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UserProfile) PhotosAdapter.this.activity).showImageViewer(
                        arrayList.get(position).getSrc_xxbig() == null ? (
                                arrayList.get(position).getSrc_xbig() == null ? arrayList.get(position).getSrc_big() : arrayList.get(position).getSrc_xbig()
                                ) : arrayList.get(position).getSrc_xxbig()
                );
            }
        });

        if(getItemCount() != 1) {
            holder.countPhoto.setVisibility(View.VISIBLE);
            last_url = arrayList.get(position).getSrc_big();
            holder.countPhoto.setText((position + 1) + "/" + getItemCount());
        } else  {
            holder.countPhoto.setVisibility(View.GONE);
        }*/
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView countPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.attachmentPhoto);
            countPhoto = (TextView) itemView.findViewById(R.id.countPhoto);
        }
    }

    public PhotosAdapter(Activity activity) {
        /*this.width =  Values.GET_SCREEN_WIDTH(activity.getApplicationContext()) - Values.dpToPx(activity.getApplicationContext(), 8);
        this.arrayList = arrayList;
        this.activity = activity;*/
    }
}
