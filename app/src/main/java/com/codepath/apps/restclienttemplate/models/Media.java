package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Media {
    String mediaUrl;



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
        }
        return media;
    }

}
