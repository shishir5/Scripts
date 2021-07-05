package com.contributetech.scripts.tvDetails

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.contributetech.scripts.R
import com.contributetech.scripts.commonListeners.ITvClick
import com.contributetech.scripts.database.tvDetails.SeasonsItemDetail
import com.contributetech.scripts.network.NetworkImageUtil
import com.contributetech.scripts.util.ImageUtil
import com.facebook.drawee.view.SimpleDraweeView

class SeasonsItemRecyclerAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var itemClickListener: ITvClick? = null
    var mSeasonsList:ArrayList<SeasonsItemDetail> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.recycler_collection_item, parent, false);
        return SeasonItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mSeasonsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holderCollection:SeasonItemViewHolder = holder as SeasonItemViewHolder
        val season:SeasonsItemDetail  = mSeasonsList.get(position)
        holderCollection.onBind(season, itemClickListener)
    }

    fun setData(newList:ArrayList<SeasonsItemDetail>) {
        mSeasonsList = newList
        notifyDataSetChanged()
    }
}

class SeasonItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var sdvImage: SimpleDraweeView
    var tvTitle: TextView

    init {
        sdvImage = view.findViewById(R.id.sdv_collection_item_image) as SimpleDraweeView
        tvTitle = view.findViewById(R.id.tv_collection_item_title)
    }

    fun onBind(season: SeasonsItemDetail, listener: ITvClick?) {
        tvTitle.setText(season.name)
        if(season.posterPath != null) {
            val path: String = NetworkImageUtil.getImagePath(season.posterPath, ImageUtil.LandscapeSizes.mid_size)
            val uri = Uri.parse(path)
            sdvImage.setImageURI(uri)
        }
        sdvImage.setOnClickListener {
            if(listener != null){
                listener.onSeasonClick(season.id)
            }
        }
    }
}