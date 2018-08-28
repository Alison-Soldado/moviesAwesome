package com.example.android.moviesawesome.ui.detail;

import android.arch.lifecycle.ViewModel;

import com.example.android.moviesawesome.data.model.review.Review;
import com.example.android.moviesawesome.data.model.video.Video;
import com.example.android.moviesawesome.data.source.remote.detail.DetailRepository;
import com.example.android.moviesawesome.util.SingleLiveEvent;

public class DetailViewModel extends ViewModel {

    SingleLiveEvent<Video> detailSingleLiveEvent = new SingleLiveEvent<>();
    SingleLiveEvent<Review> detailReviewSingleLiveEvent = new SingleLiveEvent<>();
    private DetailRepository detailRepository = new DetailRepository();

    void getVideo(double id) {
        detailRepository.getVideo(detailSingleLiveEvent, id);
    }

    void getReview(double id) {
        detailRepository.getReview(detailReviewSingleLiveEvent, id);
    }
}
