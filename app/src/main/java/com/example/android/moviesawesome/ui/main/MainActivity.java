package com.example.android.moviesawesome.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.moviesawesome.R;
import com.example.android.moviesawesome.data.model.Result;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.MainAdapterOnItemClickHandler {

    private final int PAGE_START = 1;
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
        setupRecyclerView();

        mainViewModel.movieMutableLiveData.observe(this, movie -> {
            if (movie != null) {
                //TODO: Preencher recyclerview com os dados do retorno
            }
        });

        Result result = new Result(6105, 244786, false, 454, "Whiplash", 27.264, "/c9XxwwhPHdaImA2f1WEfEsbhaFB.jpg", "en", "Whiplash", "/6bbZ6XyvgfjhQwbplnUh1LSj1ky.jpg", false, "Under the direction of a ruthless inst", "2014");
        results.add(0, result);
        mainAdapter = new MainAdapter(this, this, results);
        recyclerMain.setAdapter(mainAdapter);
    }

    private void initComponents() {
        recyclerMain = findViewById(R.id.activity_main_recycler_movie);
        progressBarMain = findViewById(R.id.activity_main_progress_bar);
    }

    private void initInstance() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.initMovie(PAGE_START);
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerMain.setLayoutManager(layoutManager);
        recyclerMain.setHasFixedSize(true);
    }

    @Override
    public void onItemClick() {
        Toast.makeText(this, "clicou", Toast.LENGTH_SHORT).show();
    }
}
