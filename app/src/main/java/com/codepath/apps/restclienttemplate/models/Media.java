package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity
public class Media {
    @ColumnInfo
    @PrimaryKey
    public long id_photo;

    @ColumnInfo
    String mediaUrl;

    public static List<Media> fromJsonTweetArray(List<Tweet> tweetsFromNetwork) {
        List<Media>medias=new ArrayList<>();
        for (int i =0;i<tweetsFromNetwork.size();i++){
            medias.add(tweetsFromNetwork.get(i).media);
        }
        return medias;
    }


    public String getMediaUrl(){
        return mediaUrl;
    }

    public Media() {}
    public static Media fromJson(JSONObject jsonObject) throws JSONException{
        Media media = new Media();
        if(!jsonObject.has("media")){
            media.mediaUrl ="";

        }else if(jsonObject.has("media")){
            JSONArray media1 = jsonObject.getJSONArray("media");
            media.mediaUrl= media1.getJSONObject(0).getString("media_url_https");
            media.id_photo = media1.getJSONObject(0).getLong("id");
        }
        return media;
    }

}
