package br.com.aaascp.androidapp.presentation.movie.upcoming;


import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import br.com.aaascp.androidapp.BuildConfig;
import br.com.aaascp.androidapp.R;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming;
import br.com.aaascp.androidapp.presentation.movie.details.MoviesDetailsActivity;
import br.com.aaascp.androidapp.presentation.movie.upcoming.adapter.UpcomingMoviesListViewHolder;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class UpcomingMoviesListActivityTest {

    private static final MovieUpcoming FIRST_MOVIE = new MovieUpcoming(1, "Filme 1", "poster_1", "17/11/1990");
    private static final MovieUpcoming SECOND_MOVIE = new MovieUpcoming(2, "Filme 2", "poster_2", "27/11/1990");

    @Mock
    private UpcomingMoviesContract.Presenter presenter;

    private UpcomingMoviesListActivity activity;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        activity = Robolectric.setupActivity(UpcomingMoviesListActivity.class);

        activity.setPresenter(presenter);
    }

    @Test
    public void onStart_startsPresenter() throws Exception {
        activity.onStart();
        verify(presenter).start();
    }

    @Test
    public void showUpcomingMoviesList_showsCorrectListOfMovies() throws Exception {
        List<MovieUpcoming> movieUpcomingList = Arrays.asList(FIRST_MOVIE, SECOND_MOVIE);
        activity.showUpcomingMoviesList(movieUpcomingList);

        UpcomingMoviesListViewHolder holder =
                (UpcomingMoviesListViewHolder) activity.upcomingMoviesList
                        .findViewHolderForAdapterPosition(0);

        assertEquals(
                FIRST_MOVIE.getTitle(),
                holder.getTitle().getText().toString());

        String releaseDate = String.format(
                activity.getString(R.string.upcoming_movie_release_date),
                FIRST_MOVIE.getReleaseDate());

        assertEquals(
                releaseDate,
                holder.getReleaseDate().getText().toString());

        holder = (UpcomingMoviesListViewHolder) activity.upcomingMoviesList
                        .findViewHolderForAdapterPosition(1);

        assertEquals(
                SECOND_MOVIE.getTitle(),
                holder.getTitle().getText().toString());

        releaseDate = String.format(
                activity.getString(R.string.upcoming_movie_release_date),
                SECOND_MOVIE.getReleaseDate());

        assertEquals(
                releaseDate,
                holder.getReleaseDate().getText().toString());
    }

    @Test
    public void onMovieClick_startsMovieDetailsActivityForMovieId() {
        List<MovieUpcoming> movieUpcomingList = Arrays.asList(FIRST_MOVIE, SECOND_MOVIE);
        activity.showUpcomingMoviesList(movieUpcomingList);


        UpcomingMoviesListViewHolder holder =
                (UpcomingMoviesListViewHolder) activity.upcomingMoviesList
                        .findViewHolderForAdapterPosition(0);

        holder.getRoot().performClick();

        Intent expectedIntent = new Intent(activity, MoviesDetailsActivity.class);
        Intent actualIntent = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
        assertEquals(
                FIRST_MOVIE.getId(),
                actualIntent.getExtras().getInt(MoviesDetailsActivity.EXTRA_MOVIE_ID));

        holder = (UpcomingMoviesListViewHolder) activity.upcomingMoviesList
                        .findViewHolderForAdapterPosition(1);

        holder.getRoot().performClick();

        expectedIntent = new Intent(activity, MoviesDetailsActivity.class);
        actualIntent = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
        assertEquals(
                SECOND_MOVIE.getId(),
                actualIntent.getExtras().getInt(MoviesDetailsActivity.EXTRA_MOVIE_ID));

    }


}