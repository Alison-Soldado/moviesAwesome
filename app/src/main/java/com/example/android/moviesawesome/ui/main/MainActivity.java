package com.example.android.moviesawesome.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.example.android.moviesawesome.R;
import com.example.android.moviesawesome.data.model.Result;
import com.example.android.moviesawesome.ui.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.MainAdapterOnItemClickHandler {

    private final int PAGE_START = 1;
    private final int NUMBER_COLUMNS = 2;

    private RecyclerView recyclerMain;
    private ProgressBar progressBarMain;
    private MainAdapter mainAdapter;
    private MainViewModel mainViewModel;
    private List<Result> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        initInstance();
        getList();
        setupRecyclerView();
        initObservers();
    }

    private void initComponents() {
        recyclerMain = findViewById(R.id.activity_main_recycler_movie);
        progressBarMain = findViewById(R.id.activity_main_progress_bar);
    }

    private void initInstance() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainAdapter = new MainAdapter(this, this, results);
    }

    private void getList() {
        mainViewModel.getListMovies(PAGE_START);
    }

    private void setupRecyclerView() {
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, NUMBER_COLUMNS);
        recyclerMain.setLayoutManager(gridLayoutManager);
        recyclerMain.setHasFixedSize(true);
        recyclerMain.setAdapter(mainAdapter);
    }

    private void initObservers() {
        mainViewModel.movieMutableLiveData.observe(this, movie -> {
            if (movie != null) {
                mainAdapter.addItems(movie.getResults());
            }
        });
    }

    @Override
    public void onItemClick(Result result) {
        Intent intentDetail = new Intent(MainActivity.this, DetailActivity.class);
        intentDetail.putExtra("result", result);
        startActivity(intentDetail);
    }
}