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

        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweets"));

        Name.setText(tweet.getUser().getName());
        UserName.setText(tweet.getUser().getScreenName());
        Description.setText(tweet.getBody());
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
                Favorite_Count2.setText(String.valueOf(var));
                Favorite_Count.setVisibility(View.INVISIBLE);
                Favorite_Count2.setVisibility(View.VISIBLE);
            }
        });
        Favorite_Count2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int var = Integer.parseInt(tweet.favorite_count);
                --var;
                Favorite_Count.setText(String.valueOf(var));
                Favorite_Count.setVisibility(View.VISIBLE);
                Favorite_Count2.setVisibility(View.INVISIBLE);
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