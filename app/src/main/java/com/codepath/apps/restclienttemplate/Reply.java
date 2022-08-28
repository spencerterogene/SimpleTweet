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

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class Reply extends DialogFragment {
    TwitterClient client;
    public static final String KEY = "BROUILLONS";
    ImageButton cross1;
    Context context;
    TextView name1;
    TextView username1;
    ImageView profile1;




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
        User MyUser = Parcels.unwrap(bundle.getParcelable("profile"));

        // Get field from view

        cross1 = view.findViewById(R.id.cross1);
        name1 = view.findViewById(R.id.Name1);
        username1 = view.findViewById(R.id.UserName1);
        profile1 = view.findViewById(R.id.Image2);

        client = TwitterApp.getRestClient(context);

        name1.setText(MyUser.getName());
        username1.setText(MyUser.getScreenName());
        Glide.with(getContext()).load(MyUser.profileImageUrl)
                .transform(new RoundedCorners(70))
                .into(profile1);


        //draft
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String draft = preferences.getString(KEY,"");

//        if(draft.isEmpty()){
//            mEditText.setText(draft);
//        }

        cross1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });




        // Fetch arguments from bundle and set title

        String title = getArguments().getString("title", "Enter Name");

        getDialog().setTitle(title);

        // Show soft keyboard automatically and request focus to field

//        mEditText.requestFocus();

        getDialog().getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().getWindow().setLayout(700,900);

    }

}
