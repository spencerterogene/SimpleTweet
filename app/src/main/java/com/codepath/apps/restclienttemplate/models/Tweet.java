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
    public boolean retweet;
    public boolean favorite;
    public String favorite_count;
    public String retweet_count;

    public Tweet(){}
    public  Media media;


    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.Body = jsonObject.getString("text");
        tweet.id = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.media = Media.fromJson(jsonObject.getJSONObject("entities"));
        tweet.favorite_count = jsonObject.getString("favorite_count");
        tweet.retweet_count = jsonObject.getString("retweet_count");
        tweet.retweet = jsonObject.getBoolean("retweeted");
        tweet.favorite = jsonObject.getBoolean("favorited");
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

    public String getCreatedAt1() {
        return TimeFormatter.getTimeStamp(createdAt);
    }


    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }


}
