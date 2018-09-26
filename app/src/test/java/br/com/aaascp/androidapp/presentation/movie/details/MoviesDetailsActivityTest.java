package br.com.aaascp.androidapp.presentation.movie.details;

import android.content.Intent;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import br.com.aaascp.androidapp.BuildConfig;
import br.com.aaascp.androidapp.R;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetails;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MoviesDetailsActivityTest {

    private static final MovieDetails MOVIE =
            new MovieDetails(
                    1,
                    "Filme 1",
                    "description",
                    "poster_1",
                    "17/11/1990");

    @Mock
    private MovieDetailsContract.Presenter presenter;

    private MoviesDetailsActivity activity;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        activity = Robolectric.setupActivity(MoviesDetailsActivity.class);

        activity.setPresenter(presenter);
    }

    @Test
    public void onCreate_startsWithCorrectExtras() throws Exception {
        MoviesDetailsActivity.startForMovie(activity, MOVIE.getId());
        Intent intent = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(
                MOVIE.getId(),
                intent.getExtras().getInt(MoviesDetailsActivity.EXTRA_MOVIE_ID));
    }

    @Test
    public void onStart_startsPresenter() throws Exception {
        activity.onStart();
        verify(presenter).start();
    }

    @Test
    public void showMovieDetails_showsCorrectMovies() throws Exception {
        activity.showMovieDetails(MOVIE);

        assertEquals(
                View.GONE,
                activity.state.getVisibility());

        assertEquals(
                MOVIE.getOverview(),
                activity.movieDescription.getText().toString());

        assertEquals(
                MOVIE.getTitle(),
                activity.movieTitle.getText().toString());

        String releaseDateFormat =
                String.format(
                        activity.getString(R.string.upcoming_movie_release_date),
                        MOVIE.getReleaseDate());

        assertEquals(
                releaseDateFormat,
                activity.movieReleaseDate.getText().toString());
    }

    @Test
    public void showError_showsErrorMessage() throws Exception {
        activity.showError();

        assertEquals(
                View.VISIBLE,
                activity.state.getVisibility());

        assertEquals(
                activity.getString(R.string.item_error),
                activity.state.getText());
    }

    @Test
    public void showLoading_showsLoadingMessage() throws Exception {
        activity.showLoading();

        assertEquals(
                View.VISIBLE,
                activity.state.getVisibility());

        assertEquals(
                activity.getString(R.string.item_loading),
                activity.state.getText());
    }

    @Test
    public void showMovieGenres_showsGenresList() throws Exception {
        String genres = "Genre 1, Genre 2";

        activity.showMovieGenres(genres);

        assertEquals(
                genres,
                activity.movieGenres.getText());
    }
}