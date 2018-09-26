package br.com.aaascp.androidapp.infra.repository.movie;

import android.util.Log;

import java.util.Collections;
import java.util.List;

import br.com.aaascp.androidapp.infra.adapter.MovieAdapter;
import br.com.aaascp.androidapp.infra.repository.RepositoryCallback;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetailsWithGenre;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming;
import br.com.aaascp.androidapp.infra.source.remote.body.ResultsResponseBody;
import br.com.aaascp.androidapp.infra.source.remote.body.movie.MovieDetailsResponseBody;
import br.com.aaascp.androidapp.infra.source.remote.body.movie.MovieUpcomingResponseBody;
import br.com.aaascp.androidapp.infra.source.remote.endpoint.MovieEndpoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DefaultMovieRepository implements MovieRepository {

    private final MovieEndpoint movieEndpoint;

    public DefaultMovieRepository(MovieEndpoint movieEndpoint) {
        this.movieEndpoint = movieEndpoint;
    }

    @Override
    public void getUpcomingList(final RepositoryCallback<List<MovieUpcoming>> callback) {
        Log.d("Andre", "repository_getUpcomingList");

        this.movieEndpoint.getUpcoming(1, "BR").enqueue(new Callback<ResultsResponseBody<List<MovieUpcomingResponseBody>>>() {
            @Override
            public void onResponse(Call<ResultsResponseBody<List<MovieUpcomingResponseBody>>> call, Response<ResultsResponseBody<List<MovieUpcomingResponseBody>>> response) {
                Log.d("Andre", "onResponse");
                if (response.body() == null) {
                    callback.onError(null);
                    return;
                }

                ResultsResponseBody<List<MovieUpcomingResponseBody>> movieUpcomingResponseBody = response.body();

                callback.onSuccess(MovieAdapter.Companion.adaptUpcoming(movieUpcomingResponseBody.getResults()));
            }

            @Override
            public void onFailure(Call<ResultsResponseBody<List<MovieUpcomingResponseBody>>> call, Throwable t) {
                Log.d("Andre", "onFailure");
                callback.onError(Collections.singletonList(t.getMessage()));
            }
        });
    }

    @Override
    public void getMovieDetails(int movieId, final RepositoryCallback<MovieDetailsWithGenre> callback) {
        this.movieEndpoint.getDetails(movieId, "pt-BR")
                .enqueue(new Callback<MovieDetailsResponseBody>() {

                    @Override
                    public void onResponse(Call<MovieDetailsResponseBody> call, Response<MovieDetailsResponseBody> response) {
                        Log.d("Andre", "onResponse");

                        if (response.body() == null) {
                            callback.onError(null);
                            return;
                        }

                        MovieDetailsResponseBody movieDetailsResponseBody = response.body();

                        callback.onSuccess(
                                new MovieDetailsWithGenre(
                                        MovieAdapter.Companion.adaptGenre(
                                                movieDetailsResponseBody.getGenres(),
                                                movieDetailsResponseBody.getId()),
                                        MovieAdapter.Companion.adaptDetails(response.body()))
                        );

                    }

                    @Override
                    public void onFailure(Call<MovieDetailsResponseBody> call, Throwable t) {
                        Log.d("Andre", "onFailure");

                        callback.onError(Collections.singletonList(t.getMessage()));
                    }
                });
    }
}
