package com.business.the_movie_app.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.business.the_movie_app.R
import com.business.the_movie_app.model.response.Movie

// presentation/MovieListAdapter.kt
class MovieListAdapter(private var movies: List<Movie>,private val onItemClick: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>(){
    private var filteredMovies: List<Movie> = movies.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item_movie_list, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tvMovieTitle)
        private val releaseDateTextView: TextView = itemView.findViewById(R.id.tvMovieDatePublished)
        private val posterImageView: ImageView = itemView.findViewById(R.id.iv_movie_image)

        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            releaseDateTextView.text = movie.releaseDate

            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .into(posterImageView)

            itemView.setOnClickListener {
                onItemClick(movie)
            }
        }
    }
}
