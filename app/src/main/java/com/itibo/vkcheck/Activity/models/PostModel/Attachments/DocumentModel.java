package com.itibo.vkcheck.Activity.models.PostModel.Attachments;

import java.io.Serializable;

/**
 * Created by erick on 2.11.15.
 */
public class DocumentModel implements Serializable {
    private String title;

    private String thumb_s;

    private String owner_id;

    private String did;

    private String access_key;

    private String thumb;

    private String url;

    private String ext;

    private String size;

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getThumb_s ()
    {
        return thumb_s;
    }

    public void setThumb_s (String thumb_s)
    {
        this.thumb_s = thumb_s;
    }

    public String getOwner_id ()
    {
        return owner_id;
    }

    public void setOwner_id (String owner_id)
    {
        this.owner_id = owner_id;
    }

    public String getDid ()
    {
        return did;
    }

    public void setDid (String did)
    {
        this.did = did;
    }

    public String getAccess_key ()
    {
        return access_key;
    }

    public void setAccess_key (String access_key)
    {
        this.access_key = access_key;
    }

    public String getThumb ()
    {
        return thumb;
    }

    public void setThumb (String thumb)
    {
        this.thumb = thumb;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getExt ()
    {
        return ext;
    }

    public void setExt (String ext)
    {
        this.ext = ext;
    }

    public String getSize ()
    {
        return size;
    }

    public void setSize (String size)
    {
        this.size = size;
    }
}
