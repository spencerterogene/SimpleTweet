package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class ExtendedEntities {
    public ExtendedEntities(){}

    public String url;

    public static ExtendedEntities fromJson(JSONObject jsonObject) throws JSONException {
        ExtendedEntities extendedEntities = new ExtendedEntities();

        if(!jsonObject.has("media")){
            extendedEntities.url = "";
        }else if(jsonObject.has("media")){
            extendedEntities.url = jsonObject.getString("media");
            JSONArray media = jsonObject.getJSONArray("media");
            extendedEntities.url= media.getJSONObject(0).getString("url");

        }

        return extendedEntities;
    }
}
