package com.itibo.vkcheck.Activity.models.Profile;

/**
 * Created by erick on 27.10.15.
 */
public class ChangesModel {
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFriend_uid() {
        return friend_uid;
    }

    public void setFriend_uid(int friend_uid) {
        this.friend_uid = friend_uid;
    }

    public boolean is_added() {
        return is_added;
    }

    public void setIs_added(boolean is_added) {
        this.is_added = is_added;
    }

    public int user_id;

    public int friend_uid;

    public boolean is_added;
}
