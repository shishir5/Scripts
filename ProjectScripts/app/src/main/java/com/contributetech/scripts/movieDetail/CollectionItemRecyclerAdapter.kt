package com.contributetech.scripts.movieDetail

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

class CollectionItemRecyclerAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onClickListener: IMovieClick? = null
    var mMoviesList:ArrayList<MovieListItem> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view:View = inflater.inflate(R.layout.recycler_collection_item, parent, false);
        return CollectionItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mMoviesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holderCollection:CollectionItemViewHolder = holder as CollectionItemViewHolder
        val movie:MovieListItem = mMoviesList.get(position)
        holderCollection.onBind(movie, onClickListener)
    }

    fun setData(newList:ArrayList<MovieListItem>) {
        mMoviesList = newList
        notifyDataSetChanged()
    }
}

class CollectionItemViewHolder(view: View):RecyclerView.ViewHolder(view) {
    var sdvImage:SimpleDraweeView
    var tvTitle:TextView

    init {
        sdvImage = view.findViewById(R.id.sdv_collection_item_image) as SimpleDraweeView
        tvTitle = view.findViewById(R.id.tv_collection_item_title)
    }

    fun onBind(movie:MovieListItem, listener:IMovieClick?) {
        tvTitle.setText(movie.originalTitle)
        if(movie.backdropPath != null) {
            val path: String = NetworkImageUtil.getImagePath(movie.posterPath, ImageUtil.LandscapeSizes.mid_size)
            val uri = Uri.parse(path)
            sdvImage.setImageURI(uri)
        }
        sdvImage.setOnClickListener {
            if(listener != null){
                listener.onMovieClick(movie.id)
            }
        }
    }
}