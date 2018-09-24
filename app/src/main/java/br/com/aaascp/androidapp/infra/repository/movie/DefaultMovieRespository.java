package br.com.aaascp.androidapp.infra.repository.movie;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import br.com.aaascp.androidapp.infra.adapter.MovieAdapter;
import br.com.aaascp.androidapp.infra.repository.RepositoryCallback;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetailsWithGenre;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming;
import br.com.aaascp.androidapp.infra.source.remote.body.movie.MovieDetailsResponseBody;
import br.com.aaascp.androidapp.infra.source.remote.endpoint.MovieEndpoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DefaultMovieRespository implements MovieRepository {

    private final MovieEndpoint movieEndpoint;

    @Inject
    public DefaultMovieRespository(MovieEndpoint movieEndpoint) {
        this.movieEndpoint = movieEndpoint;
    }

    @Override
    public void getUpcomingList(RepositoryCallback<List<MovieUpcoming>> callback) {

    }

    @Override
    public void getMovieDetails(int movieId, final RepositoryCallback<MovieDetailsWithGenre> callback) {
        movieEndpoint.getDetails(movieId, "pt-BR")
                .enqueue(new Callback<MovieDetailsResponseBody>() {

                    @Override
                    public void onResponse(Call<MovieDetailsResponseBody> call, Response<MovieDetailsResponseBody> response) {
                        if (response.body() != null) {
                            callback.onError(null);
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
                        Log.d("Andre", "failure");
                    }
                });
    }
}
