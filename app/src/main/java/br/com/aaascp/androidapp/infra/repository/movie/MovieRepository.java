package br.com.aaascp.androidapp.infra.repository.movie;

import java.util.List;

import br.com.aaascp.androidapp.infra.repository.RepositoryCallback;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetailsWithGenre;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming;

public interface MovieRepository {

    void getUpcomingList(RepositoryCallback<List<MovieUpcoming>> callback);

    void getMovieDetails(int movieId, RepositoryCallback<MovieDetailsWithGenre> callback);
}
