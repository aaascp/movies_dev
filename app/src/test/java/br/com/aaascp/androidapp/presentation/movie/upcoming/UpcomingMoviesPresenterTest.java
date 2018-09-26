package br.com.aaascp.androidapp.presentation.movie.upcoming;


import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.aaascp.androidapp.infra.repository.RepositoryCallback;
import br.com.aaascp.androidapp.infra.repository.movie.MovieRepository;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming;

import static org.mockito.Mockito.verify;

public class UpcomingMoviesPresenterTest {

    private static final MovieUpcoming FIRST_MOVIE = new MovieUpcoming(1, "Filme 1", "poster_1", "17/11/1990");
    private static final MovieUpcoming SECOND_MOVIE = new MovieUpcoming(2, "Filme 2", "poster_2", "27/11/1990");

    @Mock
    private UpcomingMoviesContract.View view;

    @Mock
    private MovieRepository movieRepository;

    @Captor
    private ArgumentCaptor<RepositoryCallback<List<MovieUpcoming>>>
            repositoryCallbackArgumentCaptor;

    private UpcomingMoviesPresenter upcomingMoviesPresenter;

    @Before
    public void setupOrdersListPresenter() {
        MockitoAnnotations.initMocks(this);

        upcomingMoviesPresenter = new UpcomingMoviesPresenter(view, movieRepository);
    }

    @Test
    public void onConstruction_setPresenter() {
        verify(view).setPresenter(upcomingMoviesPresenter);
    }

    @Test
    public void onStart_onSuccess_showList() {
        upcomingMoviesPresenter.start();

        List<MovieUpcoming> movieUpcomingList = Arrays.asList(FIRST_MOVIE, SECOND_MOVIE);
        callbackSuccess(movieUpcomingList);

        verify(view).showLoading();
        verify(view).showUpcomingMoviesList(movieUpcomingList);
    }

    @Test
    public void onStart_onSuccess_emptyList_showEmptyList() {
        upcomingMoviesPresenter.start();

        callbackSuccess(Collections.EMPTY_LIST);

        verify(view).showLoading();
        verify(view).showEmptyList();
    }

    @Test
    public void onStart_onSuccess_nullList_showError() {
        upcomingMoviesPresenter.start();

        callbackSuccess(null);

        verify(view).showLoading();
        verify(view).showError();
    }

    @Test
    public void onStart_onError_showError() {
        upcomingMoviesPresenter.start();

        callbackError();

        verify(view).showLoading();
        verify(view).showError();
    }

    @Test
    public void getUpcomingMovies_onSuccess_showList() {
        upcomingMoviesPresenter.getUpcomingMovies();

        List<MovieUpcoming> movieUpcomingList = Arrays.asList(FIRST_MOVIE, SECOND_MOVIE);
        callbackSuccess(movieUpcomingList);

        verify(view).showLoading();
        verify(view).showUpcomingMoviesList(movieUpcomingList);
    }

    @Test
    public void getUpcomingMovies_onSuccess_nullList_showError() {
        upcomingMoviesPresenter.getUpcomingMovies();

        callbackSuccess(null);

        verify(view).showLoading();
        verify(view).showError();
    }

    @Test
    public void getUpcomingMovies_onSuccess_emptyList_showEmptyList() {
        upcomingMoviesPresenter.getUpcomingMovies();

        callbackSuccess(Collections.EMPTY_LIST);

        verify(view).showLoading();
        verify(view).showEmptyList();
    }

    @Test
    public void getUpcomingMovies_onError_showError() {
        upcomingMoviesPresenter.getUpcomingMovies();

        callbackError();

        verify(view).showLoading();
        verify(view).showError();
    }

    private void callbackSuccess(List<MovieUpcoming> movieUpcomingList) {
        verify(movieRepository).getUpcomingList(repositoryCallbackArgumentCaptor.capture());

        repositoryCallbackArgumentCaptor
                .getValue()
                .onSuccess(movieUpcomingList);
    }

    private void callbackError() {
        verify(movieRepository).getUpcomingList(repositoryCallbackArgumentCaptor.capture());

        repositoryCallbackArgumentCaptor
                .getValue()
                .onError(null);
    }
}