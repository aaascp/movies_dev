package br.com.aaascp.androidapp.presentation.movie.upcoming;

import android.util.Log;

import java.util.List;

import br.com.aaascp.androidapp.infra.repository.RepositoryCallback;
import br.com.aaascp.androidapp.infra.repository.movie.MovieRepository;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming;

public class UpcomingMoviesPresenter implements UpcomingMoviesContract.Presenter {


    private final UpcomingMoviesContract.View view;
    private final MovieRepository movieRepository;

    UpcomingMoviesPresenter(
            UpcomingMoviesContract.View view,
            MovieRepository movieRepository) {

        this.view = view;
        this.movieRepository = movieRepository;

        view.setPresenter(this);
    }

    @Override
    public void start() {
        Log.d("Andre", "start");
        this.getUpcomingMovies();
    }

    @Override
    public void getUpcomingMovies() {
        Log.d("Andre", "presenter_getUpcomingMovies");
        view.showLoading();

        this.movieRepository.getUpcomingList(
                new RepositoryCallback<List<MovieUpcoming>>() {
                    @Override
                    public void onSuccess(List<MovieUpcoming> moviesUpcomingList) {
                        Log.d("Andre", "onSuccess");
                        showUpcomingMoviesList(moviesUpcomingList);
                    }

                    @Override
                    public void onError(List<String> errors) {
                        Log.d("Andre", "onError");
                        view.showError();
                    }
                });
    }

    private void showUpcomingMoviesList(List<MovieUpcoming> moviesUpcomingList) {
        if(moviesUpcomingList == null) {
            view.showError();
            return;
        }

        if(moviesUpcomingList.isEmpty()) {
            view.showEmptyList();
        } else {
            view.showUpcomingMoviesList(moviesUpcomingList);
        }
    }
}
