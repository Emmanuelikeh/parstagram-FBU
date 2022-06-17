package com.example.parstagram_fbu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.parstagram_fbu.databinding.ActivityDetailBinding;

import org.parceler.Parcels;

import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    Post post;
    //pass the Context
//    private Context context;
    private ActivityDetailBinding activityDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
        activityDetailBinding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = activityDetailBinding.getRoot();
        setContentView(view);

//        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        post = (Post) getIntent().getExtras().get("post");
        //Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", post.getUser().getUsername()));


        activityDetailBinding.tvTitle.setText(post.getUser().getUsername());

        //Setting the timestamp to on the selected image side
        Date createdAt = post.getCreatedAt();
        activityDetailBinding.tvTimeStamp.setText(Post.calculateTimeAgo(createdAt));

        if(post.getImage() != null){
            Glide.with(this).load(post.getImage().getUrl()).into(activityDetailBinding.ivDetailedPostImage);

        }






    }
}