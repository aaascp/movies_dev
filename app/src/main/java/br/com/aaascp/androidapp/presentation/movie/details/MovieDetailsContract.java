package br.com.aaascp.androidapp.presentation.movie.details;

import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetails;

public interface MovieDetailsContract {

    interface Presenter {
        void start();
    }

    interface View {
        void showMovieDetails(MovieDetails movieDetailsWithGenre);

        void showMovieGenres(String genres);

        void showError();

        void showLoading();

        void setPresenter(Presenter presenter);
    }
}
