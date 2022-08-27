package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class EditNameDialogFragment extends DialogFragment {
    private EditText mEditText;
    public static final String TAG = "EditNameDialogFragment";
    public static final int MAX_TWEET_LENGTH = 140;
    Button btnTweet1;
    ImageButton cross;
    TwitterClient client;
    Context context;




    public EditNameDialogFragment() { }

    public static EditNameDialogFragment newInstance(String title) {
        EditNameDialogFragment frag = new EditNameDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_name, container);

    }

    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // Get field from view

        mEditText = (EditText) view.findViewById(R.id.etCompose_frag);
        btnTweet1 = view.findViewById(R.id.btn);
        cross = view.findViewById(R.id.cross);
        Bundle bundle = getArguments();
        User MyUser = Parcels.unwrap(bundle.getParcelable("profile"));

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnTweet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetContent = mEditText.getText().toString();
                if (tweetContent.isEmpty()){
                    Toast.makeText(context, "Sorry, your tweet cannot be Empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (tweetContent.length()>MAX_TWEET_LENGTH) {
                    Toast.makeText(context, "Sorry, your tweet is too long", Toast.LENGTH_LONG).show();
                    return;
                }

                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG,"on Success to publis tweet");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG, "publish tweet says: "+tweet.Body);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG,"onFailure to publish tweet",throwable);
                    }
                });
                dismiss();
            }


        });


        // Fetch arguments from bundle and set title

        String title = getArguments().getString("title", "Enter Name");

        getDialog().setTitle(title);

        // Show soft keyboard automatically and request focus to field

        mEditText.requestFocus();

        getDialog().getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().getWindow().setLayout(700,900);

    }
}
