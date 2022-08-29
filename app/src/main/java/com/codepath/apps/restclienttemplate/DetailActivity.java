package com.codepath.apps.restclienttemplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {
    public static final String TAG = "DetailActivity";
    public static final int MAX_TWEET_LENGTH = 140;
    ImageView Image;
    EditText mEditText;
    TwitterClient client;
    TextView Name;
    TextView UserName;
    TextView Description;
    ImageView Images;
    TextView Favorite_Count;
    TextView Retweet_Count;
    TextView Favorite_Count2;
    TextView retweeted;
    TextView Retweet_Count1;
    TextView Heure;
    TextView likes;
    TextView retweet2;
    Button btnTweet2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left_thin);

        getSupportActionBar().setLogo(R.drawable.twitter);
        getSupportActionBar().setTitle("       Tweet");

        Image = findViewById(R.id.Image);
        Name = findViewById(R.id.Name);
        UserName = findViewById(R.id.UserName);
        Description = findViewById(R.id.Description);
        Images = findViewById(R.id.Image1);
        Favorite_Count = findViewById(R.id.coeur);
        Retweet_Count = findViewById(R.id.repeat);
        Favorite_Count2 = findViewById(R.id.coeur1);
        retweeted = findViewById(R.id.repeat);
        Retweet_Count1 = findViewById(R.id.repeat1);
        Heure = findViewById(R.id.heure);
        likes = findViewById(R.id.likes);
        mEditText = findViewById(R.id.etCompose_frag);
        retweet2 = findViewById(R.id.retweet2);
        btnTweet2 = findViewById(R.id.btn3);




        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweets"));

        Name.setText(tweet.getUser().getName());
        UserName.setText(tweet.getUser().getScreenName());
        Description.setText(tweet.getBody());
        mEditText.setHint("Reply to "+tweet.user.getName());
        mEditText.setText(tweet.user.getScreenName());
        retweeted.setText(tweet.retweet_count);
        Retweet_Count1.setText(tweet.retweet_count);
        Heure.setText(tweet.getCreatedAt1());
        likes.setText(tweet.getFavorite_count()+"Favorites");
        retweet2.setText(tweet.getRetweet_count()+"Retweet");



        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .transform(new RoundedCorners(50))
                .into(Image);

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

        btnTweet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetContent = mEditText.getText().toString();
                if (tweetContent.isEmpty()) {
                    Toast.makeText(DetailActivity.this, "Sorry your tweet cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (tweetContent.length() > MAX_TWEET_LENGTH) {
                    Toast.makeText(DetailActivity.this, "Sorry your tweet is too long", Toast.LENGTH_LONG).show();
                    return;
                }
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i("tweet", tweet.Body);
                            mEditText.setHint("Reply");
                            mEditText.setText("");
                            Toast.makeText(DetailActivity.this, "Tweeted", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

                    }
                });
            }
        });

        if (!tweet.media.getMediaUrl().isEmpty()){
            Glide.with(this)
                    .load(tweet.media.getMediaUrl())
                    .transform(new RoundedCorners(50))
                    .into(Images);
        }



    }



    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem menuItem) {
        Intent intent = new Intent(DetailActivity.this,TimelineActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent,0);
        return true;
    }


}