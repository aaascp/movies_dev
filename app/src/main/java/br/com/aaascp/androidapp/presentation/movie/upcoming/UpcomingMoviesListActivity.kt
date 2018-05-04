package br.com.aaascp.androidapp.presentation.movie.upcoming

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.aaascp.androidapp.R
import android.arch.lifecycle.ViewModelProviders
import android.view.LayoutInflater
import br.com.aaascp.androidapp.infra.repository.NetworkState
import br.com.aaascp.androidapp.infra.repository.ResourceState
import br.com.aaascp.androidapp.presentation.SingleRowStaticViewAdapter
import br.com.aaascp.androidapp.presentation.movie.upcoming.adapter.UpcomingMoviesListAdapter

import kotlinx.android.synthetic.main.activity_movie_list.*


class UpcomingMoviesListActivity : AppCompatActivity() {

    private lateinit var model: UpcomingMoviesListViewModel
    private lateinit var listAdapter: UpcomingMoviesListAdapter
    private lateinit var errorAdapter: SingleRowStaticViewAdapter
    private lateinit var loadingAdapter: SingleRowStaticViewAdapter
    private lateinit var emptyAdapter: SingleRowStaticViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_list)

        model = getViewModel()

        initSwipeToRefresh()
        initAdapter()
    }

    private fun getViewModel(): UpcomingMoviesListViewModel {
        return ViewModelProviders
                .of(this)
                .get(UpcomingMoviesListViewModel::class.java)
    }

    private fun initSwipeToRefresh() {
        model.networkState.observe(this, Observer {
            swipeRefreshLayout.isRefreshing = it == NetworkState.LOADING
        })

        swipeRefreshLayout.setOnRefreshListener {
            model.getItems()
        }
    }

    private fun initAdapter() {
        listAdapter = UpcomingMoviesListAdapter()

        errorAdapter =
                SingleRowStaticViewAdapter(
                        R.layout.row_items_error,
                        LayoutInflater.from(this))

        loadingAdapter =
                SingleRowStaticViewAdapter(
                        R.layout.row_items_loading,
                        LayoutInflater.from(this))

        emptyAdapter =
                SingleRowStaticViewAdapter(
                        R.layout.row_items_empty,
                        LayoutInflater.from(this))

        model.upcomingMoviesList.observe(
                this,
                Observer {
                    listAdapter.setItems(it)

                })

        model.state.observe(this, Observer {
            showState(it)
        })
    }

    private fun showState(state: ResourceState?) {
        when (state) {
            ResourceState.LOADING -> list.adapter = loadingAdapter
            ResourceState.EMPTY -> list.adapter = emptyAdapter
            ResourceState.ERROR -> list.adapter = errorAdapter
            ResourceState.FILLED -> list.adapter = listAdapter
        }
    }
}