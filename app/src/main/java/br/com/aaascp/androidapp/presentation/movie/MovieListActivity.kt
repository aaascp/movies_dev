package br.com.aaascp.androidapp.presentation.movie

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.aaascp.androidapp.R
import android.arch.lifecycle.ViewModelProviders
import android.view.LayoutInflater
import br.com.aaascp.androidapp.infra.repository.ListState
import br.com.aaascp.androidapp.infra.repository.NetworkState
import br.com.aaascp.androidapp.presentation.SingleRowStaticViewAdapter
import br.com.aaascp.androidapp.presentation.movie.adapter.MovieListAdapter

import kotlinx.android.synthetic.main.activity_movie_list.*


class MovieListActivity : AppCompatActivity() {

    private lateinit var model: MovieListViewModel
    private lateinit var listAdapter: MovieListAdapter
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

    private fun getViewModel(): MovieListViewModel {
        return ViewModelProviders
                .of(this)
                .get(MovieListViewModel::class.java)
    }

    private fun initSwipeToRefresh() {
        model.refreshState.observe(this, Observer {
            swipeRefreshLayout.isRefreshing = it == NetworkState.LOADING
        })

        swipeRefreshLayout.setOnRefreshListener {
            model.refresh()
        }
    }

    private fun initAdapter() {
        listAdapter = MovieListAdapter()

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

        list.adapter = listAdapter

        model.areas.observe(
                this,
                Observer {
                    listAdapter.submitList(it)
                })

        model.listState.observe(this, Observer {
            showState(it)
        })
    }

    private fun showState(state: ListState?) {
        when (state) {
            ListState.LOADING -> list.adapter = loadingAdapter
            ListState.EMPTY -> list.adapter = emptyAdapter
            ListState.ERROR -> list.adapter = errorAdapter
            ListState.FILLED -> list.adapter = listAdapter
        }
    }
}