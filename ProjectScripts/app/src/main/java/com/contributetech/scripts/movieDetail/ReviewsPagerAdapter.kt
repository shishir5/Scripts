package com.contributetech.scripts.movieDetail

import android.content.Context
import android.net.Uri
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.contributetech.scripts.R
import com.contributetech.scripts.database.Review

class ReviewsPagerAdapter(var context: Context): PagerAdapter() {

    var mReviews: ArrayList<Review> = ArrayList()


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mReviews.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.review_viewpager_item, null)
        val review: Review = mReviews.get(position)
        val tvContent:TextView = view.findViewById(R.id.tv_review)
        val tvAuthor:TextView = view.findViewById(R.id.tv_review_author)
        tvContent.setText(review.content)
        tvAuthor.setText(review.author)

        val viewPager = container as ViewPager
        viewPager.addView(view, 0)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val viewPager = container as ViewPager
        val view = `object` as View
        viewPager.removeView(view)
    }

    fun setData(newList: ArrayList<Review>) {
        mReviews = newList;
        notifyDataSetChanged();
    }
}