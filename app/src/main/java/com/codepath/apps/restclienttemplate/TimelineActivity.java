package com.codepath.apps.restclienttemplate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.TweetDao;
import com.codepath.apps.restclienttemplate.models.TweetWithUser;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;


public class TimelineActivity extends AppCompatActivity {
    public static final String TAG ="TimelineActivity";
    TwitterClient client;
    TweetDao tweetDao;
    RecyclerView Tweets;
    List<Tweet> tweets;
    TweetsAdapter adapter;
    SwipeRefreshLayout swipeContainer;
    private final int REQUEST_CODE = 20;
    EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        FloatingActionButton floatingBtn;
        floatingBtn = findViewById(R.id.floatintbut);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setLogo(R.drawable.twitter);
        getSupportActionBar().setTitle(" ");

        client = TwitterApp.getRestClient(this);
        tweetDao = ((TwitterApp) getApplicationContext()).getMyDatabase().tweetDao();


        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG,"fetching new data!");
                populateHomeTimeline();
            }
        });
        Tweets = findViewById(R.id.Tweets);
        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(this,  tweets);
        adapter.setOnItemClickListener(new TweetsAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View itemView, int position) {
                Intent i = new Intent(TimelineActivity.this,DetailActivity.class);
                Tweet tweet = tweets.get(position);
                i.putExtra("tweets", Parcels.wrap(tweet));
                TimelineActivity.this.startActivity(i);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        Tweets.setLayoutManager(new LinearLayoutManager(this));
        Tweets.setAdapter(adapter);
        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view ) {
                Intent intent = new Intent(TimelineActivity.this,ComposeActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.i(TAG,"OnLoadMore"+page);
                loadMoreData();
            }
        };
        Tweets.addOnScrollListener(scrollListener);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Showing data from database");
                List<TweetWithUser> tweetWithUsers = tweetDao.recentItems();
                List<Tweet>tweetsFromDB = TweetWithUser.getTweetList(tweetWithUsers);
                adapter.clear();
                adapter.addAll(tweetsFromDB);
            }
        });

        populateHomeTimeline();
    }



    private void loadMoreData() {
        client.getNextPageOfTweets(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG,"OnSuccess! for loadMoreData"+ json.toString());
                JSONArray jsonArray = json.jsonArray;
                try {
                    List<Tweet> tweets = Tweet.fromJsonArray(jsonArray);
                    adapter.addAll(tweets);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.i(TAG,"OnFailure for loadMoreData",throwable);

            }
        },tweets.get(tweets.size()-1).id);
    }


    private void populateHomeTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG,"OnSuccess!"+ json.toString());
                JSONArray jsonArray = json.jsonArray;
                try {
                     List<Tweet> tweetsFromNetwork = Tweet.fromJsonArray(jsonArray);
                     adapter.clear();
                     adapter.addAll(tweetsFromNetwork);
                     swipeContainer.setRefreshing(false);
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "saving data into the database");
                            List<User> usersFromNetwork = User.fromJsonTweetArray(tweetsFromNetwork);
                            tweetDao.insertModel(usersFromNetwork.toArray(new User[0]));
                            tweetDao.insertModel(tweetsFromNetwork.toArray(new Tweet[0]));

                        }
                    });

                }catch (JSONException e){
                    Log.e(TAG,"JsonException",e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG,"OnFailure!"+ response,throwable);

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("twwet"));
            tweets.add(0,tweet);
            adapter.notifyItemInserted(0);
            Tweets.smoothScrollToPosition(0);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showEditDialog() {

        FragmentManager fm = getSupportFragmentManager();

        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance("Some Title");

        editNameDialogFragment.show(fm, "fragment_edit_name");

    }
}