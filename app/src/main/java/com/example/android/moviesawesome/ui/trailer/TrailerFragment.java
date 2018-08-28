package com.example.android.moviesawesome.ui.trailer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviesawesome.R;
import com.example.android.moviesawesome.data.model.video.ResultVideo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TrailerFragment extends Fragment
        implements TrailerAdapter.TrailerAdapterOnItemClickHandler {

    private double id;
    private TrailerViewModel trailerViewModel;
    private TrailerAdapter trailerAdapter;
    private RecyclerView recyclerViewTrailer;
    private List<ResultVideo> results = new ArrayList<>();

    public static TrailerFragment newInstance(double id) {
        TrailerFragment trailerFragment = new TrailerFragment();
        Bundle args = new Bundle();
        args.putDouble("id", id);
        trailerFragment.setArguments(args);
        return trailerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getDouble("id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trailer, container, false);
        initComponents(view);
        initInstance();
        getList();
        setupRecyclerView();
        initObservers();
        return view;
    }

    private void initComponents(View view) {
        recyclerViewTrailer = view.findViewById(R.id.fragment_trailer_recycler);
    }

    private void initObservers() {
        trailerViewModel.trailerSingleLiveEvent.observe(this, video -> {
            if (video != null) {
                trailerAdapter.addItems(video.getResults());
            }
        });
    }

    private void initInstance() {
        trailerViewModel = ViewModelProviders.of(this).get(TrailerViewModel.class);
        if (getContext() != null) {
            trailerAdapter = new TrailerAdapter(getContext(), this, results);
        }
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewTrailer.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        recyclerViewTrailer.addItemDecoration(itemDecoration);
        recyclerViewTrailer.setHasFixedSize(true);
        recyclerViewTrailer.setAdapter(trailerAdapter);
    }

    private void getList() {
        trailerViewModel.getVideo(id);
    }

    @Override
    public void onItemClick(ResultVideo result) {
        Intent intentYoutube =
                new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=".concat(result.getKey())));
        startActivity(intentYoutube);
    }
}
