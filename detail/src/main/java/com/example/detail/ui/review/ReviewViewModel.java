package com.example.detail.ui.review;

import android.arch.lifecycle.ViewModel;

import com.example.core.data.model.Resource;
import com.example.core.data.model.review.Review;
import com.example.core.util.SingleLiveEvent;
import com.example.detail.data.source.remote.DetailRepository;

public class ReviewViewModel extends ViewModel {

    public SingleLiveEvent<Resource<Review>> reviewSingleLiveEvent = new SingleLiveEvent<>();
    private DetailRepository detailRepository = new DetailRepository();

    void getReview(double id) {
        detailRepository.getReview(reviewSingleLiveEvent, id);
    }
}
