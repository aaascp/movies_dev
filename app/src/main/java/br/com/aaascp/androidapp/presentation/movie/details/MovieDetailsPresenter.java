package br.com.aaascp.androidapp.presentation.movie.details;

import java.util.ArrayList;
import java.util.List;

import br.com.aaascp.androidapp.infra.repository.RepositoryCallback;
import br.com.aaascp.androidapp.infra.repository.movie.MovieRepository;
import br.com.aaascp.androidapp.infra.source.local.entity.Genre;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetailsWithGenre;
import br.com.aaascp.androidapp.presentation.util.ListUtils;

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private final MovieDetailsContract.View view;
    private final int movieId;
    private final MovieRepository movieRepository;

    public MovieDetailsPresenter(
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
                        view.showMovieDetails(movieDetailsWithGenre.getMovie());
                        view.showMovieGenres(
                                ListUtils.join(
                                        getGenresNames(
                                                movieDetailsWithGenre.getGenres())));
                    }

                    @Override
                    public void onError(List<String> errors) {
                        view.showError();
                    }
                });
    }

    private List<String> getGenresNames(List<Genre> genreList) {
        List<String> genresNames = new ArrayList<>(genreList.size());

        for (Genre genre : genreList) {
            genresNames.add(genre.getName());
        }

        return genresNames;
    }
}
