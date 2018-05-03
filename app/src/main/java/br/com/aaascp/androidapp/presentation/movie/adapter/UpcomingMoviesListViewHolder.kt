package br.com.aaascp.androidapp.presentation.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.aaascp.androidapp.R
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming
import br.com.aaascp.androidapp.presentation.ViewHolderBase
import kotlinx.android.synthetic.main.row_upcoming_movie_item.view.*

class UpcomingMoviesListViewHolder private constructor(view: View) : ViewHolderBase<MovieUpcoming>(view) {

    companion object {
        fun create(parent: ViewGroup): UpcomingMoviesListViewHolder {
            val inflatedView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_upcoming_movie_item, parent, false)
            return UpcomingMoviesListViewHolder(inflatedView)
        }
    }

    private val root: View = view.root
    private val title: TextView = view.title
    private val releaseDate: TextView = view.releaseDate

    override fun bind(item: MovieUpcoming) {
        title.text = item.title
        releaseDate.text = item.releaseDate
//        root.setOnClickListener {
//            LessonListActivity.startForArea(
//                    itemView.context,
//                    item.id,
//                    item.title,
//                    item.subject)
//        }
    }
}