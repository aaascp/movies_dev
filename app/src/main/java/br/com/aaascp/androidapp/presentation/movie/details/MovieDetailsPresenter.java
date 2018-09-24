package br.com.aaascp.androidapp.presentation.movie.details;

import java.util.List;

import br.com.aaascp.androidapp.infra.repository.RepositoryCallback;
import br.com.aaascp.androidapp.infra.repository.movie.MovieRepository;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetailsWithGenre;

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private final MovieDetailsContract.View view;
    private final int movieId;
    private final MovieRepository movieRepository;

    MovieDetailsPresenter(
            MovieDetailsContract.View view,
            int movieId,
            MovieRepository movieRepository) {
        this.view = view;
        this.movieId = movieId;
        this.movieRepository = movieRepository;


        view.setPresenter(this);
    }

    @Override
    public void start() {
        this.getMovieDetails();
    }

    private void getMovieDetails() {
        this.movieRepository.getMovieDetails(
                movieId,
                new RepositoryCallback<MovieDetailsWithGenre>() {
                    @Override
                    public void onSuccess(MovieDetailsWithGenre movieDetailsWithGenre) {
                        view.showMovieDetails(movieDetailsWithGenre);
                    }

                    @Override
                    public void onError(List<String> errors) {
                        view.showError();
                    }
                });
    }
}
