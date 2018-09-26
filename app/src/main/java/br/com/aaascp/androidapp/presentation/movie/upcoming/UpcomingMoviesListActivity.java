package br.com.aaascp.androidapp.presentation.movie.upcoming;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import java.util.List;

import br.com.aaascp.androidapp.R;
import br.com.aaascp.androidapp.infra.repository.movie.DefaultMovieRepository;
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming;
import br.com.aaascp.androidapp.infra.source.remote.ServiceGenerator;
import br.com.aaascp.androidapp.infra.source.remote.endpoint.MovieEndpoint;
import br.com.aaascp.androidapp.presentation.SingleRowStaticViewAdapter;
import br.com.aaascp.androidapp.presentation.movie.upcoming.adapter.UpcomingMoviesListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UpcomingMoviesListActivity extends AppCompatActivity implements UpcomingMoviesContract.View {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.list)
    RecyclerView upcomingMoviesList;

    private UpcomingMoviesContract.Presenter presenter;
    private UpcomingMoviesListAdapter listAdapter;
    private SingleRowStaticViewAdapter errorAdapter;
    private SingleRowStaticViewAdapter loadingAdapter;
    private SingleRowStaticViewAdapter emptyAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        new UpcomingMoviesPresenter(
                this,
                new DefaultMovieRepository(
                        new ServiceGenerator().createService(MovieEndpoint.class)));

        this.initSwipeToRefresh();
        this.initAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void showUpcomingMoviesList(List<MovieUpcoming> movieUpcomingList) {
        Log.d("Andre", "showUpcomingMoviesList");
        this.listAdapter.setItems(movieUpcomingList);
        this.upcomingMoviesList.setAdapter(this.listAdapter);
    }

    @Override
    public void showError() {
        Log.d("Andre", "showError");
        this.upcomingMoviesList.setAdapter(this.errorAdapter);
    }

    @Override
    public void showLoading() {
        Log.d("Andre", "showLoading");
        this.upcomingMoviesList.setAdapter(this.loadingAdapter);
    }

    @Override
    public void showEmptyList() {
        Log.d("Andre", "showEmptyList");
        this.upcomingMoviesList.setAdapter(this.emptyAdapter);
    }

    @Override
    public void setPresenter(UpcomingMoviesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void initAdapter() {
        this.listAdapter = new UpcomingMoviesListAdapter();

        this.errorAdapter =
                new SingleRowStaticViewAdapter(
                        R.layout.row_items_error,
                        LayoutInflater.from(this));

        this.loadingAdapter =
                new SingleRowStaticViewAdapter(
                        R.layout.row_items_loading,
                        LayoutInflater.from(this));

        this.emptyAdapter =
                new SingleRowStaticViewAdapter(
                        R.layout.row_items_empty,
                        LayoutInflater.from(this));
    }

    private void initSwipeToRefresh() {
        this.swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        presenter.getUpcomingMovies();
                    }
                }
        );
    }
}
