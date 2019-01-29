package com.example.detail.ui.trailer;

import android.arch.lifecycle.ViewModel;

import com.example.core.data.model.Resource;
import com.example.core.data.model.video.Video;
import com.example.core.util.SingleLiveEvent;
import com.example.detail.data.source.remote.DetailRepository;

public class TrailerViewModel extends ViewModel {

    public SingleLiveEvent<Resource<Video>> trailerSingleLiveEvent = new SingleLiveEvent<>();
    private DetailRepository detailRepository = new DetailRepository();

    void getVideo(double id) {
        detailRepository.getVideo(trailerSingleLiveEvent, id);
    }
}
