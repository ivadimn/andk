package ru.ivadimn.material.ui.images

import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ImageItemDecoration(
    private val context: Context,
    private val offset: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val offsetInPixels = offset.fromDpToPixels()
        with(outRect) {
            left = offsetInPixels
            top = offsetInPixels
            right = offsetInPixels
            bottom = offsetInPixels
        }
    }

    private fun Int.fromDpToPixels() : Int {
        val density = context.resources.displayMetrics.densityDpi
        val pixelsInDp = density / DisplayMetrics.DENSITY_DEFAULT
        return this * pixelsInDp
    }
}