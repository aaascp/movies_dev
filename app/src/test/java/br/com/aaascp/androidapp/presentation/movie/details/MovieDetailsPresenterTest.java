package br.com.aaascp.androidapp.presentation.movie.details;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import br.com.aaascp.androidapp.infra.repository.RepositoryCallback;
import br.com.aaascp.androidapp.infra.repository.movie.MovieRepository;
import br.com.aaascp.androidapp.infra.source.local.entity.Genre;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetails;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetailsWithGenre;
import br.com.aaascp.androidapp.presentation.util.ListUtils;

import static org.mockito.Mockito.verify;

public class MovieDetailsPresenterTest {

    private static final int MOVIE_ID = 1;

    private static final MovieDetails MOVIE_DETAILS =
            new MovieDetails(
                    MOVIE_ID,
                    "Movie",
                    "Overview",
                    "posterPath",
                    "27/11/1990");

    private static final List<Genre> GENRE_LIST = Arrays.asList(
            new Genre(1,
                    "GENRE 1",
                    MOVIE_ID),
            new Genre(2,
                    "GENRE 2",
                    MOVIE_ID));

    private static final MovieDetailsWithGenre MOVIE =
            new MovieDetailsWithGenre(
                    GENRE_LIST,
                    MOVIE_DETAILS);

    @Mock
    private MovieDetailsContract.View view;

    @Mock
    private MovieRepository movieRepository;

    @Captor
    private ArgumentCaptor<RepositoryCallback<MovieDetailsWithGenre>>
            repositoryCallbackArgumentCaptor;

    private MovieDetailsPresenter movieDetailsPresenter;

    @Before
    public void setupOrdersListPresenter() {
        MockitoAnnotations.initMocks(this);

        movieDetailsPresenter = new MovieDetailsPresenter(view, MOVIE_ID, movieRepository);
    }

    @Test
    public void onConstruction_setPresenter() {
        verify(view).setPresenter(movieDetailsPresenter);
    }

    @Test
    public void onStart_onSuccess_showList() {
        movieDetailsPresenter.start();

        callbackSuccess(MOVIE);

        verify(view).showLoading();
        verify(view).showMovieDetails(MOVIE.getMovie());

        String genres = ListUtils.join(
                Arrays.asList(
                        GENRE_LIST.get(0).getName(),
                        GENRE_LIST.get(1).getName()));

        verify(view).showMovieGenres(genres);
    }

    @Test
    public void onStart_onSuccess_nullList_showError() {
        movieDetailsPresenter.start();

        callbackSuccess(null);

        verify(view).showLoading();
        verify(view).showError();
    }

    @Test
    public void onStart_onError_showError() {
        movieDetailsPresenter.start();

        callbackError();

        verify(view).showLoading();
        verify(view).showError();
    }

    private void callbackSuccess(MovieDetailsWithGenre movieDetailsWithGenre) {
        verify(movieRepository).getMovieDetails(
                MOVIE.getMovie().getId(),
                repositoryCallbackArgumentCaptor.capture());

        repositoryCallbackArgumentCaptor
                .getValue()
                .onSuccess(movieDetailsWithGenre);
    }

    private void callbackError() {
        verify(movieRepository).getMovieDetails(
                MOVIE.getMovie().getId(),
                repositoryCallbackArgumentCaptor.capture());

        repositoryCallbackArgumentCaptor
                .getValue()
                .onError(null);
    }
}