package com.example.main;

import android.support.test.filters.SmallTest;

import com.example.core.data.model.Resource;
import com.example.core.data.model.movie.Movie;
import com.example.core.util.SingleLiveEvent;
import com.example.main.data.source.remote.MainRepository;
import com.example.main.ui.MainViewModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@SmallTest
@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {

    @Mock
    private MainRepository mainRepository = new MainRepository();
    private MainViewModel mainViewModel = new MainViewModel();
    private SingleLiveEvent<Resource<Movie>> movieSingleLiveEvent = new SingleLiveEvent<>();

    @Test
    public void givenLoadDisplay_WhenRequestList_ThenVerifyRequest() {
        mainViewModel.getListMovies(1);
        verify(mainRepository).getMovies(movieSingleLiveEvent, 1);
    }
}
