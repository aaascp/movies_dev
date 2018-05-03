package br.com.aaascp.androidapp.presentation.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.aaascp.androidapp.R
import br.com.aaascp.androidapp.infra.source.local.entity.Movie
import br.com.aaascp.androidapp.presentation.ViewHolderBase
import kotlinx.android.synthetic.main.row_area_item.view.*

class MovieListViewHolder private constructor(view: View) : ViewHolderBase<Movie>(view) {

    companion object {
        fun create(parent: ViewGroup): MovieListViewHolder {
            val inflatedView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_area_item, parent, false)
            return MovieListViewHolder(inflatedView)
        }
    }

    private val root: View = view.root
    private val subject: TextView = view.subject
    private val title: TextView = view.title

    override fun bind(item: Movie) {
        subject.text = item.subject
        title.text = item.title
//        root.setOnClickListener {
//            LessonListActivity.startForArea(
//                    itemView.context,
//                    item.id,
//                    item.title,
//                    item.subject)
//        }
    }
}