package com.example.main.ui

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.Menu
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import com.example.core.data.model.movie.Result
import com.example.core.data.source.local.AppDatabase
import com.example.core.delegate.viewProvider
import com.example.core.statemachine.ViewStateMachine
import com.example.core.ui.FavoriteViewModel
import com.example.core.util.ItemOffsetDecoration
import com.example.core.util.Router
import com.example.main.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import java.util.*

@SuppressLint("Registered")
internal class MainActivity : AppCompatActivity(), MainAdapter.MainAdapterOnItemClickHandler {

    private val viewStateMachine by lazy { ViewStateMachine() }
    private val recyclerMain: RecyclerView by viewProvider(R.id.activity_main_recycler_movie)
    private val progressBarMain: ProgressBar by viewProvider(R.id.activity_main_progress_bar)
    private val navigationMain: BottomNavigationView by viewProvider(R.id.activity_main_navigation)
    private val textViewError: TextView by viewProvider(R.id.item_generic_error_text)
    private val adView: AdView by viewProvider(R.id.activity_main_adview)
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var firebaseAnalytics: FirebaseAnalytics? = null
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainAdapter: MainAdapter
    private lateinit var appDatabase: AppDatabase
    private val results = ArrayList<Result>()
    private val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupState(savedInstanceState)
        initInstance()
        getList(savedInstanceState, PAGE_START)
        setupRecyclerView()
        setupNavigation()
        initObservers()
        loadAdRequest()
        firebaseAnalytics?.setCurrentScreen(this, MainActivity::class.java.name, null)
    }

    private fun setupState(savedInstanceState: Bundle?) {
        viewStateMachine.setup(LOAD_STATE, savedInstanceState) {
            add(LOAD_STATE) {
                visibles(progressBarMain)
                gones(recyclerMain, textViewError)
            }

            add(SUCCESS_STATE) {
                visibles(recyclerMain)
                gones(progressBarMain, textViewError)
            }

            add(ERROR_STATE) {
                visibles(textViewError)
                gones(recyclerMain, progressBarMain)
            }
        }
    }

    private fun initInstance() {
        appDatabase = AppDatabase.getInstance(applicationContext)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
        mainAdapter = MainAdapter(this, this, results)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        MobileAds.initialize(this, getString(R.string.id_admob_aplication))
    }

    private fun getList(savedInstanceState: Bundle?, page: Int) {
        if (savedInstanceState != null) {
            mainAdapter.addItems(savedInstanceState.getParcelableArrayList("results"))
        } else {
            viewStateMachine.changeState(LOAD_STATE)
            mainViewModel.getListMovies(page)
        }
    }

    private fun setupRecyclerView() {
        val gridLayoutManager = GridLayoutManager(this, calculateBestSpanCount(POSTER_WIDTH))
        recyclerMain.apply {
            addItemDecoration(ItemOffsetDecoration(this@MainActivity, R.dimen.small_margin))
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = mainAdapter
        }
    }

    private fun setupNavigation() {
        val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when(item.itemId) {
                    R.id.menu_main_movie_popular -> {
                        getList(null, PAGE_START)
                        true
                    }
                    R.id.menu_main_movie_top_rated -> {
                        getListTopRated(PAGE_START)
                        true
                    }
                    R.id.menu_main_movie_my_favorites -> {
                        getMyFavorites()
                        true
                    }
                    else -> false
                }
            }
        navigationMain.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun initObservers() {
        mainViewModel.movieSingleLiveEvent?.observe(this, Observer { movie ->
            if (movie != null) {
                if (movie.data == null) {
                    viewStateMachine.changeState(ERROR_STATE)
                } else {
                    mainAdapter.addItems(movie.data?.results!!)
                    viewStateMachine.changeState(SUCCESS_STATE)
                }
            }
        })
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("results", mainAdapter.items as ArrayList<out Parcelable>)
        super.onSaveInstanceState(outState)
    }

    private fun getListTopRated(page: Int) {
        viewStateMachine.changeState(LOAD_STATE)
        mainViewModel.getListMoviesTop(page)
    }

    private fun getMyFavorites() {
        favoriteViewModel.getListFavorites(appDatabase)?.observe(this@MainActivity, Observer { favorite ->
            if (favorite != null) {
                mainAdapter.addItems(favorite)
                viewStateMachine.changeState(SUCCESS_STATE)
            } else {
                viewStateMachine.changeState(ERROR_STATE)
            }
        })
    }

    private fun calculateBestSpanCount(posterWidth: Int): Int {
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val screenWidth = outMetrics.widthPixels.toFloat()
        return Math.round(screenWidth / posterWidth)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_search, menu)
        val search = menu.findItem(R.id.menu_main_search_item)
        val searchView = search.actionView as SearchView
        searchTitleRepository(searchView)
        return true
    }

    private fun searchTitleRepository(search: SearchView) {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mainAdapter.filter?.filter(newText)
                mainAdapter.notifyDataSetChanged()
                return true
            }
        })
    }

    override fun onItemClick(result: Result) {
        bundle.putLong(FirebaseAnalytics.Param.ITEM_ID, result.id)
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, result.title)
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, result.poster_path)
        firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
        startActivity(Router.provideToDetailIntent(result))
    }

    override fun onPause() {
        adView.resume()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        adView.resume()
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }

    private fun loadAdRequest() {
        val adRequest = AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .build()
        adView.loadAd(adRequest)
    }

    companion object {
        const val PAGE_START = 1
        const val LOAD_STATE = 0
        const val SUCCESS_STATE = 1
        const val ERROR_STATE = 2
        const val POSTER_WIDTH = 500
    }
}
