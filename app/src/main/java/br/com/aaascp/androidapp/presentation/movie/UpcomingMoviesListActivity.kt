package br.com.aaascp.androidapp.presentation.movie

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.aaascp.androidapp.R
import android.arch.lifecycle.ViewModelProviders
import android.util.Log
import br.com.aaascp.androidapp.presentation.movie.adapter.UpcomingMoviesListAdapter

import kotlinx.android.synthetic.main.activity_movie_list.*


class UpcomingMoviesListActivity : AppCompatActivity() {

    private lateinit var modelUpcoming: UpcomingMoviesListViewModel
    private lateinit var listAdapter: UpcomingMoviesListAdapter
//    private lateinit var errorAdapter: SingleRowStaticViewAdapter
//    private lateinit var loadingAdapter: SingleRowStaticViewAdapter
//    private lateinit var emptyAdapter: SingleRowStaticViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_list)

        modelUpcoming = getViewModel()

//        initSwipeToRefresh()
        initAdapter()
    }

    private fun getViewModel(): UpcomingMoviesListViewModel {
        return ViewModelProviders
                .of(this)
                .get(UpcomingMoviesListViewModel::class.java)
    }

//    private fun initSwipeToRefresh() {
//        modelUpcoming.refreshState.observe(this, Observer {
//            swipeRefreshLayout.isRefreshing = it == NetworkState.LOADING
//        })
//
//        swipeRefreshLayout.setOnRefreshListener {
//            modelUpcoming.refresh()
//        }
//    }

    private fun initAdapter() {
//        listAdapter = UpcomingMoviesListAdapter()
//
//        errorAdapter =
//                SingleRowStaticViewAdapter(
//                        R.layout.row_items_error,
//                        LayoutInflater.from(this))
//
//        loadingAdapter =
//                SingleRowStaticViewAdapter(
//                        R.layout.row_items_loading,
//                        LayoutInflater.from(this))
//
//        emptyAdapter =
//                SingleRowStaticViewAdapter(
//                        R.layout.row_items_empty,
//                        LayoutInflater.from(this))

//        list.adapter = listAdapter

        modelUpcoming.upcomingMoviesList.observe(
                this,
                Observer {
                    list.adapter = UpcomingMoviesListAdapter(it!!)
                })

//        modelUpcoming.listState.observe(this, Observer {
//            showState(it)
//        })
    }

//    private fun showState(state: ListState?) {
//        when (state) {
//            ListState.LOADING -> list.adapter = loadingAdapter
//            ListState.EMPTY -> list.adapter = emptyAdapter
//            ListState.ERROR -> list.adapter = errorAdapter
//            ListState.FILLED -> list.adapter = listAdapter
//        }
//    }
}