package com.example.parstagram_fbu;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;


public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        //Register my parse models

    //        ParseObject.registerSubclass(Post.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("63de6mxS3KTWOUqjQRAJFr9sK6jo2kq8k7I5wGFz")
                .clientKey("0ZTTC3Uwh8xQbC2QWqaRovea89zHWVNwJEKLnbOB")
                .server("https://parseapi.back4app.com")
                .build()
        );





    }
}
