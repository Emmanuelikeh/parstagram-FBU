package fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parstagram_fbu.Post;
import com.example.parstagram_fbu.PostsAdapter;
import com.example.parstagram_fbu.R;
import com.example.parstagram_fbu.databinding.FragmentFeedBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;



public class FeedFragment extends Fragment {

    private FragmentFeedBinding fragmentFeedBinding;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    private SwipeRefreshLayout swipeRefreshLayout;
    public  static final String TAG = "PostFragment";



    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentFeedBinding = FragmentFeedBinding.inflate(getLayoutInflater(),container,false);
        View view = fragmentFeedBinding.getRoot();
        return view;
//        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentFeedBinding = null;

    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstancesState){
        super.onViewCreated(view,savedInstancesState);
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(),allPosts);
        fragmentFeedBinding.rvPosts.setAdapter(adapter);
        fragmentFeedBinding.rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout = view.findViewById(R.id.swipeContainer);

        fragmentFeedBinding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        fragmentFeedBinding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // query posts from Parstagram



        queryPosts();
    }


    protected void queryPosts() {
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