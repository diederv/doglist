package com.github.diederv.doglist.post

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.diederv.doglist.model.Movie
import com.github.diederv.doglist.R
import com.github.diederv.doglist.databinding.ItemPostBinding

class MovieListAdapter: RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private lateinit var movieList:List<Movie>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPostBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_post, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return if(::movieList.isInitialized) movieList.size else 0
    }

    fun updateMovieList(list:List<Movie>){
        this.movieList = list
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemPostBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = MovieViewModel()

        fun bind(movie: Movie){
            viewModel.bind(movie)
            binding.viewModel = viewModel
        }
    }
}