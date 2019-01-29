package com.example.detail.data.source.remote;

import android.support.annotation.NonNull;

import com.example.core.data.model.Resource;
import com.example.core.data.model.review.Review;
import com.example.core.data.model.video.Video;
import com.example.core.util.SingleLiveEvent;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRepository {

    private WebServiceDetail webService = new ApiUtilsDetail().getList();

    public void getVideo(SingleLiveEvent<Resource<Video>> detailSingleLiveEvent, double id) {
        webService.getVideo(id).enqueue(new Callback<Video>() {
            @Override
            public void onResponse(@NonNull Call<Video> call, @NonNull Response<Video> response) {
                detailSingleLiveEvent.setValue(Resource.success(Objects.requireNonNull(response.body())));
            }

            @Override
            public void onFailure(@NonNull Call<Video> call, @NonNull Throwable t) {
                detailSingleLiveEvent.setValue(Resource.error(t.getMessage(), null));
            }
        });
    }

    public void getReview(SingleLiveEvent<Resource<Review>> detailReviewSingleLiveEvent, double id) {
        webService.getReview(id).enqueue(new Callback<Review>() {
            @Override
            public void onResponse(@NonNull Call<Review> call, @NonNull Response<Review> response) {
                detailReviewSingleLiveEvent.setValue(Resource.success(Objects.requireNonNull(response.body())));
            }

            @Override
            public void onFailure(@NonNull Call<Review> call, @NonNull Throwable t) {
                detailReviewSingleLiveEvent.setValue(Resource.error(t.getMessage(), null));
            }
        });
    }
}
