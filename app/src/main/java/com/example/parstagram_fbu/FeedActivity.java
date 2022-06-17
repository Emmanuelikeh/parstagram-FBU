package com.example.parstagram_fbu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.parstagram_fbu.databinding.ActivityFeedBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    protected PostsAdapter adapter;
    protected List<Post> allPosts;

    private ActivityFeedBinding activityFeedBinding;
    public  static final String TAG = "FeesActivity";
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_feed);

        activityFeedBinding = ActivityFeedBinding.inflate(getLayoutInflater());
        View view = activityFeedBinding.getRoot();
        setContentView(view);

        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(this,allPosts);





        // binding the giving our recyclerview our adapter

        activityFeedBinding.rvPosts.setAdapter(adapter);
        activityFeedBinding.rvPosts.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipeContainer);

        activityFeedBinding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                adapter.clear();
                queryPosts();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        // Configure the refreshing colors
        activityFeedBinding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // query posts from Parstagram
        queryPosts();



    }





    private void queryPosts() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by user key
        query.include(Post.KEY_USER);
        // limit query to latest 20 items
        query.setLimit(20);
        // order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                // for debugging purposes let's print every post description to logcat
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }

                // save received posts to list and notify adapter of new data
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }
}