package br.com.aaascp.androidapp.presentation.movie.details;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.aaascp.androidapp.Inject;
import br.com.aaascp.androidapp.R;
import br.com.aaascp.androidapp.infra.repository.movie.DefaultMovieRepository;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetails;
import br.com.aaascp.androidapp.infra.source.remote.ServiceGenerator;
import br.com.aaascp.androidapp.infra.source.remote.endpoint.MovieEndpoint;
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

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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

        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });

        new MovieDetailsPresenter(
                this,
                this.getMovieIdExtra(),
                new DefaultMovieRepository(
                        new ServiceGenerator().createService(MovieEndpoint.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.start();
    }

    @Override
    public void showMovieDetails(MovieDetails movieDetails) {
        this.state.setVisibility(View.GONE);
        this.bindMovie(movieDetails);
    }


    @Override
    public void showMovieGenres(String genres) {
        this.movieGenres.setText(genres);
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
    public void setPresenter(MovieDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void bindMovie(MovieDetails movieDetails) {
        if (movieDetails.getPosterPath() != null) {
            Inject.provideImageUtils().loadImageFromApi(this.moviePoster, movieDetails.getPosterPath(), 500);
        }

        this.movieDescription.setText(movieDetails.getOverview());
        this.movieTitle.setText(movieDetails.getTitle());

        String releaseDateFormat = this.getString(R.string.upcoming_movie_release_date);
        this.movieReleaseDate.setText(
                String.format(
                        releaseDateFormat,
                        movieDetails.getReleaseDate()));
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
}
