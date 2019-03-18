package com.example.main.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.core.data.model.movie.Result;
import com.example.core.data.source.local.AppDatabase;
import com.example.core.ui.FavoriteViewModel;
import com.example.core.util.ItemOffsetDecoration;
import com.example.core.util.Router;
import com.example.main.R;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("Registered")
public class MainActivity extends AppCompatActivity
        implements MainAdapter.MainAdapterOnItemClickHandler {

    private final int PAGE_START = 1;

    private RecyclerView recyclerMain;
    private ProgressBar progressBarMain;
    private MainAdapter mainAdapter;
    private MainViewModel mainViewModel;
    private FavoriteViewModel favoriteViewModel;
    private TextView textViewError;
    private List<Result> results = new ArrayList<>();
    private BottomNavigationView navigationMain;
    private AppDatabase appDatabase;
    int posterWidth = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        initInstance();
        getList(savedInstanceState, PAGE_START);
        setupRecyclerView();
        setupNavigation();
        initObservers();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("results", (ArrayList<? extends Parcelable>) mainAdapter.getItems());
        super.onSaveInstanceState(outState);
    }

    private void setupNavigation() {
        navigationMain.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initComponents() {
        recyclerMain = findViewById(R.id.activity_main_recycler_movie);
        progressBarMain = findViewById(R.id.activity_main_progress_bar);
        navigationMain = findViewById(R.id.activity_main_navigation);
        textViewError = findViewById(R.id.item_generic_error_text);
    }

    private void initInstance() {
        appDatabase = AppDatabase.getInstance(getApplicationContext());
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        mainAdapter = new MainAdapter(this, this, results);
    }

    private void getList(Bundle savedInstanceState, int page) {
        if (savedInstanceState != null) {
            mainAdapter.addItems(savedInstanceState.getParcelableArrayList("results"));
        } else {
            progressBarMain.setVisibility(View.VISIBLE);
            recyclerMain.setVisibility(View.GONE);
            mainViewModel.getListMovies(page);
        }
    }

    private void getListTopRated(int page) {
        progressBarMain.setVisibility(View.VISIBLE);
        recyclerMain.setVisibility(View.GONE);
        mainViewModel.getListMoviesTop(page);
    }

    private void getMyFavorites() {
        favoriteViewModel.getListFavorites(appDatabase).observe(this, favorite -> {
            if (favorite != null) {
                mainAdapter.addItems(favorite);
                textViewError.setVisibility(View.GONE);
                progressBarMain.setVisibility(View.GONE);
                recyclerMain.setVisibility(View.VISIBLE);
            } else {
                textViewError.setVisibility(View.VISIBLE);
                progressBarMain.setVisibility(View.GONE);
                recyclerMain.setVisibility(View.GONE);
            }
        });
    }

    private void setupRecyclerView() {
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, calculateBestSpanCount(posterWidth));
        recyclerMain.addItemDecoration(new ItemOffsetDecoration(this, R.dimen.small_margin));
        recyclerMain.setLayoutManager(gridLayoutManager);
        recyclerMain.setHasFixedSize(true);
        recyclerMain.setAdapter(mainAdapter);
    }

    private void initObservers() {
        mainViewModel.movieSingleLiveEvent.observe(this, movie -> {
            if (movie != null) {
                if (movie.data == null) {
                    textViewError.setVisibility(View.VISIBLE);
                    progressBarMain.setVisibility(View.GONE);
                    recyclerMain.setVisibility(View.GONE);
                } else {
                    mainAdapter.addItems(movie.data.getResults());
                    textViewError.setVisibility(View.GONE);
                    progressBarMain.setVisibility(View.GONE);
                    recyclerMain.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        if (item.getItemId() == R.id.menu_main_movie_popular) {
            getList(null, PAGE_START);
            return true;
        } else if (item.getItemId() == R.id.menu_main_movie_top_rated) {
            getListTopRated(PAGE_START);
            return true;
        } else if (item.getItemId() == R.id.menu_main_movie_my_favorites) {
            getMyFavorites();
            return true;
        }
        return false;
    };

    private int calculateBestSpanCount(int posterWidth) {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float screenWidth = outMetrics.widthPixels;
        return Math.round(screenWidth / posterWidth);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_search, menu);
        MenuItem search = menu.findItem(R.id.menu_main_search_item);
        SearchView searchView = (SearchView) search.getActionView();
        searchTitleRepository(searchView);
        return true;
    }

    private void searchTitleRepository(SearchView search) {
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mainAdapter.getFilter().filter(newText);
                mainAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public void onItemClick(Result result) {
        startActivity(Router.provideToDetailIntent(result));
    }
}
