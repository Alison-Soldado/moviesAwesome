package com.example.android.moviesawesome.ui.review;

import android.arch.lifecycle.ViewModel;

import com.example.android.moviesawesome.data.model.review.Review;
import com.example.android.moviesawesome.data.source.remote.detail.DetailRepository;
import com.example.android.moviesawesome.util.SingleLiveEvent;

public class ReviewViewModel extends ViewModel {

    SingleLiveEvent<Review> reviewSingleLiveEvent = new SingleLiveEvent<>();
    private DetailRepository detailRepository = new DetailRepository();

    void getReview(double id) {
        detailRepository.getReview(reviewSingleLiveEvent, id);
    }
}
