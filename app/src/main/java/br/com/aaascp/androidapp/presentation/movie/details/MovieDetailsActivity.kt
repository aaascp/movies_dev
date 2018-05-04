package br.com.aaascp.androidapp.presentation.movie.details

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.aaascp.androidapp.R
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.util.Log
import br.com.aaascp.androidapp.infra.repository.NetworkState
import br.com.aaascp.androidapp.infra.repository.ResourceState
import br.com.aaascp.androidapp.infra.source.local.entity.Genre
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetails
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetailsWithGenre
import br.com.aaascp.androidapp.presentation.util.loadImageFromApi
import kotlinx.android.synthetic.main.activity_movie_details.*

import kotlinx.android.synthetic.main.include_movie_details.*


class MoviesDetailsActivity : AppCompatActivity() {

    private lateinit var model: MovieDetailsViewModel

    companion object {
        private const val MOVIE_ID = "MOVIE_ID"

        fun start(context: Context, id: Int) {

            val intent = Intent(context, MoviesDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID, id)

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_details)
        val id = intent.extras.getInt(MOVIE_ID)

        model = getViewModel()

        init(id)
    }

    private fun getViewModel(): MovieDetailsViewModel {
        return ViewModelProviders
                .of(this)
                .get(MovieDetailsViewModel::class.java)
    }

    private fun init(id: Int) {
        model.getDetails(id)

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

    private fun bindDetails(movieWithGenre: MovieDetailsWithGenre) {
        val movie = movieWithGenre.movie

        movie?.let {
            bindMovie(movie)
        }

        bindGenres(movieWithGenre.genres)
    }

    private fun bindMovie(movie: MovieDetails) {
        movie.posterPath?.let {
            loadImageFromApi(moviePoster, it)
        }

        movieDescription.text = movie.overview
        bindReleaseDate(movie.releaseDate)

        movieTitle.text = movie.title
    }

    private fun bindReleaseDate(date: String) {
        val releaseDateFormat = getString(R.string.upcoming_movie_release_date)

        movieReleaseDate.text =
                String.format(releaseDateFormat, date)
    }

    private fun bindGenres(genres: List<Genre>) {
        movieGenres.text = genres.joinToString(", "){it.name}
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