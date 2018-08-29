package com.example.android.moviesawesome.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.moviesawesome.R;
import com.example.android.moviesawesome.data.model.movie.Result;
import com.example.android.moviesawesome.ui.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements MainAdapter.MainAdapterOnItemClickHandler {

    private final int PAGE_START = 1;

    private RecyclerView recyclerMain;
    private ProgressBar progressBarMain;
    private MainAdapter mainAdapter;
    private MainViewModel mainViewModel;
    private List<Result> results = new ArrayList<>();
    private BottomNavigationView navigationMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        initInstance();
        getList();
        setupRecyclerView();
        setupNavigation();
        initObservers();
    }

    private void setupNavigation() {
        navigationMain.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initComponents() {
        recyclerMain = findViewById(R.id.activity_main_recycler_movie);
        progressBarMain = findViewById(R.id.activity_main_progress_bar);
        navigationMain = findViewById(R.id.activity_main_navigation);
    }

    private void initInstance() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainAdapter = new MainAdapter(this, this, results);
    }

    private void getList() {
        progressBarMain.setVisibility(View.VISIBLE);
        recyclerMain.setVisibility(View.GONE);
        mainViewModel.getListMovies(PAGE_START);
    }

    private void getListTopRated() {
        progressBarMain.setVisibility(View.VISIBLE);
        recyclerMain.setVisibility(View.GONE);
        mainViewModel.getListMoviesTop(PAGE_START);
    }

    private void setupRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerMain.setLayoutManager(gridLayoutManager);
        recyclerMain.setHasFixedSize(true);
        recyclerMain.setAdapter(mainAdapter);
    }

    private void initObservers() {
        mainViewModel.movieSingleLiveEvent.observe(this, movie -> {
            if (movie != null) {
                mainAdapter.addItems(movie.getResults());
                progressBarMain.setVisibility(View.GONE);
                recyclerMain.setVisibility(View.VISIBLE);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.menu_main_movie_popular:
                        getList();
                        return true;
                    case R.id.menu_main_movie_top_rated:
                        getListTopRated();
                        return true;
                    case R.id.menu_main_movie_my_favorites:
                        return true;
                }
                return false;
            };

    @Override
    public void onItemClick(Result result) {
        Intent intentDetail = new Intent(MainActivity.this, DetailActivity.class);
        intentDetail.putExtra("result", result);
        startActivity(intentDetail);
    }
}
