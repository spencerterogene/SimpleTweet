package com.codepath.apps.restclienttemplate.models;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel
public class Tweet {
    public String Body;
    public String createdAt;
    public  long id;
    public User user;

    public Tweet(){}
    public  Media media;


    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.Body = jsonObject.getString("text");
        tweet.id = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.media = Media.fromJson(jsonObject.getJSONObject("entities"));

        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray)throws JSONException{
        List<Tweet> tweets = new ArrayList<>();
        for (int i =0;i< jsonArray.length();i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;

    }

    public String getBody() {
        return Body;
    }

    public String getCreatedAt() {
        return  "."+ TimeFormatter.getTimeDifference(createdAt);
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }


}
