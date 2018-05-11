package com.contributetech.scripts.homescreen

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.contributetech.scripts.R
import com.contributetech.scripts.commonListeners.IMovieClick
import com.contributetech.scripts.database.moviesListItemDetail.MovieListItem
import com.contributetech.scripts.network.NetworkImageUtil
import com.contributetech.scripts.util.ImageUtil
import com.facebook.drawee.view.SimpleDraweeView

class HorizontalMovieListRecyclerAdapter():RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mMovieList:ArrayList<MovieListItem> = ArrayList()
    var onClickListener: IMovieClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MoviesViewHolder(inflater.inflate(R.layout.horizontal_movie_list_item_view, parent, false))
    }

    override fun getItemCount(): Int {
        return mMovieList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie: MovieListItem = mMovieList.get(position)
        (holder as MoviesViewHolder).onBindView(movie, onClickListener)
    }

    fun setData(newList:ArrayList<MovieListItem>) {
        mMovieList = newList
        notifyDataSetChanged()
    }
}

class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var fivMovieImage: SimpleDraweeView
    var tvMovieTitle:TextView

    init {
        fivMovieImage = view.findViewById(R.id.fiv_movie_image)
        tvMovieTitle = view.findViewById(R.id.tv_movie_title)
    }

    fun onBindView(movie: MovieListItem, listener:IMovieClick?) {
        tvMovieTitle.setText(movie.originalTitle)
        if(movie.backdropPath != null) {
            val path: String = NetworkImageUtil.getImagePath(movie.posterPath, ImageUtil.LandscapeSizes.mid_size)
            val uri = Uri.parse(path)
            fivMovieImage.setImageURI(uri)
        }
        fivMovieImage.setOnClickListener {
            if(listener != null){
                listener.onMovieClick(movie.id)
            }
        }
    }

}
