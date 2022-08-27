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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.SimpleMainThreadMediaPlayerListener;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

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
        TweetsAdapter.ViewHolder viewHolder = new TweetsAdapter.ViewHolder(view,listener);
        return  viewHolder;
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener=listener;
    }

    public interface OnItemClickListener{
    void OnItemClick(View itemView,int position);
    }
    private OnItemClickListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ProfileImage;
        TextView Body;
        TextView ScreenName;
        TextView username;
        TextView Date;
        ImageView Image;
        TextView Favorite_Count;
        TextView Retweet_Count;
        TextView Favorite_Count2;
        TextView retweeted;
        TextView Retweet_Count1;
        VideoPlayerView mVideoPlayer_1;
        ImageView mVideoCover;


        public ViewHolder(@NonNull View itemView, final OnItemClickListener clickListener) {
            super(itemView);
            ProfileImage = itemView.findViewById(R.id.ProfileImage);
            Body = itemView.findViewById(R.id.Body);
            ScreenName = itemView.findViewById(R.id.ScreenName);
            username = itemView.findViewById(R.id.username);
            Date = itemView.findViewById(R.id.Date);
            Image = itemView.findViewById(R.id.Image);
            Favorite_Count = itemView.findViewById(R.id.coeur);
            Favorite_Count2 = itemView.findViewById(R.id.coeur1);
            retweeted = itemView.findViewById(R.id.repeat);
            Retweet_Count1 = itemView.findViewById(R.id.repeat1);
            mVideoPlayer_1 = itemView.findViewById(R.id.video_player_1);
            mVideoCover = itemView.findViewById(R.id.video_cover_1);
            Retweet_Count = itemView.findViewById(R.id.repeat);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.OnItemClick(itemView,getAdapterPosition());
                }
            });

        }


        public void bind(Tweet tweet) {
            Body.setText(tweet.Body);
            ScreenName.setText(tweet.user.name);
            username.setText("@"+tweet.user.screenName);
            Date.setText(tweet.getCreatedAt());
            Favorite_Count.setText(tweet.favorite_count);
            Favorite_Count2 = itemView.findViewById(R.id.coeur1);
            retweeted = itemView.findViewById(R.id.repeat);
            Retweet_Count1 = itemView.findViewById(R.id.repeat1);
            mVideoPlayer_1 = itemView.findViewById(R.id.video_player_1);
            mVideoCover = itemView.findViewById(R.id.video_cover_1);




            retweeted.setText(tweet.retweet_count);
            Retweet_Count1.setText(tweet.retweet_count);
            Glide.with(context).load(tweet.user.profileImageUrl)
                    .transform(new RoundedCorners(50))
                    .into(ProfileImage);

           if (!tweet.media.getMediaUrl().isEmpty()){
               Glide.with(context)
                       .load(tweet.media.getMediaUrl())
                       .transform(new RoundedCorners(50))
                       .into(Image);
           }

            if (tweet.favorite){
                Favorite_Count.setVisibility(View.INVISIBLE);
                Favorite_Count2.setVisibility(View.VISIBLE);
            }else{
                Favorite_Count.setVisibility(View.VISIBLE);
                Favorite_Count2.setVisibility(View.INVISIBLE);
            }
           Favorite_Count.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int var = Integer.parseInt(tweet.favorite_count);
                   ++var;
                   tweet.retweet=true;
                   Favorite_Count2.setText(String.valueOf(var));
                   Favorite_Count.setVisibility(View.INVISIBLE);
                   Favorite_Count2.setVisibility(View.VISIBLE);
               }
           });
            Favorite_Count2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int var = Integer.parseInt(tweet.favorite_count);
                    tweet.retweet=true;
                    Favorite_Count.setText(String.valueOf(var));
                    Favorite_Count.setVisibility(View.VISIBLE);
                    Favorite_Count2.setVisibility(View.INVISIBLE);
                }
            });

            if (tweet.retweet){
                Retweet_Count.setVisibility(View.INVISIBLE);
                Retweet_Count1.setVisibility(View.VISIBLE);
            }else{
                Retweet_Count.setVisibility(View.VISIBLE);
                Retweet_Count1.setVisibility(View.INVISIBLE);
            }

           Retweet_Count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int var = Integer.parseInt(tweet.retweet_count);
                        ++var;
                        tweet.retweet=true;

                        Retweet_Count1.setText(String.valueOf(var));
                        Retweet_Count.setVisibility(View.INVISIBLE);
                        Retweet_Count1.setVisibility(View.VISIBLE);
                    }
                });
            Retweet_Count1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int var = Integer.parseInt(tweet.retweet_count);
                    Retweet_Count.setText(String.valueOf(var));
                    Retweet_Count.setVisibility(View.VISIBLE);
                    Retweet_Count1.setVisibility(View.INVISIBLE);
                    tweet.retweet=true;

                }
            });

             VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
                @Override
                public void onPlayerItemChanged(MetaData metaData) {

                }
            });

            mVideoPlayer_1.addMediaPlayerListener(new SimpleMainThreadMediaPlayerListener(){
                @Override
                public void onVideoPreparedMainThread() {
                    // We hide the cover when video is prepared. Playback is about to start
                    mVideoCover.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onVideoStoppedMainThread() {
                    // We show the cover when video is stopped
                    mVideoCover.setVisibility(View.VISIBLE);
                }

                @Override
                public void onVideoCompletionMainThread() {
                    // We show the cover when video is completed
                    mVideoCover.setVisibility(View.VISIBLE);
                }
            });


        }

    }
}
