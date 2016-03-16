package com.itibo.vkcheck.Activity.api;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.itibo.vkcheck.Activity.common.Constants;
import com.itibo.vkcheck.Activity.common.HttpRequest;
import com.itibo.vkcheck.Activity.models.PostModel.Attachments.AttachmentsModel;
import com.itibo.vkcheck.Activity.models.PostModel.Attachments.AudioModel;
import com.itibo.vkcheck.Activity.models.PostModel.Attachments.DocumentModel;
import com.itibo.vkcheck.Activity.models.PostModel.Attachments.PhotoModel;
import com.itibo.vkcheck.Activity.models.PostModel.Attachments.VideoModel;
import com.itibo.vkcheck.Activity.models.PostModel.CommentsModel;
import com.itibo.vkcheck.Activity.models.LastSeenModel;
import com.itibo.vkcheck.Activity.models.PostModel.LikesModel;
import com.itibo.vkcheck.Activity.models.PostModel.PostModel;
import com.itibo.vkcheck.Activity.models.PostModel.RepostsModel;
import com.itibo.vkcheck.Activity.models.Profile.Subscribers;
import com.itibo.vkcheck.Activity.models.RequestModel;
import com.itibo.vkcheck.Activity.models.SearchModel;
import com.itibo.vkcheck.Activity.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by erick on 14.10.15.
 */
public class Vk {

    public static abstract class CallbackArraySearchModel {
        public abstract void onSuccess(ArrayList<SearchModel> model);

        public abstract void onError(String error);
    }

    public static abstract class CallbackSearchModel {
        public abstract void onSuccess(SearchModel model);

        public abstract void onError(String error);
    }

    public static abstract class CallbackArrayPostModel {
        public abstract void onSuccess(ArrayList<PostModel> model);

        public abstract void onError(String error);
    }

    public static abstract class CallbackArray {
        public abstract void onSuccess(ArrayList<Integer> list);
    }

    public static abstract class CallbackSubscribers {
        public abstract void onSuccess(Subscribers model);

        public abstract void onError(String error);
    }

    public static void asyncSearchUsers(String query, final CallbackArraySearchModel callback) {
        new AsyncTask<String, Void, ArrayList<SearchModel>>() {
            @Override
            protected ArrayList<SearchModel> doInBackground(String... params) {
                String result = "";
                String responseLine = "";

                ArrayList<SearchModel> Array = new ArrayList<>();

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("api.vk.com")
                        .appendPath("method")
                        .appendPath("users.search")
                        .appendQueryParameter("q", params[0])
                        .appendQueryParameter("fields", "photo_100,last_seen,online")
                        .appendQueryParameter("count", "50")
                        .appendQueryParameter("access_token", Constants.VK_TOKEN);
                String myUrl = builder.build().toString();

                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(myUrl).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Length", "0");
                    connection.setConnectTimeout(10000);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((responseLine = bufferedReader.readLine()) != null) {
                        result += responseLine;
                    }
                    bufferedReader.close();

                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray response = jsonObject.getJSONArray("response");
                    for (int i = 0; i < response.length(); i++) {
                        if (i != 0) {
                            JSONObject currLot = response.getJSONObject(i);
                            SearchModel tempModel = new SearchModel();
                            if (currLot.has("uid"))
                                tempModel.setUid(currLot.getInt("uid"));
                            if (currLot.has("first_name"))
                                tempModel.setFirst_name(currLot.getString("first_name"));
                            if (currLot.has("last_name"))
                                tempModel.setLast_name(currLot.getString("last_name"));
                            if (currLot.has("photo_100"))
                                tempModel.setPhoto_100(currLot.getString("photo_100"));
                            if (currLot.has("online"))
                                tempModel.setOnline(Integer.parseInt(currLot.getString("online")));

                            if (currLot.has("last_seen")) {
                                LastSeenModel tempLastSeen = new LastSeenModel();
                                JSONObject data = new JSONObject(currLot.getString("last_seen"));
                                if (data.has("platform"))
                                    tempLastSeen.setPlatform(Integer.parseInt(data.getString("platform")));
                                if (data.has("time"))
                                    tempLastSeen.setTime(Integer.parseInt(data.getString("time")));
                                tempModel.setLast_seen(tempLastSeen);
                            }
                            Array.add(tempModel);
                        }
                    }
                } catch (JSONException | ProtocolException | MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Array;
            }

            @Override
            protected void onPostExecute(ArrayList<SearchModel> result) {
                callback.onSuccess(result);
            }
        }.execute(query);
    }

    public static void asyncGetUsersFull(String query, final CallbackArraySearchModel callback) {
        new AsyncTask<String, Void, ArrayList<SearchModel>>() {
            @Override
            protected ArrayList<SearchModel> doInBackground(String... params) {
                String resultString = "";
                String responseLine = "";
                ArrayList<SearchModel> returnModel = new ArrayList<SearchModel>();

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("api.vk.com")
                        .appendPath("method")
                        .appendPath("users.get")
                        .appendQueryParameter("user_ids", params[0])
                        .appendQueryParameter("fields", "photo_200,photo_200_orig,last_seen,online");
                String myUrl = builder.build().toString();

                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(myUrl).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Length", "0");
                    connection.setConnectTimeout(10000);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((responseLine = bufferedReader.readLine()) != null) {
                        resultString += responseLine;
                    }
                    bufferedReader.close();

                    JSONObject jsonObject = new JSONObject(resultString);
                    JSONArray response = jsonObject.getJSONArray("response");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject currLot = response.getJSONObject(i);
                        SearchModel tempModel = new SearchModel();
                        tempModel.setUid(currLot.getInt("uid"));
                        if (currLot.has("first_name"))
                            tempModel.setFirst_name(currLot.getString("first_name"));
                        if (currLot.has("last_name"))
                            tempModel.setLast_name(currLot.getString("last_name"));
                        if (currLot.has("photo_200"))
                            tempModel.setPhoto_200(currLot.getString("photo_200"));
                        if (currLot.has("photo_200_orig"))
                            tempModel.setPhoto_200_orig(currLot.getString("photo_200_orig"));
                        if (currLot.has("online"))
                            tempModel.setOnline(Integer.parseInt(currLot.getString("online")));

                        if (currLot.getString("last_seen") != null) {
                            LastSeenModel tempLastSeen = new LastSeenModel();
                            JSONObject data = new JSONObject(currLot.getString("last_seen"));
                            if (data.has("platform"))
                                tempLastSeen.setPlatform(Integer.parseInt(data.getString("platform")));
                            if (data.has("time"))
                                tempLastSeen.setTime(Integer.parseInt(data.getString("time")));
                            tempModel.setLast_seen(tempLastSeen);
                        }

                        returnModel.add(tempModel);
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                return returnModel;
            }

            @Override
            protected void onPostExecute(ArrayList<SearchModel> result) {
                callback.onSuccess(result);
            }
        }.execute(query);
    }

    public static void asyncGetUsersShort(String query, final CallbackArraySearchModel callback) {
        new AsyncTask<String, Void, ArrayList<SearchModel>>() {
            @Override
            protected ArrayList<SearchModel> doInBackground(String... params) {
                if (params[0] != null) {
                    String resultString = "";
                    String responseLine = "";
                    ArrayList<SearchModel> returnModel = new ArrayList<SearchModel>();

                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("https")
                            .authority("api.vk.com")
                            .appendPath("method")
                            .appendPath("users.get")
                            .appendQueryParameter("user_ids", params[0])
                            .appendQueryParameter("fields", "photo_100,online,last_seen");
                    String myUrl = builder.build().toString();

                    try {
                        HttpURLConnection connection = (HttpURLConnection) new URL(myUrl).openConnection();
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Content-Length", "0");
                        connection.setConnectTimeout(10000);
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        while ((responseLine = bufferedReader.readLine()) != null) {
                            resultString += responseLine;
                        }
                        bufferedReader.close();

                        JSONObject jsonObject = new JSONObject(resultString);
                        JSONArray response = jsonObject.getJSONArray("response");
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject currLot = response.getJSONObject(i);
                            SearchModel tempModel = new SearchModel();
                            if (currLot.has("uid"))
                                tempModel.setUid(currLot.getInt("uid"));
                            if (currLot.has("first_name"))
                                tempModel.setFirst_name(currLot.getString("first_name"));
                            if (currLot.has("last_name"))
                                tempModel.setLast_name(currLot.getString("last_name"));
                            if (currLot.has("photo_100"))
                                tempModel.setPhoto_100(currLot.getString("photo_100"));
                            if (currLot.has("online"))
                                tempModel.setOnline(Integer.parseInt(currLot.getString("online")));

                            if (currLot.has("last_seen")) {
                                LastSeenModel tempLastSeen = new LastSeenModel();
                                JSONObject data = new JSONObject(currLot.getString("last_seen"));
                                if (data.has("platform"))
                                    tempLastSeen.setPlatform(Integer.parseInt(data.getString("platform")));
                                if (data.has("time"))
                                    tempLastSeen.setTime(Integer.parseInt(data.getString("time")));
                                tempModel.setLast_seen(tempLastSeen);
                            }
                            returnModel.add(tempModel);
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    return returnModel;
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<SearchModel> result) {
                callback.onSuccess(result);
            }
        }.execute(query);
    }

    public static void asyncGetUserFriends(final String uid, final CallbackArray callbackArray) {
        new AsyncTask<String, Void, ArrayList<Integer>>() {
            @Override
            protected ArrayList<Integer> doInBackground(String... params) {
                String responseLine = "";
                String resultString = "";

                ArrayList<Integer> list = new ArrayList<>();
                Uri.Builder builder = new Uri.Builder()
                        .scheme("https")
                        .authority("api.vk.com")
                        .appendPath("method")
                        .appendPath("friends.get")
                        .appendQueryParameter("uid", params[0]);
                ;

                String url = builder.build().toString();

                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Length", "0");
                    connection.setConnectTimeout(10000);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((responseLine = bufferedReader.readLine()) != null) {
                        resultString += responseLine;
                    }
                    JSONObject jsonObject = new JSONObject(resultString);
                    JSONArray response = jsonObject.getJSONArray("response");
                    for (int i = 0; i < response.length(); i++) {
                        list.add(Integer.parseInt(response.get(i).toString()));
                    }

                    bufferedReader.close();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                return list;
            }

            @Override
            protected void onPostExecute(ArrayList<Integer> list) {
                callbackArray.onSuccess(list);
            }
        }.execute(uid);
    }

    public static void asyncGetSubscribers(final String uid, final CallbackSubscribers callbackSubscribers) {
        new AsyncTask<String, Void, Subscribers>() {
            @Override
            protected Subscribers doInBackground(String... params) {
                Subscribers subscribers = new Subscribers();

                String responseLine = "";
                String resultString = "";

                Uri.Builder builder = new Uri.Builder()
                        .scheme("https")
                        .authority("api.vk.com")
                        .appendPath("method")
                        .appendPath("users.getFollowers")
                        .appendQueryParameter("uid", params[0])
                        .appendQueryParameter("count", "150");

                String url = builder.build().toString();

                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Length", "0");
                    connection.setConnectTimeout(10000);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((responseLine = bufferedReader.readLine()) != null) {
                        resultString += responseLine;
                    }
                    bufferedReader.close();

                    JSONObject jsonObject = new JSONObject(resultString);
                    JSONObject response = jsonObject.getJSONObject("response");

                    if (response.has("count"))
                        subscribers.setCount(String.valueOf(response.getInt("count")));

                    if (response.has("items")) {
                        String[] items = new String[response.getInt("count")];
                        JSONArray array = response.getJSONArray("items");
                        for (int j = 0; j < array.length(); j++) {
                            items[j] = array.get(j).toString();
                        }
                        subscribers.setItems(items);
                    }

                } catch (JSONException | IOException e1) {
                    e1.printStackTrace();
                }
                return subscribers;
            }

            @Override
            protected void onPostExecute(Subscribers list) {
                callbackSubscribers.onSuccess(list);
            }
        }.execute(uid);
    }

    public static void asyncUserPosts(String query, final int offset, final int count, final CallbackArrayPostModel callback) {
        new AsyncTask<String, Void, ArrayList<PostModel>>() {
            @Override
            protected ArrayList<PostModel> doInBackground(String... params) {
                String result = "";
                ArrayList<PostModel> returnModel = new ArrayList<PostModel>();
                String responseLine = "";

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("api.vk.com")
                        .appendPath("method")
                        .appendPath("wall.get")
                        .appendQueryParameter("owner_id", params[0])
                        .appendQueryParameter("offset", String.valueOf(offset))
                        .appendQueryParameter("count", String.valueOf(count));
                String myUrl = builder.build().toString();

                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(myUrl).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Length", "0");
                    connection.setConnectTimeout(10000);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((responseLine = bufferedReader.readLine()) != null) {
                        result += responseLine;
                    }
                    bufferedReader.close();

                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray response = jsonObject.getJSONArray("response");
                    for (int i = 0; i < response.length(); i++) {
                        if (i != 0) {
                            JSONObject currLot = response.getJSONObject(i);
                            PostModel tempModel = new PostModel();
                            if (currLot.has("id"))
                                tempModel.setId(currLot.getInt("id"));
                            if (currLot.has("from_id"))
                                tempModel.setFrom_id(currLot.getString("from_id"));
                            if (currLot.has("to_id"))
                                tempModel.setTo_id(currLot.getString("to_id"));
                            if (currLot.has("date"))
                                tempModel.setDate(currLot.getString("date"));
                            if (currLot.has("post_type"))
                                tempModel.setPost_type(currLot.getString("post_type"));
                            if (currLot.has("text"))
                                tempModel.setText(currLot.getString("text"));

                            /*SHORT INFO*/
                            String resultTemp = "";


                            Uri.Builder builderTemp = new Uri.Builder();
                            builderTemp.scheme("https")
                                    .authority("api.vk.com")
                                    .appendPath("method")
                                    .appendPath("users.get")
                                    .appendQueryParameter("user_ids", currLot.getString("from_id"))
                                    .appendQueryParameter("fields", "photo_100");
                            String myUrlTemp = builderTemp.build().toString();

                            HttpURLConnection connectionTemp = (HttpURLConnection) new URL(myUrlTemp).openConnection();
                            connectionTemp.setRequestMethod("GET");
                            connectionTemp.setRequestProperty("Content-Length", "0");
                            connectionTemp.setConnectTimeout(10000);
                            BufferedReader bufferedReaderTemp = new BufferedReader(new InputStreamReader(connectionTemp.getInputStream()));
                            while ((responseLine = bufferedReaderTemp.readLine()) != null) {
                                resultTemp += responseLine;
                            }
                            bufferedReaderTemp.close();

                            SearchModel returnModelTemp = new SearchModel();
                            JSONObject jsonObjectTemp = new JSONObject(resultTemp);
                            JSONArray responseTemp = jsonObjectTemp.getJSONArray("response");
                            for (int j = 0; j < responseTemp.length(); j++) {
                                JSONObject currLotTemp = responseTemp.getJSONObject(j);
                                if (currLotTemp.has("uid"))
                                    returnModelTemp.setUid(currLotTemp.getInt("uid"));
                                if (currLotTemp.has("first_name"))
                                    returnModelTemp.setFirst_name(currLotTemp.getString("first_name"));
                                if (currLotTemp.has("last_name"))
                                    returnModelTemp.setLast_name(currLotTemp.getString("last_name"));
                                if (currLotTemp.has("photo_100"))
                                    returnModelTemp.setPhoto_100(currLotTemp.getString("photo_100"));
                            }

                            tempModel.setSearchModel(returnModelTemp);
                            /**/

                            ArrayList<AttachmentsModel> attachmentsModels = new ArrayList<AttachmentsModel>();

                            if (currLot.has("attachments")) {
                                JSONObject jsonObjectAttacments = new JSONObject(currLot.toString());
                                JSONArray responseAttacments = jsonObjectAttacments.getJSONArray("attachments");
                                for (int index = 0; index < responseAttacments.length(); index++) {
                                    JSONObject data = responseAttacments.getJSONObject(index);
                                    AttachmentsModel tempAttachments = new AttachmentsModel();
                                    if (data.has("type"))
                                        tempAttachments.setType(data.getString("type"));

                                    if (data.getString("type").equals("photo")) {
                                        JSONObject photo = new JSONObject(data.getString("photo"));
                                        PhotoModel photoModel = new PhotoModel();
                                        if (photo.has("src_big"))
                                            photoModel.setSrc_big(photo.getString("src_big"));
                                        if (photo.has("src_xbig"))
                                            photoModel.setSrc_xbig(photo.getString("src_xbig"));
                                        if (photo.has("src_xxbig"))
                                            photoModel.setSrc_xxbig(photo.getString("src_xxbig"));
                                        if (photo.has("height"))
                                            photoModel.setHeight(photo.getString("height"));
                                        if (photo.has("width"))
                                            photoModel.setWidth(photo.getString("width"));
                                        tempAttachments.setPhoto(photoModel);
                                    }

                                    if (data.getString("type").equals("audio")) {
                                        JSONObject audio = new JSONObject(data.getString("audio"));
                                        AudioModel audioModel = new AudioModel();
                                        if (audio.has("artist"))
                                            audioModel.setArtist(audio.getString("artist"));
                                        if (audio.has("title"))
                                            audioModel.setTitle(audio.getString("title"));
                                        if (audio.has("duration"))
                                            audioModel.setDuration(audio.getString("duration"));
                                        if (audio.has("url"))
                                            audioModel.setUrl(audio.getString("url"));
                                        tempAttachments.setAudio(audioModel);
                                    }

                                    if (data.getString("type").equals("video")) {
                                        JSONObject video = new JSONObject(data.getString("video"));
                                        VideoModel videoModel = new VideoModel();
                                        if (video.has("vid"))
                                            videoModel.setVid(video.getString("vid"));
                                        if (video.has("title"))
                                            videoModel.setTitle(video.getString("title"));
                                        if (video.has("duration"))
                                            videoModel.setDuration(video.getString("duration"));
                                        if (video.has("image"))
                                            videoModel.setImage(video.getString("image"));
                                        tempAttachments.setVideo(videoModel);
                                    }

                                    if (data.getString("type").equals("doc")) {
                                        JSONObject doc = new JSONObject(data.getString("doc"));
                                        DocumentModel docModel = new DocumentModel();
                                        if (doc.has("title"))
                                            docModel.setTitle(doc.getString("title"));
                                        if (doc.has("ext"))
                                            docModel.setExt(doc.getString("ext"));
                                        if (doc.has("url"))
                                            docModel.setUrl(doc.getString("url"));
                                        if (doc.has("thumb"))
                                            docModel.setThumb(doc.getString("thumb"));
                                        tempAttachments.setDoc(docModel);
                                    }
                                    attachmentsModels.add(tempAttachments);
                                }
                            }

                            tempModel.setAttachments(attachmentsModels);

                            if (currLot.has("comments")) {
                                CommentsModel tempComments = new CommentsModel();
                                JSONObject data = new JSONObject(currLot.getString("comments"));
                                if (data.has("count"))
                                    tempComments.setCount(Integer.parseInt(data.getString("count")));
                                tempModel.setComments(tempComments);
                            }

                            if (currLot.has("likes")) {
                                LikesModel tempLikes = new LikesModel();
                                JSONObject data = new JSONObject(currLot.getString("likes"));
                                if (data.has("count"))
                                    tempLikes.setCount(Integer.parseInt(data.getString("count")));
                                tempModel.setLikes(tempLikes);
                            }

                            if (currLot.has("reposts")) {
                                RepostsModel tempReposts = new RepostsModel();
                                JSONObject data = new JSONObject(currLot.getString("reposts"));
                                if (data.has("count"))
                                    tempReposts.setCount(Integer.parseInt(data.getString("count")));
                                tempModel.setReposts(tempReposts);
                            }

                            returnModel.add(tempModel);
                        }
                    }
                } catch (JSONException | ProtocolException | MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return returnModel;
            }

            @Override
            protected void onPostExecute(ArrayList<PostModel> result) {
                callback.onSuccess(result);
            }
        }.execute(query);
    }

    public static void asyncGetUser(String query, final CallbackSearchModel callback) {
        new AsyncTask<String, Void, SearchModel>() {
            @Override
            protected SearchModel doInBackground(String... params) {
                String resultString = "";
                String responseLine = "";
                SearchModel returnModel = new SearchModel();

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("api.vk.com")
                        .appendPath("method")
                        .appendPath("users.get")
                        .appendQueryParameter("user_ids", params[0])
                        .appendQueryParameter("fields", "photo_400_orig,photo_200_orig,online,last_seen");
                String myUrl = builder.build().toString();

                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(myUrl).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Length", "0");
                    connection.setConnectTimeout(10000);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((responseLine = bufferedReader.readLine()) != null) {
                        resultString += responseLine;
                    }
                    bufferedReader.close();
                    JSONObject jsonObject = new JSONObject(resultString);
                    JSONArray response = jsonObject.getJSONArray("response");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject currLot = response.getJSONObject(i);
                        if (currLot.has("uid"))
                            returnModel.setUid(currLot.getInt("uid"));
                        if (currLot.has("first_name"))
                            returnModel.setFirst_name(currLot.getString("first_name"));
                        if (currLot.has("last_name"))
                            returnModel.setLast_name(currLot.getString("last_name"));
                        if (currLot.has("photo_400_orig"))
                            returnModel.setPhoto_400_orig(currLot.getString("photo_400_orig"));
                        if (currLot.has("photo_200_orig"))
                            returnModel.setPhoto_200_orig(currLot.getString("photo_200_orig"));
                        if (currLot.has("online"))
                            returnModel.setOnline(Integer.parseInt(currLot.getString("online")));

                        if (currLot.has("last_seen")) {
                            LastSeenModel tempLastSeen = new LastSeenModel();
                            JSONObject data = new JSONObject(currLot.getString("last_seen"));
                            if (data.has("platform"))
                                tempLastSeen.setPlatform(Integer.parseInt(data.getString("platform")));
                            if (data.has("time"))
                                tempLastSeen.setTime(Integer.parseInt(data.getString("time")));
                            returnModel.setLast_seen(tempLastSeen);
                        }
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                return returnModel;
            }

            @Override
            protected void onPostExecute(SearchModel result) {
                callback.onSuccess(result);
            }
        }.execute(query);
    }

    public static SearchModel getUser(String uid, Activity activity) {
        String result = "";
        SearchModel returnModel = new SearchModel();
        HttpRequest request = new HttpRequest();

        RequestModel model = new RequestModel();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.vk.com")
                .appendPath("method")
                .appendPath("users.get")
                .appendQueryParameter("user_ids", uid)
                .appendQueryParameter("fields", "photo_400_orig,photo_200_orig,online");
        String myUrl = builder.build().toString();

        model.setUrl(myUrl);
        model.setMethod("GET");
        model.setHeaders(null);
        model.setBody(null);

        try {
            result = request.execute(model).get();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray response = jsonObject.getJSONArray("response");
            for (int i = 0; i < response.length(); i++) {
                JSONObject currLot = response.getJSONObject(i);
                if (currLot.has("uid"))
                    returnModel.setUid(currLot.getInt("uid"));
                if (currLot.has("first_name"))
                    returnModel.setFirst_name(currLot.getString("first_name"));
                if (currLot.has("last_name"))
                    returnModel.setLast_name(currLot.getString("last_name"));
                if (currLot.has("photo_400_orig"))
                    returnModel.setPhoto_400_orig(currLot.getString("photo_400_orig"));
                if (currLot.has("photo_200_orig"))
                    returnModel.setPhoto_200_orig(currLot.getString("photo_200_orig"));
                if (currLot.has("online"))
                    returnModel.setOnline(Integer.parseInt(currLot.getString("online")));
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        ((UserProfile) activity).updateValues(returnModel);
        return returnModel;
    }

    public static SearchModel getMinimalNamePhoto(String uid) {
        String result = "";
        SearchModel returnModel = new SearchModel();
        HttpRequest request = new HttpRequest();

        RequestModel model = new RequestModel();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.vk.com")
                .appendPath("method")
                .appendPath("users.get")
                .appendQueryParameter("user_ids", uid)
                .appendQueryParameter("fields", "photo_100");
        String myUrl = builder.build().toString();

        model.setUrl(myUrl);
        model.setMethod("GET");
        model.setHeaders(null);
        model.setBody(null);

        try {
            result = request.execute(model).get();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray response = jsonObject.getJSONArray("response");
            for (int i = 0; i < response.length(); i++) {
                JSONObject currLot = response.getJSONObject(i);
                if (currLot.has("uid"))
                    returnModel.setUid(currLot.getInt("uid"));
                if (currLot.has("first_name"))
                    returnModel.setFirst_name(currLot.getString("first_name"));
                if (currLot.has("last_name"))
                    returnModel.setLast_name(currLot.getString("last_name"));
                if (currLot.has("photo_100"))
                    returnModel.setPhoto_200(currLot.getString("photo_100"));
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        return returnModel;
    }

    public static ArrayList<SearchModel> getUsers(String uids, Activity activity) {
        String result = "";
        ArrayList<SearchModel> returnModel = new ArrayList<SearchModel>();
        HttpRequest request = new HttpRequest();

        RequestModel model = new RequestModel();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.vk.com")
                .appendPath("method")
                .appendPath("users.get")
                .appendQueryParameter("user_ids", uids)
                .appendQueryParameter("fields", "photo_200,photo_200_orig,last_seen,online");
        String myUrl = builder.build().toString();

        model.setUrl(myUrl);
        model.setMethod("GET");
        model.setHeaders(null);
        model.setBody(null);

        try {
            result = request.execute(model).get();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray response = jsonObject.getJSONArray("response");
            for (int i = 0; i < response.length(); i++) {
                JSONObject currLot = response.getJSONObject(i);
                SearchModel tempModel = new SearchModel();
                if (currLot.has("uid"))
                    tempModel.setUid(currLot.getInt("uid"));
                if (currLot.has("first_name"))
                    tempModel.setFirst_name(currLot.getString("first_name"));
                if (currLot.has("last_name"))
                    tempModel.setLast_name(currLot.getString("last_name"));
                if (currLot.has("photo_200"))
                    tempModel.setPhoto_200(currLot.getString("photo_200"));
                if (currLot.has("photo_200_orig"))
                    tempModel.setPhoto_200_orig(currLot.getString("photo_200_orig"));
                if (currLot.has("online"))
                    tempModel.setOnline(Integer.parseInt(currLot.getString("online")));

                if (currLot.has("last_seen")) {
                    LastSeenModel tempLastSeen = new LastSeenModel();
                    JSONObject data = new JSONObject(currLot.getString("last_seen"));
                    if (data.has("platform"))
                        tempLastSeen.setPlatform(Integer.parseInt(data.getString("platform")));
                    if (data.has("time"))
                        tempLastSeen.setTime(Integer.parseInt(data.getString("time")));
                    tempModel.setLast_seen(tempLastSeen);
                }

                returnModel.add(tempModel);
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        return returnModel;
    }

    public static void GetAudioAsync(String url, final String artist, final String title) {
        new AsyncTask<String, Integer, Void>() {
            @Override
            protected Void doInBackground(String... args) {
                try {
                    int count = 0;
                    URL url = new URL(args[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(10000);
                    connection.connect();

                    int lenghtOfFile = connection.getContentLength();

                    File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/Music/vkcheck/");
                    try {
                        if (dir.mkdir()) {
                            System.out.println("Directory created");
                        } else {
                            System.out.println("Directory is not created");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Music/vkcheck/" + artist.replace(" ", "_") + "-" + title.replace(" ", "_") + ".mp3");
                    byte data[] = new byte[1024];
                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        publishProgress((int) (total * 100 / lenghtOfFile));
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... progress) {
                Log.e("PROGRESS DOWNLOAD", String.valueOf(progress[0]));
            }
        }.execute(url);
    }
}
