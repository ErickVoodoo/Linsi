package com.itibo.vkcheck.Activity.models.PostModel.Attachments;

import com.itibo.vkcheck.Activity.adapter.DocsAdapter;

/**
 * Created by erick on 20.10.15.
 */
public class AttachmentsModel {

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PhotoModel getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoModel photo) {
        this.photo = photo;
    }

    public AudioModel getAudio() {
        return audio;
    }

    public void setAudio(AudioModel audio) {
        this.audio = audio;
    }

    public String type;

    public PhotoModel photo;

    public AudioModel audio;

    public VideoModel getVideo() {
        return video;
    }

    public void setVideo(VideoModel video) {
        this.video = video;
    }

    public VideoModel video;

    public DocumentModel getDoc() {
        return doc;
    }

    public void setDoc(DocumentModel doc) {
        this.doc = doc;
    }

    public DocumentModel doc;
}
