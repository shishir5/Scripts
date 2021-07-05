package com.contributetech.scripts.movieDetail

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.contributetech.scripts.R
import com.contributetech.scripts.database.Cast
import com.contributetech.scripts.database.Review
import com.contributetech.scripts.network.NetworkImageUtil
import com.contributetech.scripts.util.ImageUtil
import com.facebook.drawee.view.SimpleDraweeView

class CastHorizontalRecyclerAdapter():RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mCastList:ArrayList<Cast> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.layout_credits_item_horizontal_recycler, parent, false);
        return CastItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mCastList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holderCollection:CastItemViewHolder = holder as CastItemViewHolder
        val cast: Cast = mCastList.get(position)
        holderCollection.onBind(cast)
    }

    fun setData(newList:ArrayList<Cast>) {
        mCastList = newList
        notifyDataSetChanged()
    }
}

class CastItemViewHolder(view: View):RecyclerView.ViewHolder(view) {
    var sdvImage: SimpleDraweeView
    var tvName: TextView
    var tvCharacterName: TextView

    init {
        sdvImage = view.findViewById(R.id.sdv_cast_image) as SimpleDraweeView
        tvName = view.findViewById(R.id.tv_cast_name)
        tvCharacterName = view.findViewById(R.id.tv_character_name)
    }

    fun onBind(cast: Cast) {
        tvName.setText(cast.name)
        tvCharacterName.setText(cast.character)
        if(cast.profilePath != null) {
            val path: String = NetworkImageUtil.getImagePath(cast.profilePath, ImageUtil.PortraitSizes.small_size)
            val uri = Uri.parse(path)
            sdvImage.setImageURI(uri)
        }
    }
}