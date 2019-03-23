package com.example.main.data.source.remote;

import com.example.core.data.model.Resource;
import com.example.core.data.model.movie.Movie;
import com.example.core.util.SingleLiveEvent;

public interface DataSourceMainRepository {

    void getMovies(SingleLiveEvent<Resource<Movie>> movieSingleLiveEvent, Integer page);
    void getTopMovies(SingleLiveEvent<Resource<Movie>> movieTopSingleLiveEvent, Integer page);
}
