package com.example.android.moviesawesome.data.source.remote.detail;

import android.support.annotation.NonNull;

import com.example.android.moviesawesome.data.model.movie.Movie;
import com.example.android.moviesawesome.data.model.review.Review;
import com.example.android.moviesawesome.data.model.video.Video;
import com.example.android.moviesawesome.data.source.remote.ApiUtils;
import com.example.android.moviesawesome.data.source.remote.WebService;
import com.example.android.moviesawesome.util.SingleLiveEvent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRepository {

    private WebService webService = new ApiUtils().getListMovieService();

    public void getVideo(SingleLiveEvent<Video> detailSingleLiveEvent, double id) {
        webService.getVideo(id).enqueue(new Callback<Video>() {
            @Override
            public void onResponse(@NonNull Call<Video> call, @NonNull Response<Video> response) {
                detailSingleLiveEvent.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Video> call, @NonNull Throwable t) {
                // TODO: Desenvolver caso de erro na lista de filmes
            }
        });
    }

    public void getReview(SingleLiveEvent<Review> detailReviewSingleLiveEvent, double id) {
        webService.getReview(id).enqueue(new Callback<Review>() {
            @Override
            public void onResponse(@NonNull Call<Review> call, @NonNull Response<Review> response) {
                detailReviewSingleLiveEvent.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Review> call, @NonNull Throwable t) {
                // TODO: Desenvolver caso de erro na lista de filmes
            }
        });
    }
}
