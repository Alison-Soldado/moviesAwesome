package com.example.android.moviesawesome.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.moviesawesome.R;
import com.example.android.moviesawesome.data.model.movie.Result;
import com.example.android.moviesawesome.data.model.video.ResultVideo;
import com.example.android.moviesawesome.ui.main.MainAdapter;
import com.example.android.moviesawesome.ui.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity
        implements DetailAdapter.DetailAdapterOnItemClickHandler {

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
    private DetailViewModel detailViewModel;
    private DetailAdapter detailAdapter;
    private RecyclerView recyclerViewTrailer;
    private List<ResultVideo> results = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initComponents();
        initIntent();
        initInstance();
        setupToolbar();
        setupCollapsing();
        fillComponents();
        getList();
        setupRecyclerView();
        initObservers();
    }

    private void initObservers() {
        detailViewModel.detailSingleLiveEvent.observe(this, video -> {
            if (video != null) {
                detailAdapter.addItems(video.getResults());
//                recyclerViewTrailer.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewTrailer.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewTrailer.addItemDecoration(itemDecoration);
        recyclerViewTrailer.setHasFixedSize(true);
        recyclerViewTrailer.setAdapter(detailAdapter);
    }

    private void initInstance() {
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        detailAdapter = new DetailAdapter(this, this, results);
    }

    private void getList() {
        detailViewModel.getVideo(result.getId());
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
        recyclerViewTrailer = findViewById(R.id.activity_detail_recycler_trailer);
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

    @Override
    public void onItemClick(ResultVideo result) {
        Intent intentYoutube =
                new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=".concat(result.getKey())));
        startActivity(intentYoutube);
    }
}
