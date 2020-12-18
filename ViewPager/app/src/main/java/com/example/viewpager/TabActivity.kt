package com.example.viewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class TabActivity : AppCompatActivity(R.layout.activity_tab) {
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
    private lateinit var tab: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tab = findViewById<TabLayout>(R.id.tabLayout)
        viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val adapter = OnboardingAdapter(screens, this)
        viewPager.adapter = adapter

        TabLayoutMediator(tab, viewPager) {
            t, position -> t.text = "Tab ${position + 1}"
            if (position % 2 == 0) t.setIcon(R.drawable.tab_icon)
        }.attach()

        tab.getTabAt(1)?.orCreateBadge?.apply {
            number = 2
            badgeGravity = BadgeDrawable.TOP_END
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tab.getTabAt(position)?.removeBadge()
            }
        })


    }

}