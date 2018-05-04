package br.com.aaascp.androidapp.presentation.movie.details

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.aaascp.androidapp.R
import android.arch.lifecycle.ViewModelProviders
import br.com.aaascp.androidapp.infra.repository.NetworkState
import br.com.aaascp.androidapp.infra.repository.ResourceState
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetails
import br.com.aaascp.androidapp.presentation.util.loadImageFromApi
import kotlinx.android.synthetic.main.activity_movie_details.*

import kotlinx.android.synthetic.main.include_movie_details.*


class MoviesDetailsActivity : AppCompatActivity() {

    private lateinit var model: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_details)

        model = getViewModel()

        init()
    }

    private fun getViewModel(): MovieDetailsViewModel {
        return ViewModelProviders
                .of(this)
                .get(MovieDetailsViewModel::class.java)
    }


    private fun init() {
        model.movieDetails.observe(
                this,
                Observer {
                    if (it != null) {
                        bindDetails(it)
                    } else {
                        bindError()
                    }

                })

        model.state.observe(this, Observer {
            //            showState(it)
        })
    }

    private fun bindDetails(movie: MovieDetails) {
        movie.posterPath?.let {
            loadImageFromApi(moviePoster, it)
        }

        movieDescription.text = movie.overview
        movieReleaseDate.text = movie.releaseDate
        movieTitle.text = movie.title
    }

    private fun bindError() {

    }

//    private fun showState(state: ResourceState?) {
//        when (state) {
//            ResourceState.LOADING -> list.adapter = loadingAdapter
//            ResourceState.EMPTY -> list.adapter = emptyAdapter
//            ResourceState.ERROR -> list.adapter = errorAdapter
//            ResourceState.FILLED -> list.adapter = listAdapter
//        }
//    }
}