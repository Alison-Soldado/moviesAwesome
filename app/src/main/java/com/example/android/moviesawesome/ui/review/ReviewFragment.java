package com.example.android.moviesawesome.ui.review;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviesawesome.R;

public class ReviewFragment extends Fragment {

    private double id;

    public static android.support.v4.app.Fragment newInstance(double id) {
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
            id = getArguments().getDouble("id", 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        return view;
    }
}
