package com.example.android.moviesawesome.ui.trailer;

import android.arch.lifecycle.ViewModel;

import com.example.android.moviesawesome.data.model.Resource;
import com.example.android.moviesawesome.data.model.video.Video;
import com.example.android.moviesawesome.data.source.remote.detail.DetailRepository;
import com.example.android.moviesawesome.util.SingleLiveEvent;

public class TrailerViewModel extends ViewModel {

    public SingleLiveEvent<Resource<Video>> trailerSingleLiveEvent = new SingleLiveEvent<>();
    private DetailRepository detailRepository = new DetailRepository();

    void getVideo(double id) {
        detailRepository.getVideo(trailerSingleLiveEvent, id);
    }
}
