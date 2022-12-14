package com.codepath.apps.restclienttemplate;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.codepath.apps.restclienttemplate.models.Media;
import com.codepath.apps.restclienttemplate.models.SampleModel;
import com.codepath.apps.restclienttemplate.models.SampleModelDao;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.TweetDao;
import com.codepath.apps.restclienttemplate.models.User;

@Database(entities={SampleModel.class, Tweet.class, User.class, Media.class}, version=2)
public abstract class MyDatabase extends RoomDatabase {
    public abstract SampleModelDao sampleModelDao();


    // Database name to be used
    public static final String NAME = "MyDataBase";

    public abstract TweetDao tweetDao();
}
