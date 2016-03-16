package com.itibo.vkcheck.Activity.adapter;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itibo.vkcheck.Activity.common.TimeCommon;
import com.itibo.vkcheck.Activity.models.PostModel.Attachments.AudioModel;
import com.itibo.vkcheck.Activity.service.MusicPlayer;
import com.itibo.vkcheck.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by erick on 20.10.15.
 */
public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder> {
    ArrayList<AudioModel> arrayList;
    private Activity activity;

    @Override
    public AudioAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.attachments_audio, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (!arrayList.get(position).getUrl().equals("")) {
            holder.audioMainContainer.setVisibility(View.VISIBLE);
            //holder.audioMainContainer.setBackgroundColor(this.activity.getResources().getColor(android.R.color.white));

            Picasso.with(this.activity)
                    .load(MusicPlayer.getInstance().getPlayerIcon(arrayList.get(position)))
                    .into(holder.audioPlay);

            holder.artist.setText(arrayList.get(position).getArtist());
            holder.name.setText(arrayList.get(position).getTitle());
            holder.url.setText(arrayList.get(position).getUrl());
            holder.duration.setText(TimeCommon.getHourMinuteFromUnix(Integer.parseInt(arrayList.get(position).getDuration())));

            holder.audioPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*MediaPlayerNotification notification = new MediaPlayerNotification();
                    notification.createNotificationPlayer(activity, arrayList.get(position).getArtist(), arrayList.get(position).getTitle());*/
                    /*TODO ПЛЕЕР СОХРАНЯЮЩИЙ ПЕСНИ, НЕ ВЕРНО ПОЛУЧАЕТ РАЗМЕР ФАЙЛА*/
                    //Vk.GetAudioAsync(arrayList.get(position).getUrl(), arrayList.get(position).getArtist(), arrayList.get(position).getTitle() );
                    MusicPlayer.getInstance().setUrl(arrayList.get(position), arrayList);

                    if (MusicPlayer.getInstance().isPlaying())
                        Picasso.with(AudioAdapter.this.activity)
                                .load(android.R.drawable.ic_media_pause)
                                .into(holder.audioPlay);
                    else
                        Picasso.with(AudioAdapter.this.activity)
                                .load(android.R.drawable.ic_media_play)
                                .into(holder.audioPlay);

                    MusicPlayer.getInstance().setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                            notifyDataSetChanged();
                        }
                    });

                }
            });
        } else {
            //holder.audioMainContainer.setBackgroundColor(this.activity.getResources().getColor(R.color.greenColor));
            holder.audioMainContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView audioPlay;
        public TextView artist;
        public TextView name;
        public TextView url;
        public TextView duration;
        public RelativeLayout audioMainContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            audioPlay = (ImageView) itemView.findViewById(R.id.audioPlay);
            artist = (TextView) itemView.findViewById(R.id.audioArtist);
            name = (TextView) itemView.findViewById(R.id.audioName);
            url = (TextView) itemView.findViewById(R.id.audioUrl);
            duration = (TextView) itemView.findViewById(R.id.audioDuration);
            audioMainContainer = (RelativeLayout) itemView.findViewById(R.id.audioMainContainer);
        }
    }

    public AudioAdapter(ArrayList<AudioModel> arrayList, Activity activity) {
        this.arrayList = arrayList;
        this.activity = activity;
    }
}
