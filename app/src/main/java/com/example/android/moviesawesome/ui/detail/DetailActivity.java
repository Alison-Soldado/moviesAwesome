package com.example.android.moviesawesome.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.moviesawesome.R;
import com.example.android.moviesawesome.data.model.movie.Result;
import com.example.android.moviesawesome.data.source.local.AppDatabase;
import com.example.android.moviesawesome.util.AppExecutors;

public class DetailActivity extends AppCompatActivity {

    private static final int DEFAULT_FAVORITE_ID = -1;

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
    private ViewPager viewPagerDetail;
    private DetailAdapter detailAdapter;
    private DetailViewModel detailViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initComponents();
        initIntent();
        setupToolbar();
        setupCollapsing();
        initInstance();
        fillComponents();
        setupViewPager();
        initObserver();
    }

    private void initInstance() {
        AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
        DetailViewModelFactory factory = new DetailViewModelFactory(appDatabase, result.getId());
        detailViewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
    }

    private void initObserver() {
        detailViewModel.getFavorite().observe(this, result -> {
            if (result != null) {
                if (this.result.getId() == result.getId()) {
                    Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Falhou", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupViewPager() {
        detailAdapter = new DetailAdapter(getSupportFragmentManager(), result.getId());
        viewPagerDetail.setAdapter(detailAdapter);
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
        viewPagerDetail = findViewById(R.id.activity_detail_view_pager);
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
        AppExecutors.getInstance().diskIO().execute(() -> detailViewModel.insertFavorite(result));
    }
}
