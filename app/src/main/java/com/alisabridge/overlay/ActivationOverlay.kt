package com.alisabridge.overlay

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * A simple activation overlay view that lights the four corners briefly.
 */
class ActivationOverlay @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint().apply {
        color = Color.CYAN
        alpha = 200
        style = Paint.Style.FILL
    }
    var animate = false

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        val r = Math.min(w, h) * 0.08f
        if (animate) {
            // four corners
            canvas.drawCircle(r, r, r, paint)
            canvas.drawCircle(w - r, r, r, paint)
            canvas.drawCircle(r, h - r, r, paint)
            canvas.drawCircle(w - r, h - r, r, paint)
        }
    }

    fun showOnce(durationMs: Long = 700) {
        animate = true
        invalidate()
        postDelayed({
            animate = false
            invalidate()
        }, durationMs)
    }
}
