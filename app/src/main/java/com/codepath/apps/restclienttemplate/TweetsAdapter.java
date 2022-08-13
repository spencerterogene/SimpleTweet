package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{

    Context context;
    List<Tweet> tweets;
    public TweetsAdapter (Context context ,List<Tweet> tweets){
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.item_tweet,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public void clear(){
        tweets.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Tweet> tweetList){
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ProfileImage;
        TextView Body;
        TextView ScreenName;
        TextView username;
        TextView Date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ProfileImage = itemView.findViewById(R.id.ProfileImage);
            Body = itemView.findViewById(R.id.Body);
            ScreenName = itemView.findViewById(R.id.ScreenName);
            username = itemView.findViewById(R.id.username);
            Date = itemView.findViewById(R.id.Date);

        }

        public void bind(Tweet tweet) {
            Body.setText(tweet.Body);
            ScreenName.setText(tweet.user.name);
            username.setText("@"+tweet.user.screenName);
            Date.setText(tweet.getCreatedAt());
            Glide.with(context).load(tweet.user.profileImageUrl).into(ProfileImage);
        }
    }
}
