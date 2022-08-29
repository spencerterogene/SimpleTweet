package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class Reply extends DialogFragment {
    TwitterClient client;
    private EditText mEditText;
    public static final int MAX_TWEET_LENGTH = 140;
    public static final String TAG = "EditNameDialogFragment";
    public static final String KEY = "BROUILLONS";
    ImageButton cross1;
    Context context;
    Button btnTweet;
    TextView name1;
    TextView username1;
    ImageView profile1;
    TextView arrow;
    TextInputLayout textfield;




    public Reply() { }

    public static Reply newInstance(String title) {
        Reply frag = new Reply();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reply, container);

    }

    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        Tweet tweet = Parcels.unwrap(bundle.getParcelable("tweets"));
        User MyUser = Parcels.unwrap(bundle.getParcelable("profile"));

        // Get field from view

        cross1 = view.findViewById(R.id.cross1);
        arrow = view.findViewById(R.id.arrow);
        name1 = view.findViewById(R.id.Name1);
        username1 = view.findViewById(R.id.UserName1);
        profile1 = view.findViewById(R.id.Image2);
        btnTweet = view.findViewById(R.id.btn1);
        textfield = view.findViewById(R.id.text2);
        mEditText = view.findViewById(R.id.etCompose_frag1);
        client = TwitterApp.getRestClient(context);
        name1.setText(MyUser.getName());
        username1.setText(MyUser.getScreenName());
        Glide.with(getContext()).load(MyUser.profileImageUrl)
                .transform(new RoundedCorners(70))
                .into(profile1);


        //draft
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String draft = preferences.getString(KEY,"");

        mEditText.setText(tweet.user.getScreenName());
        cross1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        textfield.setHint("Reply to"+tweet.user.getScreenName());
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetContent = mEditText.getText().toString();
                if (tweetContent.isEmpty()) {
                    Toast.makeText(context, "Sorry your tweet cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (tweetContent.length() > MAX_TWEET_LENGTH) {
                    Toast.makeText(context, "Sorry your tweet is too long", Toast.LENGTH_LONG).show();
                    return;
                }
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "on success load more data");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG, "published tweet is : " + tweet.Body);
                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dismiss();

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "on failure to publish tweet", throwable);

                    }
                });

            }
        });




        // Fetch arguments from bundle and set title

        String title = getArguments().getString("title", "Enter Name");

        getDialog().setTitle(title);

        // Show soft keyboard automatically and request focus to field



        getDialog().getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().getWindow().setLayout(700,900);

    }

}
