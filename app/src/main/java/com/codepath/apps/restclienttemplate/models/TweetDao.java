package com.codepath.apps.restclienttemplate.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TweetDao {
    @Query("SELECT Tweet.Body AS Tweet_body,Tweet.createdAt AS tweet_createdAt, Tweet.id AS tweet_id," +
            "Tweet.favorite_count AS tweet_favorite_count,Tweet.retweet_count AS tweet_retweet_count," +
            "Tweet.favorite AS tweet_favorite,Tweet.retweet AS tweet_retweet,User.* ,Media.* FROM Tweet, " +
            "Media INNER JOIN User ON Tweet.userId = User.id ORDER BY createdAt DESC LIMIT 5")
    List<TweetWithUser> recentItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(Tweet... tweets);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(User... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(Media... media);
}
