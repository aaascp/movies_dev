package br.com.aaascp.androidapp.presentation.movie.upcoming;

import java.util.List;

import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming;

public class UpcomingMoviesContract {

    interface Presenter {
        void start();

        void getUpcomingMovies();
    }

    interface View {

        void showUpcomingMoviesList(List<MovieUpcoming> movieUpcomingList);

        void showError();

        void showLoading();

        void showEmptyList();

        void setPresenter(Presenter presenter);
    }
}
