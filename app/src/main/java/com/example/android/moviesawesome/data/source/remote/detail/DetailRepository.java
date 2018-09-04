package com.example.android.moviesawesome.data.source.remote.detail;

import android.support.annotation.NonNull;

import com.example.android.moviesawesome.data.model.Resource;
import com.example.android.moviesawesome.data.model.review.Review;
import com.example.android.moviesawesome.data.model.video.Video;
import com.example.android.moviesawesome.data.source.remote.ApiUtils;
import com.example.android.moviesawesome.data.source.remote.WebService;
import com.example.android.moviesawesome.util.SingleLiveEvent;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRepository {

    private WebService webService = new ApiUtils().getListMovieService();

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
