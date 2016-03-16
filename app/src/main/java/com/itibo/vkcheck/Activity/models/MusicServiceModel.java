package com.itibo.vkcheck.Activity.models;

import com.itibo.vkcheck.Activity.models.PostModel.Attachments.AudioModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by erick on 21.10.15.
 */
public class MusicServiceModel implements Serializable {

    public AudioModel audioModel;

    public ArrayList<AudioModel> audioModelArrayList;

    public AudioModel getAudioModel() {
        return audioModel;
    }

    public void setAudioModel(AudioModel audioModel) {
        this.audioModel = audioModel;
    }

    public ArrayList<AudioModel> getAudioModelArrayList() {
        return audioModelArrayList;
    }

    public void setAudioModelArrayList(ArrayList<AudioModel> audioModelArrayList) {
        this.audioModelArrayList = audioModelArrayList;
    }
}
