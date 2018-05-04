package br.com.aaascp.androidapp.presentation.movie.upcoming.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import br.com.aaascp.androidapp.R
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming
import br.com.aaascp.androidapp.presentation.ViewHolderBase


class UpcomingMoviesListAdapter : RecyclerView.Adapter<ViewHolderBase<MovieUpcoming>>() {
    private val upcomingMoviesList = mutableListOf<MovieUpcoming>()

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ) = UpcomingMoviesListViewHolder.create(parent)


    override fun onBindViewHolder(
            holder: ViewHolderBase<MovieUpcoming>,
            position: Int
    ) = holder.bind(upcomingMoviesList[position])

    override fun getItemViewType(position: Int) = LIST_TYPE

    override fun getItemCount(): Int = upcomingMoviesList.size

    fun setItems(items: List<MovieUpcoming>?) {
        items?.let {
            upcomingMoviesList.clear()
            upcomingMoviesList.addAll(items)
            notifyDataSetChanged()
        }
    }

    companion object {
        private const val LIST_TYPE = R.layout.row_upcoming_movie_item
    }
}
