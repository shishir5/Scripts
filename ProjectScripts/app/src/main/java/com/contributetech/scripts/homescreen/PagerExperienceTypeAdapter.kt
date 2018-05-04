package com.contributetech.scripts.homescreen

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import android.view.ViewGroup

class PagerExperienceTypeAdapter(fragmentManager: FragmentManager, var mActivityContract: MoviesFragmentContract.ActivityContract):FragmentStatePagerAdapter(fragmentManager) {

    val SIZE:Int = 2
    var firstFrag:HomeScreenFragment? = null
    var secondFrag:HomeScreenFragment? = null

    override fun getItem(position: Int): Fragment {
        when (position ) {
            0 -> {
                firstFrag = HomeScreenFragment.newInstance()
                firstFrag?.mActivityContract = mActivityContract

            }
            1 -> {
                secondFrag = HomeScreenFragment.newInstance()
                secondFrag?.mActivityContract = mActivityContract
            }
        }
        Log.d("check123", "NeitherCase")
        return HomeScreenFragment()
    }

    override fun getCount(): Int {
        return SIZE
    }

    fun getFragment(position:Int) : HomeScreenFragment? {
        if (position == 0) {
            if (firstFrag != null) {
                return firstFrag as HomeScreenFragment
            }
        }else {
            if (secondFrag != null) {
                return secondFrag as HomeScreenFragment
            }
        }
       return null
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if(position == 0) {
            if(firstFrag == null){
                firstFrag = super.instantiateItem(container, position) as HomeScreenFragment
            }
            firstFrag?.mActivityContract = mActivityContract
            return firstFrag as HomeScreenFragment
        }
        else {
            if(secondFrag == null){
                secondFrag = super.instantiateItem(container, position) as HomeScreenFragment
            }
            secondFrag?.mActivityContract = mActivityContract
            return secondFrag as HomeScreenFragment
        }
    }
}