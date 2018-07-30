package com.example.android.moviesawesome.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
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
    private Button buttonFavorite;
    private TextView textViewDescription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setupToolbar();
        initIntent();
        initComponents();
        fillComponents();
    }

    private void setupToolbar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initIntent() {
        result = getIntent().getParcelableExtra("result");
    }

    private void initComponents() {
        textViewTitle = findViewById(R.id.activity_detail_title);
        imageViewImage = findViewById(R.id.activity_detail_image);
        textViewYear = findViewById(R.id.activity_detail_year);
        textViewDuration = findViewById(R.id.activity_detail_duration);
        textViewRating = findViewById(R.id.activity_detail_rating);
        buttonFavorite = findViewById(R.id.activity_detail_favorite);
        textViewDescription = findViewById(R.id.activity_detail_description);
    }

    private void fillComponents() {
        textViewTitle.setText(result.getTitle());
        textViewYear.setText(result.getRelease_date());
        textViewRating.setText(String.valueOf(result.getVote_average()));
        textViewDescription.setText(result.getOverview());

        Glide
                .with(this)
                .load("http://image.tmdb.org/t/p/".concat("w185").concat(result.getPoster_path()))
                .into(imageViewImage);
    }
}
