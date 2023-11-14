package com.chainreaction.task.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chainreaction.task.domain.model.Movie
import com.chainreaction.task.movies.databinding.ItemMovieBinding
import com.chainreaction.task.util.loadImage

class MoviesAdapter : PagingDataAdapter<Movie, MoviesAdapter.MovieViewHolder>(MovieComparator) {

    var onShareClick: ((String?, String?) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(it) }
    }

 inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                textViewTitle.text = movie.movieTitle
                textViewOverview.text = movie.overview
                binding.imageView.loadImage("${BuildConfig.IMAGE_BASE_URL}${movie?.imageId}")
                buttonShare.setOnClickListener {
                    onShareClick?.invoke(movie.movieTitle, movie.overview)
                }
            }
        }
    }

    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}
