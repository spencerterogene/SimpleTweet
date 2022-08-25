package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "userId"))
public class Tweet {
    @ColumnInfo
    public String Body;

    @ColumnInfo
    public String createdAt;

    @ColumnInfo
    @PrimaryKey
    public  long id;
    @Ignore
    public User user;

    @ColumnInfo
    public long userId;
    @ColumnInfo
    public boolean retweet;
    @ColumnInfo
    public boolean favorite;
    @ColumnInfo
    public String favorite_count;
    @ColumnInfo
    public String retweet_count;

    public Tweet(){}
    @Ignore
    public  Media media;


    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.Body = jsonObject.getString("text");
        tweet.id = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        User user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.userId = user.id;
        tweet.user = user;
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

    public long getUserId() {
        return userId;
    }

    public boolean isRetweet() {
        return retweet;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public String getFavorite_count() {
        return favorite_count;
    }

    public String getRetweet_count() {
        return retweet_count;
    }

    public Media getMedia() {
        return media;
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
