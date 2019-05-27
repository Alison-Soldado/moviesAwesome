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
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import br.com.carrefour.delegate.viewProvider

import com.example.core.data.model.movie.Result
import com.example.core.data.source.local.AppDatabase
import com.example.core.ui.FavoriteViewModel
import com.example.core.util.ItemOffsetDecoration
import com.example.core.util.Router
import com.example.main.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics

import java.util.ArrayList

@SuppressLint("Registered")
internal class MainActivity : AppCompatActivity(), MainAdapter.MainAdapterOnItemClickHandler {

    companion object { const val PAGE_START = 1 }

    private val recyclerMain: RecyclerView by viewProvider(R.id.activity_main_recycler_movie)
    private val progressBarMain: ProgressBar by viewProvider(R.id.activity_main_progress_bar)
    private val navigationMain: BottomNavigationView by viewProvider(R.id.activity_main_navigation)
    private val textViewError: TextView by viewProvider(R.id.item_generic_error_text)
    private val adView: AdView by viewProvider(R.id.activity_main_adview)
    private var mainAdapter: MainAdapter? = null
    private var mainViewModel: MainViewModel? = null
    private var favoriteViewModel: FavoriteViewModel? = null
    private val results = ArrayList<Result>()
    private var appDatabase: AppDatabase? = null
    var posterWidth = 500
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initInstance()
        getList(savedInstanceState, PAGE_START)
        setupRecyclerView()
        setupNavigation()
        initObservers()
        loadAdRequest()
        mFirebaseAnalytics?.setCurrentScreen(this, MainActivity::class.java.name, null)
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("results", mainAdapter!!.items as ArrayList<out Parcelable>)
        super.onSaveInstanceState(outState)
    }

    private fun setupNavigation() {
        val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when {
                    item.itemId == R.id.menu_main_movie_popular -> {
                        getList(null, PAGE_START)
                        true
                    }
                    item.itemId == R.id.menu_main_movie_top_rated -> {
                        getListTopRated(PAGE_START)
                        true
                    }
                    item.itemId == R.id.menu_main_movie_my_favorites -> {
                        getMyFavorites()
                        true
                    }
                    else -> false
                }
            }
        navigationMain.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun initInstance() {
        appDatabase = AppDatabase.getInstance(applicationContext)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
        mainAdapter = MainAdapter(this, this, results)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        MobileAds.initialize(this, getString(R.string.id_admob_aplication))
    }

    private fun getList(savedInstanceState: Bundle?, page: Int) {
        if (savedInstanceState != null) {
            mainAdapter?.addItems(savedInstanceState.getParcelableArrayList("results"))
        } else {
            progressBarMain.visibility = View.VISIBLE
            recyclerMain.visibility = View.GONE
            mainViewModel?.getListMovies(page)
        }
    }

    private fun getListTopRated(page: Int) {
        progressBarMain.visibility = View.VISIBLE
        recyclerMain.visibility = View.GONE
        mainViewModel?.getListMoviesTop(page)
    }

    private fun getMyFavorites() {
        favoriteViewModel?.getListFavorites(appDatabase)?.observe(this@MainActivity, Observer { favorite ->
            if (favorite != null) {
                mainAdapter?.addItems(favorite)
                textViewError.visibility = View.GONE
                progressBarMain.visibility = View.GONE
                recyclerMain.visibility = View.VISIBLE
            } else {
                textViewError.visibility = View.VISIBLE
                progressBarMain.visibility = View.GONE
                recyclerMain.visibility = View.GONE
            }
        })
    }

    private fun setupRecyclerView() {
        val gridLayoutManager = GridLayoutManager(this, calculateBestSpanCount(posterWidth))
        recyclerMain.addItemDecoration(ItemOffsetDecoration(this, R.dimen.small_margin))
        recyclerMain.layoutManager = gridLayoutManager
        recyclerMain.setHasFixedSize(true)
        recyclerMain.adapter = mainAdapter
    }

    private fun initObservers() {
        mainViewModel?.movieSingleLiveEvent?.observe(this, Observer { movie ->
            if (movie != null) {
                if (movie.data == null) {
                    textViewError.visibility = View.VISIBLE
                    progressBarMain.visibility = View.GONE
                    recyclerMain.visibility = View.GONE
                } else {
                    mainAdapter?.addItems(movie.data?.results)
                    textViewError.visibility = View.GONE
                    progressBarMain.visibility = View.GONE
                    recyclerMain.visibility = View.VISIBLE
                }
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
                mainAdapter?.filter?.filter(newText)
                mainAdapter?.notifyDataSetChanged()
                return true
            }
        })
    }

    override fun onItemClick(result: Result) {
        bundle.putLong(FirebaseAnalytics.Param.ITEM_ID, result.id)
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, result.title)
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, result.poster_path)
        mFirebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
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
}
