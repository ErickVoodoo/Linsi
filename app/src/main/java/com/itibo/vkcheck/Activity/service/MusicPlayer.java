package com.itibo.vkcheck.Activity.service;

import android.media.MediaPlayer;
import android.util.Log;

import com.itibo.vkcheck.Activity.models.PostModel.Attachments.AudioModel;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by erick on 27.10.15.
 */
public class MusicPlayer extends MediaPlayer implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    public AudioModel selectedAudio;
    public ArrayList<AudioModel> audioModels;

    private static MusicPlayer instance;

    public static synchronized MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    public void setUrl(AudioModel model, ArrayList<AudioModel> arrayList) {
        try {
            if (selectedAudio != null && model.getUrl().equals(selectedAudio.getUrl())) {
                if (this.isPlaying())
                    this.pause();
                else
                    this.start();
            } else {
                this.reset();
                this.selectedAudio = model;
                this.audioModels = arrayList;
                this.setDataSource(model.getUrl());
                this.setOnPreparedListener(this);
                this.setOnCompletionListener(this);
                this.prepareAsync();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPlayerIcon(AudioModel model) {
        if (selectedAudio != null) {
            if (!(model.getUrl().equals(selectedAudio.getUrl()))) {
                return android.R.drawable.ic_media_play;
            } else {
                if (isPlaying())
                    return android.R.drawable.ic_media_pause;
                else
                    return android.R.drawable.ic_media_play;
            }
        }
        return android.R.drawable.ic_media_play;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        int index = this.audioModels.indexOf(selectedAudio);
        int next_index = (index + 1 >= this.audioModels.size() ? 0 : index + 1);
        this.setUrl(this.audioModels.get(next_index), this.audioModels);
    }

    public void nextSound() {
        if (this.audioModels.size() != 1) {
            int index = this.audioModels.indexOf(selectedAudio);
            int next_index = (index + 1 >= this.audioModels.size() ? 0 : index + 1);
            this.setUrl(this.audioModels.get(next_index), this.audioModels);
        }
    }

    public void previousSound() {
        if (this.audioModels.size() != 1) {
            if (this.getCurrentPosition() > 10000) {
                this.seekTo(0);
            } else {
                int index = this.audioModels.indexOf(selectedAudio);
                int previous_index = (index - 1 >= 0 ? index - 1 : this.audioModels.size() - 1);
                this.setUrl(this.audioModels.get(previous_index), this.audioModels);
            }
        }
    }
}

