package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    ImageView Image;
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



        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweets"));

        Name.setText(tweet.getUser().getName());
        UserName.setText(tweet.getUser().getScreenName());
        Description.setText(tweet.getBody());
        retweeted.setText(tweet.retweet_count);
        Retweet_Count1.setText(tweet.retweet_count);
        Heure.setText(tweet.getCreatedAt1());


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
                Favorite_Count.setText(String.valueOf(var));
                Favorite_Count.setVisibility(View.VISIBLE);
                Favorite_Count2.setVisibility(View.INVISIBLE);
                tweet.retweet=true;

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
                var++;
                tweet.retweet=true;
                Retweet_Count.setText(String.valueOf(var));
                Retweet_Count.setVisibility(View.INVISIBLE);
                Retweet_Count1.setVisibility(View.VISIBLE);

            }
        });

        Retweet_Count1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int var = Integer.parseInt(tweet.retweet_count);
                tweet.retweet=true;
                Retweet_Count1.setText(String.valueOf(var));
                retweeted.setVisibility(View.VISIBLE);
                Retweet_Count1.setVisibility(View.INVISIBLE);
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
    public boolean onOptionsItemSelected (MenuItem menuItem) {
        int homeAsUp = R.id.homeAsUp;
            Intent intent = new Intent(DetailActivity.this,TimelineActivity.class);
            startActivity(intent);

        return true;
    }


}