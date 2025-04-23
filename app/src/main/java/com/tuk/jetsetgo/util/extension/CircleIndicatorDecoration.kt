package com.tuk.jetsetgo.util.extension

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CircleIndicatorDecoration : RecyclerView.ItemDecoration() {
    private val radius = 8f
    private val itemSpacing = 40f
    private val paintInactive = Paint().apply {
        style = Paint.Style.FILL
        color = 0xFFE0E0E0.toInt() // 회색
        isAntiAlias = true
    }
    private val paintActive = Paint().apply {
        style = Paint.Style.FILL
        color = 0xFF85B5E4.toInt() // 파란색
        isAntiAlias = true
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val itemCount = parent.adapter?.itemCount ?: return
        if (itemCount <= 1) return

        val indicatorStartX = (parent.width - (itemCount - 1) * itemSpacing) / 2f
        val indicatorY = parent.height - 40f

        val layoutManager = parent.layoutManager ?: return
        val activePosition = (layoutManager as? androidx.recyclerview.widget.LinearLayoutManager)
            ?.findFirstVisibleItemPosition() ?: return

        for (i in 0 until itemCount) {
            val cx = indicatorStartX + i * itemSpacing
            val paint = if (i == activePosition) paintActive else paintInactive
            c.drawCircle(cx, indicatorY, radius, paint)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
    }
}
