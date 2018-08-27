package com.example.android.moviesawesome.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.moviesawesome.R;
import com.example.android.moviesawesome.data.model.Result;

public class DetailActivity extends AppCompatActivity {

    private Result result;
    private TextView textViewTitle;
    private ImageView imageViewImage;
    private TextView textViewYear;
    private TextView textViewDuration;
    private TextView textViewRating;
    private TextView textViewDescription;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageViewMovie;
    private FloatingActionButton fabFavorite;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initComponents();
        initIntent();
        setupToolbar();
        setupCollapsing();
        fillComponents();
    }

    private void initComponents() {
        imageViewImage = findViewById(R.id.activity_detail_image);
        textViewYear = findViewById(R.id.activity_detail_year);
        textViewDuration = findViewById(R.id.activity_detail_duration);
        textViewRating = findViewById(R.id.activity_detail_rating);
        textViewDescription = findViewById(R.id.activity_detail_description);
        toolbar = findViewById(R.id.activity_detail_toolbar);
        collapsingToolbarLayout = findViewById(R.id.activity_detail_collapsing);
        imageViewMovie = findViewById(R.id.activity_detail_image_movie);
        fabFavorite = findViewById(R.id.activity_detail_fab);
    }

    private void initIntent() {
        result = getIntent().getParcelableExtra("result");
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupCollapsing() {
        collapsingToolbarLayout.setExpandedTitleColor(
                getResources().getColor(android.R.color.transparent));
    }

    private void fillComponents() {
        collapsingToolbarLayout.setTitle(result.getTitle());
        textViewYear.setText(result.getRelease_date());
        textViewRating.setText(String.valueOf(result.getVote_average()));
        textViewDescription.setText(result.getOverview());

        Glide
                .with(this)
                .load("http://image.tmdb.org/t/p/".concat("w185").concat(result.getPoster_path()))
                .into(imageViewImage);

        Glide
                .with(this)
                .load("http://image.tmdb.org/t/p/".concat("w185").concat(result.getPoster_path()))
                .into(imageViewMovie);
    }

    public void markAsFavorite(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
