package com.contributetech.scripts.movieDetail

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.contributetech.scripts.R
import com.contributetech.scripts.application.Config
import com.contributetech.scripts.database.VideoListItem
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailView
import com.google.android.youtube.player.YouTubeThumbnailLoader


interface IVideoThumbnailClick {
    fun onClick(videoUrl:String)
}

class PagerVideoCarouselAdapter(var mContext: Context, var mMovieVideoUrls: ArrayList<VideoListItem>, val clickListener:IVideoThumbnailClick): PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mMovieVideoUrls.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.video_carousel_item_view, null)
        val imageUrl: String = mMovieVideoUrls.get(position).key


        val videoThumbnailImageView:YouTubeThumbnailView = view.findViewById(R.id.video_thumbnail_image_view)
        videoThumbnailImageView.initialize(Config.YOUTUBE_DEV_KEY, object : YouTubeThumbnailView.OnInitializedListener {
            override fun onInitializationSuccess(youTubeThumbnailView: YouTubeThumbnailView, youTubeThumbnailLoader: YouTubeThumbnailLoader) {
                //when initialization is sucess, set the video id to thumbnail to load
                youTubeThumbnailLoader.setVideo(imageUrl)

                youTubeThumbnailLoader.setOnThumbnailLoadedListener(object : YouTubeThumbnailLoader.OnThumbnailLoadedListener {
                    override fun onThumbnailLoaded(youTubeThumbnailView: YouTubeThumbnailView, s: String) {
                        //when thumbnail loaded successfully release the thumbnail loader as we are showing thumbnail in adapter
                        youTubeThumbnailLoader.release()
                    }

                    override fun onThumbnailError(youTubeThumbnailView: YouTubeThumbnailView, errorReason: YouTubeThumbnailLoader.ErrorReason) {
                        //print or show error when thumbnail load failed
                        Log.e("check123", "Youtube Thumbnail Error")
                    }
                })
            }

            override fun onInitializationFailure(youTubeThumbnailView: YouTubeThumbnailView, youTubeInitializationResult: YouTubeInitializationResult) {
                //print or show error when initialization failed
                Log.e("check123", "Youtube Initialization Failure")
            }
        })

        videoThumbnailImageView.setOnClickListener(View.OnClickListener {
            clickListener.onClick(imageUrl)
        })

        val viewPager = container as ViewPager
        viewPager.addView(view, 0)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val viewPager = container as ViewPager
        val view = `object` as View
        viewPager.removeView(view)
    }

    fun setData(newList: ArrayList<VideoListItem>) {
        mMovieVideoUrls = newList;
        notifyDataSetChanged();
    }
}