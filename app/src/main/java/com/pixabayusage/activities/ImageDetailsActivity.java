package com.pixabayusage.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pixabayusage.R;

public class ImageDetailsActivity extends AppCompatActivity {

    private ImageView imageView, fab;
    private TextView tagsTV, viewsTV, downloadsTV, likesTV, commentsTV, userTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        getWindow().setStatusBarColor(Color.GRAY);
        initViews();
        addListeners();
        getDetails();
    }

    private void addListeners() {
        fab.setOnClickListener(view -> {
            finish();
        });
    }

    private void initViews() {
        tagsTV = findViewById(R.id.tags);
        viewsTV = findViewById(R.id.views);
        downloadsTV = findViewById(R.id.downloads);
        likesTV = findViewById(R.id.likes);
        commentsTV = findViewById(R.id.comments);
        userTV = findViewById(R.id.user);
        imageView = findViewById(R.id.image);
        fab = findViewById(R.id.fab);
    }

    private void getDetails() {
        Intent i = getIntent();

        final String url = i.getExtras().getString("url");
        final String tags = i.getExtras().getString("tags");
        final Long views = i.getExtras().getLong("views");
        final Long downloads = i.getExtras().getLong("downloads");
        final Long likes = i.getExtras().getLong("likes");
        final Long comments = i.getExtras().getLong("comments");
        final String user = i.getExtras().getString("user");

        Glide.with(imageView)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView);
        tagsTV.setText(tags);
        viewsTV.setText(String.valueOf(views));
        downloadsTV.setText(String.valueOf(downloads));
        likesTV.setText(String.valueOf(likes));
        commentsTV.setText(String.valueOf(comments));
        userTV.setText(user);
    }
}