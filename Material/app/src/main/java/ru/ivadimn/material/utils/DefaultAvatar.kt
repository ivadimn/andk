package ru.ivadimn.material.utils

import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.random.Random

class DefaultAvatar(
    val width : Float,
    val height : Float,
    val ini : String
) : Drawable() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.textSize = height / 2
        paint.color = Color.WHITE
        fillPaint.color = generateColor()
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(RectF(0F, 0F, width, height), fillPaint)
        val tw = paint.measureText(ini)
        canvas.drawText(ini, ((width - tw) / 2), (height / 2 + paint.textSize / 3), paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
        invalidateSelf()
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    private fun generateColor() : Int {
        return Color.argb(255, Random.nextInt(43, 203),
            Random.nextInt(43, 203), Random.nextInt(43, 203))
    }
}