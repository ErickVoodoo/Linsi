package com.itibo.vkcheck.Activity.models.PostModel;

import com.itibo.vkcheck.Activity.adapter.DocsAdapter;
import com.itibo.vkcheck.Activity.models.PostModel.Attachments.AttachmentsModel;
import com.itibo.vkcheck.Activity.models.PostModel.Attachments.AudioModel;
import com.itibo.vkcheck.Activity.models.PostModel.Attachments.DocumentModel;
import com.itibo.vkcheck.Activity.models.PostModel.Attachments.PhotoModel;
import com.itibo.vkcheck.Activity.models.PostModel.Attachments.VideoModel;
import com.itibo.vkcheck.Activity.models.SearchModel;

import java.util.ArrayList;

/**
 * Created by erick on 19.10.15.
 */
public class PostModel {
    private String text;

    private String copy_post_type;

    public SearchModel getSearchModel() {
        return searchModel;
    }

    public void setSearchModel(SearchModel searchModel) {
        this.searchModel = searchModel;
    }

    private SearchModel searchModel;

    private String date;

    private String copy_owner_id;

    private int id;

    private String copy_post_date;

    private String copy_post_id;

    private String to_id;

    private RepostsModel reposts;

    private String from_id;

    private LikesModel likes;

    private String post_type;

    private CommentsModel comments;

    public ArrayList<AttachmentsModel> getAttachments() {
        return attachments;
    }

    public ArrayList<PhotoModel> getPhotosFromAttachments(ArrayList<AttachmentsModel> attachmentsModels) {
        ArrayList<PhotoModel> photoModels = new ArrayList<>();
        for (int i = 0; i < attachmentsModels.size(); i++) {
            if(attachmentsModels.get(i).getType().equals("photo")) {
                photoModels.add(attachmentsModels.get(i).getPhoto());
            }
        }
        return photoModels;
    }

    public ArrayList<VideoModel> getVideosFromAttachments(ArrayList<AttachmentsModel> attachmentsModels) {
        ArrayList<VideoModel> photoModels = new ArrayList<>();
        for (int i = 0; i < attachmentsModels.size(); i++) {
            if(attachmentsModels.get(i).getType().equals("video")) {
                photoModels.add(attachmentsModels.get(i).getVideo());
            }
        }
        return photoModels;
    }

    public ArrayList<AudioModel> getAudiosFromAttachments(ArrayList<AttachmentsModel> attachmentsModels) {
        ArrayList<AudioModel> photoModels = new ArrayList<>();
        for (int i = 0; i < attachmentsModels.size(); i++) {
            if(attachmentsModels.get(i).getType().equals("audio") && !attachmentsModels.get(i).getAudio().getUrl().equals("")) {
                photoModels.add(attachmentsModels.get(i).getAudio());
            }
        }
        return photoModels;
    }

    public ArrayList<DocumentModel> getDocsFromAttachments(ArrayList<AttachmentsModel> attachmentsModels) {
        ArrayList<DocumentModel> docModels = new ArrayList<>();
        for (int i = 0; i < attachmentsModels.size(); i++) {
            if(attachmentsModels.get(i).getType().equals("doc") && !attachmentsModels.get(i).getDoc().getUrl().equals("")) {
                docModels.add(attachmentsModels.get(i).getDoc());
            }
        }
        return docModels;
    }

    public void setAttachments(ArrayList<AttachmentsModel> attachments) {
        this.attachments = attachments;
    }

    private ArrayList<AttachmentsModel> attachments;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCopy_post_type() {
        return copy_post_type;
    }

    public void setCopy_post_type(String copy_post_type) {
        this.copy_post_type = copy_post_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCopy_owner_id() {
        return copy_owner_id;
    }

    public void setCopy_owner_id(String copy_owner_id) {
        this.copy_owner_id = copy_owner_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCopy_post_date() {
        return copy_post_date;
    }

    public void setCopy_post_date(String copy_post_date) {
        this.copy_post_date = copy_post_date;
    }

    public String getCopy_post_id() {
        return copy_post_id;
    }

    public void setCopy_post_id(String copy_post_id) {
        this.copy_post_id = copy_post_id;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

    public RepostsModel getReposts() {
        return reposts;
    }

    public void setReposts(RepostsModel reposts) {
        this.reposts = reposts;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public LikesModel getLikes() {
        return likes;
    }

    public void setLikes(LikesModel likes) {
        this.likes = likes;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public CommentsModel getComments() {
        return comments;
    }

    public void setComments(CommentsModel comments) {
        this.comments = comments;
    }
}
