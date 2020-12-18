package com.example.viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2


class MainActivity : AppCompatActivity() {

    private val screens : List<OnboardingScreen> = listOf(
        OnboardingScreen(
            textRes = R.string.onboarding_text_1,
            bgColorRes = R.color.onboarding_color_1,
            drawableRes = R.drawable.onboarding_image_1
        ),
        OnboardingScreen(
            textRes = R.string.onboarding_text_2,
            bgColorRes = R.color.onboarding_color_2,
            drawableRes = R.drawable.onboarding_image_2
        ),
        OnboardingScreen(
            textRes = R.string.onboarding_text_3,
            bgColorRes = R.color.onboarding_color_3,
            drawableRes = R.drawable.onboarding_image_3
        ),
        OnboardingScreen(
            textRes = R.string.onboarding_text_4,
            bgColorRes = R.color.onboarding_color_4,
            drawableRes = R.drawable.onboarding_image_4
        ),
        OnboardingScreen(
            textRes = R.string.onboarding_text_5,
            bgColorRes = R.color.onboarding_color_5,
            drawableRes = R.drawable.onboarding_image_5
        ),
        OnboardingScreen(
            textRes = R.string.onboarding_text_6,
            bgColorRes = R.color.onboarding_color_6,
            drawableRes = R.drawable.onboarding_image_6
        ),
        OnboardingScreen(
            textRes = R.string.onboarding_text_7,
            bgColorRes = R.color.onboarding_color_7,
            drawableRes = R.drawable.onboarding_image_7
        ),
        OnboardingScreen(
            textRes = R.string.onboarding_text_8,
            bgColorRes = R.color.onboarding_color_8,
            drawableRes = R.drawable.onboarding_image_8
        )
    )

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val adapter = OnboardingAdapter(screens, this)
        viewPager.adapter = adapter

        viewPager.offscreenPageLimit = 1
        viewPager.setCurrentItem(3, false)
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                toast("Selected page is $position")
            }
        })

        viewPager.setPageTransformer {
            page, position ->
            when {
                position < -1 || position > 1 -> {
                    page.alpha = 0f
                }
                position <= 0 -> {
                    page.alpha = 1 + position
                }
                position <= 1 -> {
                    page.alpha = 1 - position
                }
            }
        }
    }

    private fun toast(msg : String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}