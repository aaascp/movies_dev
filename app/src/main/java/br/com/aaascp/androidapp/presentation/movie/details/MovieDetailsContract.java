package br.com.aaascp.androidapp.presentation.movie.details;

import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetails;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetailsWithGenre;

public interface MovieDetailsContract {

    interface Presenter {
        void start();
    }

    interface View {
        void showMovieDetails(MovieDetailsWithGenre movieDetailsWithGenre);

        void showError();

        void showLoading();

        void bindMovie(MovieDetails movieDetails);

        void setPresenter(Presenter presenter);
    }
}
