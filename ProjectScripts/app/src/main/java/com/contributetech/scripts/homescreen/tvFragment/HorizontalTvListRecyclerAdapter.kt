package com.contributetech.scripts.homescreen.tvFragment

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.contributetech.scripts.R
import com.contributetech.scripts.commonListeners.ITvClick
import com.contributetech.scripts.database.tvListItemDetail.TvShowListItem
import com.contributetech.scripts.network.NetworkImageUtil
import com.contributetech.scripts.util.ImageUtil
import com.facebook.drawee.view.SimpleDraweeView

class HorizontalTvListRecyclerAdapter():RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mTvShowList:ArrayList<TvShowListItem> = ArrayList()
    var listener:ITvClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
        return TvViewHolder(inflater.inflate(R.layout.horizontal_movie_list_item_view, parent, false))
    }

    override fun getItemCount(): Int {
        return mTvShowList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvShow: TvShowListItem = mTvShowList.get(position)
        (holder as TvViewHolder).onBindView(tvShow, listener)
    }

    fun setData(newList:ArrayList<TvShowListItem>) {
        mTvShowList = newList
        notifyDataSetChanged()
    }
}

class TvViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var fivMovieImage: SimpleDraweeView
    var tvMovieTitle:TextView

    init {
        fivMovieImage = view.findViewById(R.id.fiv_movie_image)
        tvMovieTitle = view.findViewById(R.id.tv_movie_title)
    }

    fun onBindView(tvShow: TvShowListItem, listener: ITvClick?) {
        tvMovieTitle.setText(tvShow.originalTitle)
        if(tvShow.backdropPath != null) {
            val path: String = NetworkImageUtil.getImagePath(tvShow.posterPath, ImageUtil.LandscapeSizes.mid_size)
            val uri = Uri.parse(path)
            fivMovieImage.setImageURI(uri)
        }
        fivMovieImage.setOnClickListener(View.OnClickListener {
            listener?.onTvClick(tvShow.id)
        })
    }

}
