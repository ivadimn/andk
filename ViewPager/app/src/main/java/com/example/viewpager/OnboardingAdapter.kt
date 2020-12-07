package com.example.viewpager

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingAdapter(
    private val screens: List<OnboardingScreen>,
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {


    override fun getItemCount(): Int {
        Log.d("VIEW_PAGER_ADAPTER", "adapter getItemCount" )
        return screens.size
    }

    override fun createFragment(position: Int): Fragment {
        Log.d("VIEW_PAGER_ADAPTER", "adapter createFragment $position" )
        val screen = screens[position]
        return OnboardingFragment.newInstance(
            textRes = screen.textRes,
            bgColorRes = screen.bgColorRes,
            drawableRes = screen.drawableRes
        )
    }
}