package com.example.android.moviesawesome.ui.review;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviesawesome.R;
import com.example.android.moviesawesome.data.model.review.ResultReview;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReviewFragment extends Fragment
        implements ReviewAdapter.ReviewAdapterOnItemClickHandler {

    private double id;
    private ReviewViewModel reviewViewModel;
    private ReviewAdapter reviewAdapter;
    private RecyclerView recyclerViewReview;
    private List<ResultReview> results = new ArrayList<>();

    public static ReviewFragment newInstance(double id) {
        ReviewFragment reviewFragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putDouble("id", id);
        reviewFragment.setArguments(args);
        return reviewFragment;
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
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        initComponents(view);
        initInstance();
        getList();
        setupRecyclerView();
        initObservers();
        return view;
    }

    private void initComponents(View view) {
        recyclerViewReview = view.findViewById(R.id.fragment_review_recycler);
    }

    private void initObservers() {
        reviewViewModel.reviewSingleLiveEvent.observe(this, review -> {
            if (review != null) {
                reviewAdapter.addItems(review.getResults());
            }
        });
    }

    private void initInstance() {
        reviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        if (getContext() != null) {
            reviewAdapter = new ReviewAdapter(getContext(), this, results);
        }
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewReview.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration =
                new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        Drawable drawableWhite = ContextCompat.getDrawable(getContext(), R.color.colorDivider);
        itemDecoration.setDrawable(Objects.requireNonNull(drawableWhite));
        recyclerViewReview.addItemDecoration(itemDecoration);
        recyclerViewReview.setHasFixedSize(true);
        recyclerViewReview.setAdapter(reviewAdapter);
    }

    private void getList() {
        reviewViewModel.getReview(id);
    }

    @Override
    public void onItemClick(ResultReview result) {
        Intent intentReview = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getUrl()));
        startActivity(intentReview);
    }
}
