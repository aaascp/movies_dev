package br.com.aaascp.androidapp.presentation.movie.details;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.List;

import br.com.aaascp.androidapp.R;
import br.com.aaascp.androidapp.infra.repository.movie.DefaultMovieRespository;
import br.com.aaascp.androidapp.infra.source.local.entity.Genre;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetails;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetailsWithGenre;
import br.com.aaascp.androidapp.infra.source.remote.ServiceGenerator;
import br.com.aaascp.androidapp.infra.source.remote.endpoint.MovieEndpoint;
import br.com.aaascp.androidapp.presentation.util.ImageUtilsKt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesDetailsActivity extends Activity implements MovieDetailsContract.View {

    private static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";

    @BindView(R.id.state)
    TextView state;

    @BindView(R.id.movieDescription)
    TextView movieDescription;

    @BindView(R.id.movieTitle)
    TextView movieTitle;

    @BindView(R.id.movieReleaseDate)
    TextView movieReleaseDate;

    @BindView(R.id.movieGenres)
    TextView movieGenres;

    @BindView(R.id.moviePoster)
    ImageView moviePoster;

//    @BindView(R.id.toolbar)
//    Toolbar toolbar;

    private MovieDetailsContract.Presenter presenter;


    public static void startForMovie(Context context, int movieId) {

        Intent intent = new Intent(
                context,
                MoviesDetailsActivity.class);

        intent.putExtra(EXTRA_MOVIE_ID, movieId);

        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

//        toolbar.setNavigationOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        onBackPressed();
//                    }
//                });

        new MovieDetailsPresenter(
                this,
                this.getMovieIdExtra(),
                new DefaultMovieRespository(
                        new ServiceGenerator().createService(MovieEndpoint.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.start();
    }

    @Override
    public void showMovieDetails(MovieDetailsWithGenre movieDetailsWithGenre) {
        this.state.setVisibility(View.GONE);
        this.bindMovie(movieDetailsWithGenre.getMovie());
        this.bindGenres(movieDetailsWithGenre.getGenres());
    }

    @Override
    public void showError() {
        this.state.setVisibility(View.GONE);
        this.state.setText(
                this.getString(
                        R.string.item_error
                ));
    }

    @Override
    public void showLoading() {
        this.state.setVisibility(View.GONE);
        this.state.setText(
                this.getString(
                        R.string.item_loading
                ));
    }

    @Override
    public void bindMovie(MovieDetails movieDetails) {
        if (movieDetails == null) {
            return;
        }

        if (movieDetails.getPosterPath() != null) {
            ImageUtilsKt.loadImageFromApi(this.moviePoster, movieDetails.getPosterPath(), 500);
        }

        this.movieDescription.setText(movieDetails.getOverview());
        this.movieTitle.setText(movieDetails.getTitle());

        String releaseDateFormat = this.getString(R.string.upcoming_movie_release_date);
        this.movieReleaseDate.setText(
                String.format(
                        releaseDateFormat,
                        movieDetails.getReleaseDate()));
    }

    @Override
    public void setPresenter(MovieDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private int getMovieIdExtra() {
        return 210577;
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            return extras.getInt(EXTRA_MOVIE_ID);
//        }
//
//        return -1;
    }

    private void bindGenres(List<Genre> genresList) {
        this.movieGenres.setText(this.joinGenres(genresList));
    }

    private String joinGenres(List<Genre> genreList) {
        String genresJoined = "";

        for (Genre genre : genreList) {
            genresJoined = genre.getName() + ", ";
        }

        return genresJoined.substring(0, genresJoined.length() - 2);
    }
}
