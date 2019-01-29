package com.example.detail.data.source.remote;


import com.example.core.BuildConfig;
import com.example.core.data.model.movie.Movie;
import com.example.core.data.model.review.Review;
import com.example.core.data.model.video.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface WebServiceDetail {

    String API_KEY = BuildConfig.API_KEY_MOVIE_DB;

    @GET("{id}/videos?api_key=" + API_KEY)
    Call<Video> getVideo(@Path("id") double id);

    @GET("{id}/reviews?api_key=" + API_KEY)
    Call<Review> getReview(@Path("id") double id);
}
