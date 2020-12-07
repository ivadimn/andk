package com.example.viewpager

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {

    companion object {
        private const val KEY_TEXT = "text"
        private const val KEY_COLOR = "color"
        private const val KEY_IMAGE = "image"

        fun newInstance(
            @StringRes textRes : Int,
            @ColorRes bgColorRes: Int,
            @DrawableRes drawableRes: Int
        ) : OnboardingFragment {
           return OnboardingFragment().withArguments {
               putInt(KEY_TEXT, textRes)
               putInt(KEY_COLOR, bgColorRes)
               putInt(KEY_IMAGE, drawableRes)
           }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        requireView().setBackgroundResource(requireArguments().getInt(KEY_COLOR))
        requireView().findViewById<TextView>(R.id.textView).text =
            getString(requireArguments().getInt(KEY_TEXT))
        requireView().findViewById<ImageView>(R.id.imageView)
            .setImageResource(requireArguments().getInt(KEY_IMAGE))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("VIEW_PAGER_FRAGMENT",
            "fragment onCreate ${resources.getString(requireArguments().getInt(KEY_TEXT))}" )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("VIEW_PAGER_FRAGMENY",
            "fragment onDestroy ${resources.getString(requireArguments().getInt(KEY_TEXT))}" )
    }
}